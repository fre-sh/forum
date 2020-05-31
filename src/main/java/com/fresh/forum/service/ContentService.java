package com.fresh.forum.service;

import com.fresh.forum.dto.*;
import com.fresh.forum.model.Comment;
import com.fresh.forum.model.Content;
import com.fresh.forum.model.Question;
import com.fresh.forum.dao.ContentDAO;
import com.fresh.forum.dao.QuestionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private QuestionDAO questionDAO;

    @Autowired
    private SensitiveService sensitiveService;

    public Content getById(int id) {
        return contentDAO.findById(id);
    }

    public List<ViewObject> getLatest(int userId, int offset, int limit) {
        List<Content> contentList = contentDAO.getLatest(userId, offset, limit);
        return contentList.stream()
                .map(content -> ViewObject
                    .build("content", content)
                    .set("user", userService.getById(content.getUserId()))
                    .set("userFanCnt", followService.getFollowerCount(EntityType.user, content.getUserId()))
                    .set("commentCnt", commentService.getCommentCount(content.getId(), EntityType.content))
                    .set("comments", commentService.getCommentsByEntity(content.getId(), EntityType.content))
                    .set("isFollow", followService.isFollower(hostHolder.getUser().getId(), EntityType.content, content.getId()))
        ).collect(Collectors.toList());
    }

    public String getBriefContent(Content content) {
        return content.getContent().substring(0, Math.min(200, content.getContent().length())) + (content.getContent().length() > 200 ? "..." : "" );
    }

    public int countAnswer(int userId) {
        return contentDAO.countByUserId(userId);
    }

    public List<Content> listAnswer(String questionTitle) {
        return contentDAO.findByContentTypeAndTitle(ContentType.answer, questionTitle);
    }

    public Content add(String text, String contentType, String title) {
        Content content = new Content();
        content.setCreatedDate(new Date());
        content.setContent(sensitiveService.filter(text));
        content.setContentType(ContentType.valueOf(contentType));
        content.setTitle(title);
        content.setUserId(hostHolder.getUser().getId());

        if (content.getContentType() == ContentType.answer) {
            Question question = questionDAO.findByTitle(title);
            question.setAnswerCount(question.getAnswerCount() + 1);
        }

        return contentDAO.save(content);
    }

    public void init() {
        for (Content content : contentDAO.findAll()) {
            content.setCommentCnt(commentService.getCommentCount(content.getId(), EntityType.content));
        }
    }

    public void delete(List<Integer> ids) {
        contentDAO.deleteAllByIdIn(ids);
    }

    public void update(Content content) {
        contentDAO.save(content);
    }

    public void add(Content content) {
        content.setContent(sensitiveService.filter(content.getContent()));
        content.setUserId(hostHolder.getUser().getId());
        if (content.getContentType() == ContentType.answer) {
            Question question = questionDAO.findByTitle(content.getTitle());
            question.setAnswerCount(question.getAnswerCount() + 1);
        }
        contentDAO.save(content);
    }

    public Page<Content> listByQuery(Query query) {
        Content tmp = new Content();
        tmp.setTitle(query.getKw());
        tmp.setStatus(query.getStatus());
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("title", ExampleMatcher.GenericPropertyMatchers.contains());
        Example<Content> example = Example.of(tmp, matcher);

        Sort sort = new Sort(Sort.Direction.DESC, "createdDate");
        // JPA 分页从0页开始
        return contentDAO.findAll(example, new PageRequest(query.getCurPage() - 1, query.getPageSize(), sort));
    }

    public List<Content> allByQuery(Query query) {
        Content tmp = new Content();
        tmp.setTitle(query.getKw());
        tmp.setContentType(query.getContentType());
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("title", ExampleMatcher.GenericPropertyMatchers.contains());
        Example<Content> example = Example.of(tmp, matcher);

        Sort sort = new Sort(Sort.Direction.DESC, "createdDate");
        // JPA 分页从0页开始
        return contentDAO.findAll(example, sort);
    }
}
