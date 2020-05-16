package com.fresh.forum.controller;

import com.fresh.forum.dto.ContentType;
import com.fresh.forum.dto.EntityType;
import com.fresh.forum.dto.ViewObject;
import com.fresh.forum.model.FollowRelation;
import com.fresh.forum.service.SearchService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @RequestMapping(path = {"/search/"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String search(Model model, @RequestParam("q") String keyword) {
        if (StringUtils.isBlank(keyword)) {
            return "redirect:/";
        }

        model.addAttribute("vos", searchService.searchAll(keyword));
        model.addAttribute("keyword", keyword);
        return "search";
    }

    @RequestMapping(path = {"/search/user"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String searchUser(Model model, @RequestParam("q") String keyword) {
        model.addAttribute("vos", searchService.searchUser(keyword));
        model.addAttribute("keyword", keyword);
        return "search";
    }

    @RequestMapping(path = {"/search/question"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String searchQuestion(Model model, @RequestParam("q") String keyword) {
        model.addAttribute("vos", searchService.searchQuestion(keyword));
        model.addAttribute("keyword", keyword);
        return "search";
    }

    @RequestMapping(path = {"/search/content"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String searchContent(Model model, @RequestParam("q") String keyword) {
        model.addAttribute("vos", searchService.searchContent(keyword));
        model.addAttribute("keyword", keyword);
        return "search";
    }
}
