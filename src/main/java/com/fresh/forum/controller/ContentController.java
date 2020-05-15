package com.fresh.forum.controller;

import com.fresh.forum.dto.ContentType;
import com.fresh.forum.dto.EntityType;
import com.fresh.forum.dto.HostHolder;
import com.fresh.forum.dto.ResponseTO;
import com.fresh.forum.model.Content;
import com.fresh.forum.model.Question;
import com.fresh.forum.service.CommentService;
import com.fresh.forum.service.ContentService;
import com.fresh.forum.service.FollowService;
import com.fresh.forum.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

        contentService.add(content, contentType, title);
        return ResponseTO.success();
    }

    /**
     * 访问文章/回答（所属问题）
     * @param id
     * @return
     */
    @RequestMapping("/content/{id}")
    public String showContent(@PathVariable int id) {
        Content content = contentService.getOne(id);
        if (content.getContentType() == ContentType.answer) {
            Question question = questionService.getByTitle(content.getTitle());
            return "redirect:/question/" + question.getId();
        } else {
            return "/";
        }
    }

}
