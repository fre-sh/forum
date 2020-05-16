package com.fresh.forum.controller;

import com.fresh.forum.dto.EntityType;
import com.fresh.forum.dto.HostHolder;
import com.fresh.forum.dto.ViewObject;
import com.fresh.forum.model.Question;
import com.fresh.forum.model.User;
import com.fresh.forum.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    QuestionService questionService;

    @Autowired
    UserService userService;

    @Autowired
    FollowService followService;

    @Autowired
    CommentService commentService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    private ContentService contentService;

    @Autowired
    private RecordService recordService;

    @RequestMapping("/search")
    public String search(Model model, @RequestParam String keyword) {
        model.addAttribute("vos", getQuestions(0, 0, 20));
        return "index";
    }

    @RequestMapping(path = {"/home", "/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model) {
        model.addAttribute("vos", contentService.getLatest(0, 0, 20));
        return "index";
    }

    @RequestMapping(path = {"/user/{userId}"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String userIndex(Model model, @PathVariable("userId") int userId) {
        model.addAttribute("vos", getQuestions(userId, 0, 20));

        User user = userService.getUser(userId);
        ViewObject vo = new ViewObject();
        vo.set("user", user);
        vo.set("answerCnt", contentService.countAnswer(userId));
        vo.set("followerCount", followService.getFollowerCount(EntityType.user, userId));
        vo.set("followeeCount", followService.getFolloweeCount(userId, EntityType.user));
        if (hostHolder.getUser() != null) {
            vo.set("followed", followService.isFollower(hostHolder.getUser().getId(), EntityType.user, userId));
        } else {
            vo.set("followed", false);
        }
        model.addAttribute("profileUser", vo);

        recordService.save(hostHolder.getUser().getId(), EntityType.user, userId);
        return "profile";
    }


    private List<ViewObject> getQuestions(int userId, int offset, int limit) {
        List<Question> questionList = questionService.getLatestQuestions(userId, offset, limit);
        List<ViewObject> vos = CreateViewObjects(questionList);
        return vos;
    }

    private List<ViewObject> CreateViewObjects(List<Question> questionList) {
        List<ViewObject> vos = new ArrayList<>();
        for (Question question : questionList) {
            ViewObject vo = new ViewObject();
            vo.set("question", question);
            vo.set("followCount", followService.getFollowerCount(EntityType.question, question.getId()));
            vo.set("user", userService.getUser(question.getUserId()));
            vos.add(vo);
        }
        return vos;
    }
}
