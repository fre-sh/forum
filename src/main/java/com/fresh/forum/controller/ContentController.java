package com.fresh.forum.controller;

import com.fresh.forum.dto.*;
import com.fresh.forum.model.Content;
import com.fresh.forum.model.Question;
import com.fresh.forum.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@Controller()
public class ContentController {
    private static final Logger logger = LoggerFactory.getLogger(ContentController.class);
    @Autowired
    HostHolder hostHolder;

    @Autowired
    CommentService commentService;

    @Autowired
    QuestionService questionService;

    @Autowired
    ContentService contentService;

    @Autowired
    FollowService followService;

    @Autowired
    private UserService userService;
    @Autowired
    private RecordService recordService;

    @RequestMapping("/article/edit")
    public String follow() {

        return "articleEdit";
    }

    @RequestMapping("/content/follow")
    @ResponseBody
    public ResponseTO follow(int id) {
        followService.follow(hostHolder.getUser().getId(), EntityType.content, id);
        return ResponseTO.success();
    }

    @RequestMapping("/content/disFollow")
    @ResponseBody
    public ResponseTO disFollow(int id) {
        followService.disFollow(hostHolder.getUser().getId(), EntityType.content, id);
        return ResponseTO.success();
    }

    @RequestMapping(value = "/content/add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseTO add(String content, String contentType, String title) {
        return ResponseTO.success(contentService.add(content, contentType, title));
    }

    /**
     * 访问文章/回答（所属问题）
     * @param id
     * @return
     */
    @RequestMapping("/content/{id}")
    public String showContent(@PathVariable int id, Model model) {
        Content content = contentService.getOne(id);
        if (content.getContentType() == ContentType.answer) {
            Question question = questionService.getByTitle(content.getTitle());
            return "redirect:/question/" + question.getId() + "#answer-item-" + id;

        } else if (content.getContentType() == ContentType.article){
            model.addAttribute("content", content);
            model.addAttribute("author", userService.getUser(content.getUserId()));
            model.addAttribute("comments", commentService.getCommentsByEntity(content.getId(), EntityType.content).stream()
                    .map(comment ->
                            ViewObject.build("comment", comment)
                            .set("user", userService.getUser(comment.getUserId()))
                    ).collect(Collectors.toList())
            );
            model.addAttribute("isFollow", followService.isFollower(hostHolder.getUser().getId(), EntityType.content, content.getId()));
            recordService.save(hostHolder.getUser().getId(), EntityType.content, id);
        }
        return "article";
    }

}
