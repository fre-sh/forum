package com.fresh.forum.service;

import com.fresh.forum.dto.EntityType;
import com.fresh.forum.dto.ViewObject;
import com.fresh.forum.model.Content;
import com.fresh.forum.repository.ContentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ContentService {

    @Autowired
    ContentDAO contentDAO;

    @Autowired
    UserService userService;

    @Autowired
    FollowService followService;

    @Autowired
    private CommentService commentService;

    public List<ViewObject> getLatest(int userId, int offset, int limit) {
        List<Content> contentList = contentDAO.getLatest(userId, offset, limit);
        return contentList.stream()
                .map(content -> ViewObject
                    .build("content", content)
                    .set("briefContent", getBriefContent(content))
                    .set("user", userService.getUser(content.getUserId()))
                    .set("userFanCnt", followService.getFollowerCount(EntityType.user, content.getUserId()))
                    .set("commentCnt", commentService.getCommentCount(content.getId(), EntityType.content))
        ).collect(Collectors.toList());
    }

    private String getBriefContent(Content content) {
        return content.getContent().substring(0, Math.min(200, content.getContent().length())) + (content.getContent().length() > 200 ? "..." : "" );
    }
}
