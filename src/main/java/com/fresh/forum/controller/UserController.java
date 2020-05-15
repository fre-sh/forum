package com.fresh.forum.controller;

import com.fresh.forum.dto.EntityType;
import com.fresh.forum.dto.HostHolder;
import com.fresh.forum.model.FollowRelation;
import com.fresh.forum.model.User;
import com.fresh.forum.service.*;
import com.fresh.forum.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {

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

    @RequestMapping(path = {"/user/{uid}/followers"}, method = {RequestMethod.GET})
    public String followers(Model model, @PathVariable("uid") int userId) {
        if (hostHolder.getUser() != null) {
            List<User> followers = followService.getFollowers(EntityType.user, userId)
                    .stream()
                    .map(e -> userService.getUser(e.getUserId())).collect(Collectors.toList());
            model.addAttribute("followers", followers);
        } else {
            List<User> followees = followService.getFollowObjs(userId, EntityType.user)
                    .stream()
                    .map(e -> userService.getUser(e.getEntityId())).collect(Collectors.toList());
            model.addAttribute("followees", followees);
        }
        model.addAttribute("followerCount", followService.getFollowerCount(EntityType.user, userId));
        model.addAttribute("curUser", userService.getUser(userId));
        return "followers";
    }

    @RequestMapping(path = {"/user/follow"}, method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String followUser(@RequestParam("userId") int userId) {
        if (hostHolder.getUser() == null) {
            return WendaUtil.getJSONString(999);
        }

        followService.follow(hostHolder.getUser().getId(), EntityType.user, userId);

        return WendaUtil.getJSONString(0);
    }

    @RequestMapping(path = {"/user/disFollow"}, method = {RequestMethod.POST})
    @ResponseBody
    public String unfollowUser(@RequestParam("userId") int userId) {
        if (hostHolder.getUser() == null) {
            return WendaUtil.getJSONString(999);
        }

        followService.disFollow(hostHolder.getUser().getId(), EntityType.user, userId);

        return WendaUtil.getJSONString(0);
    }

}
