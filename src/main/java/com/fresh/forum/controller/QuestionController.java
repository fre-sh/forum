package com.fresh.forum.controller;

import com.fresh.forum.dto.ResponseTO;
import com.fresh.forum.model.User;
import com.fresh.forum.util.AppException;
import com.fresh.forum.dto.EntityType;
import com.fresh.forum.dto.HostHolder;
import com.fresh.forum.dto.ViewObject;
import com.fresh.forum.model.Question;
import com.fresh.forum.service.*;
import com.fresh.forum.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private ContentService contentService;

    @Autowired
    FollowService followService;
    @Autowired
    RecordService recordService;

    @RequestMapping("/question/follow")
    @ResponseBody
    public ResponseTO follow(int id) {
        followService.follow(hostHolder.getUser().getId(), EntityType.question, id);
        return ResponseTO.success();
    }

    @RequestMapping("/question/disFollow")
    @ResponseBody
    public ResponseTO disFollow(int id) {
        followService.disFollow(hostHolder.getUser().getId(), EntityType.question, id);
        return ResponseTO.success();
    }

    @RequestMapping(path = {"/question/list"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model) {
        List<ViewObject> vos = questionService.getLatestQuestions(0, 0, 20).stream()
                .map(question -> ViewObject
                        .build("question", question)
                        .set("user", question.getUser())
                        .set("answerCnt", contentService.listAnswer(question.getTitle()).size())
                        .set("followCount", followService.getFollowerCount(EntityType.user, question.getUser().getId()))
                ).collect(Collectors.toList());
        model.addAttribute("vos", vos);
        return "question";
    }

    @RequestMapping("/question")
    public String question(String title) {
        Question question = questionService.getByTitle(title);
        return "redirect:/question/" + question.getId();
    }

    @RequestMapping(value = "/question/{qid}", method = {RequestMethod.GET})
    public String questionDetail(Model model, @PathVariable("qid") int qid) {
        Question question = questionService.getById(qid);
        model.addAttribute("question", question);
        model.addAttribute("isFollow", followService.isFollower(hostHolder.getUser().getId(), EntityType.question, qid));

        List<User> followers = followService.getFollowers(EntityType.question, qid).stream()
                .map(follower -> userService.getUser(follower.getUserId())
                ).collect(Collectors.toList());

        List<ViewObject> vos = contentService.listAnswer(question.getTitle()).stream()
                .map(answer -> ViewObject.build("answer", answer)
                        .set("user", userService.getUser(answer.getUserId()))
                        .set("comments", commentService.getCommentsByEntity(answer.getId(), EntityType.content))
                        .set("isFollow", followService.isFollower(hostHolder.getUser().getId(), EntityType.content, answer.getId()))
                ).collect(Collectors.toList());

        model.addAttribute("vos", vos);
        model.addAttribute("followers", followers);

        recordService.save(hostHolder.getUser().getId(), EntityType.question, qid);
        return "detail";
    }


    @RequestMapping(value = "/question/add", method = {RequestMethod.POST})
    @ResponseBody
    public String addQuestion(@RequestParam("title") String title, @RequestParam("content") String content) {
        try {
            Question question = new Question();
            question.setContent(content);
            question.setTitle(title);
            questionService.add(question);
            return WendaUtil.getJSONString(0);
        } catch (Exception e) {
            logger.error("增加问题失败" + e.getMessage());
        }
        return WendaUtil.getJSONString(1, "失败");
    }

}
