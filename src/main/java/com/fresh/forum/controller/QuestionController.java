package com.fresh.forum.controller;

import com.fresh.forum.AppException;
import com.fresh.forum.dto.EntityType;
import com.fresh.forum.dto.HostHolder;
import com.fresh.forum.dto.ViewObject;
import com.fresh.forum.model.Comment;
import com.fresh.forum.model.Question;
import com.fresh.forum.model.User;
import com.fresh.forum.service.CommentService;
import com.fresh.forum.service.QuestionService;
import com.fresh.forum.service.UserService;
import com.fresh.forum.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class QuestionController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    QuestionService questionService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    UserService userService;

    @Autowired
    CommentService commentService;

//    @Autowired
//    FollowService followService;
//
//    @Autowired
//    LikeService likeService;
//
//    @Autowired
//    EventProducer eventProducer;

    @RequestMapping(value = "/question/{qid}", method = {RequestMethod.GET})
    public String questionDetail(Model model, @PathVariable("qid") int qid) {
        Question question = questionService.getById(qid);
        model.addAttribute("question", question);

        List<Comment> commentList = commentService.getCommentsByEntity(qid, EntityType.ENTITY_QUESTION);
        List<ViewObject> comments = new ArrayList<>();
        for (Comment comment : commentList) {
            ViewObject vo = new ViewObject();
            vo.set("comment", comment);
//            if (hostHolder.getUser() == null) {
//                vo.set("liked", 0);
//            } else {
//                vo.set("liked", likeService.getLikeStatus(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT, comment.getId()));
//            }
//
//            vo.set("likeCount", likeService.getLikeCount(EntityType.ENTITY_COMMENT, comment.getId()));
            vo.set("user", userService.getUser(comment.getUserId()));
            comments.add(vo);
        }
        model.addAttribute("comments", comments);

//        List<ViewObject> followUsers = new ArrayList<ViewObject>();
//        // 获取关注的用户信息
//        List<Integer> users = followService.getFollowers(EntityType.ENTITY_QUESTION, qid, 20);
//        for (Integer userId : users) {
//            ViewObject vo = new ViewObject();
//            User u = userService.getUser(userId);
//            if (u == null) {
//                continue;
//            }
//            vo.set("name", u.getName());
//            vo.set("headUrl", u.getHeadUrl());
//            vo.set("id", u.getId());
//            followUsers.add(vo);
//        }
//        model.addAttribute("followUsers", followUsers);
//        if (hostHolder.getUser() != null) {
//            model.addAttribute("followed", followService.isFollower(hostHolder.getUser().getId(), EntityType.ENTITY_QUESTION, qid));
//        } else {
//            model.addAttribute("followed", false);
//        }

        return "detail";
    }

    @RequestMapping(value = "/question/add", method = {RequestMethod.POST})
    @ResponseBody
    public String addQuestion(@RequestParam("title") String title, @RequestParam("content") String content) {
        try {
            Question question = new Question();
            question.setContent(content);
            question.setCreatedDate(new Date());
            question.setTitle(title);
            if (hostHolder.getUser() == null) {
                question.setUserId(WendaUtil.ANONYMOUS_USERID);
                throw new AppException();
            } else {
                question.setUserId(hostHolder.getUser().getId());
            }
            questionService.addQuestion(question);
//                eventProducer.fireEvent(new EventModel(EventType.ADD_QUESTION)
//                        .setActorId(question.getUserId()).setEntityId(question.getId())
//                .setExt("title", question.getTitle()).setExt("content", question.getContent()));
            return WendaUtil.getJSONString(0);
        } catch (Exception e) {
            logger.error("增加问题失败" + e.getMessage());
        }
        return WendaUtil.getJSONString(1, "失败");
    }

}