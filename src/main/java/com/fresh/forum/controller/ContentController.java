package com.fresh.forum.controller;

import com.fresh.forum.dto.HostHolder;
import com.fresh.forum.dto.ResponseTO;
import com.fresh.forum.service.CommentService;
import com.fresh.forum.service.ContentService;
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

    @RequestMapping(value = "/content/add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseTO add(String content, String contentType, String title) {

        contentService.add(content, contentType, title);
        return ResponseTO.success();
    }
}
