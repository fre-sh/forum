package com.fresh.forum.controller;

import com.fresh.forum.dto.EntityType;
import com.fresh.forum.dto.HostHolder;
import com.fresh.forum.dto.ResponseTO;
import com.fresh.forum.dto.ViewObject;
import com.fresh.forum.model.Comment;
import com.fresh.forum.service.CommentService;
import com.fresh.forum.service.QuestionService;
import com.fresh.forum.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
    @Autowired
    HostHolder hostHolder;

    @Autowired
    CommentService commentService;

    @Autowired
    QuestionService questionService;

//    @Autowired
//    EventProducer eventProducer;


    @RequestMapping(path = {"comment/add"}, method = {RequestMethod.POST})
    @ResponseBody
    public ResponseTO addComment(@RequestParam("contentId") int contentId,
                                 @RequestParam("content") String content) {
        try {
            Comment comment = new Comment();
            comment.setContent(content);
            if (hostHolder.getUser() != null) {
                comment.setUserId(hostHolder.getUser().getId());
            } else {
                comment.setUserId(WendaUtil.ANONYMOUS_USERID);
            }
            comment.setCreatedDate(new Date());
            comment.setEntityType(EntityType.content);
            comment.setEntityId(contentId);
            comment = commentService.addComment(comment);
            return ResponseTO.success(
                    ViewObject.build("comment", comment).set("user", hostHolder.getUser())
            );
        } catch (Exception e) {
            logger.error("增加评论失败" + e.getMessage());
        }
        return ResponseTO.failed("添加评论失败");
    }
}
