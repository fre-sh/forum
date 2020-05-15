package com.fresh.forum.controller;

import com.fresh.forum.dto.EntityType;
import com.fresh.forum.dto.HostHolder;
import com.fresh.forum.dto.ViewObject;
import com.fresh.forum.model.FollowRelation;
import com.fresh.forum.model.User;
import com.fresh.forum.service.*;
import com.fresh.forum.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        List<Integer> userIds = followService.getFollowers(EntityType.user, userId)
                .stream().map(FollowRelation::getUserId)
                .collect(Collectors.toList());
        model.addAttribute("followers",
                getUsersInfo(hostHolder.getUser().getId(), userIds)
        );

        model.addAttribute("followerCount", followService.getFollowerCount(EntityType.user, userId));
        model.addAttribute("curUser", userService.getUser(userId));
        return "followers";
    }

    @RequestMapping(path = {"/user/{uid}/followees"}, method = {RequestMethod.GET})
    public String followees(Model model, @PathVariable("uid") int userId) {
        List<Integer> userIds = followService.getFollowObjs(userId, EntityType.user)
                .stream().map(FollowRelation::getUserId)
                .collect(Collectors.toList());
        model.addAttribute("followees", getUsersInfo(hostHolder.getUser().getId(), userIds));
        model.addAttribute("followeeCount", followService.getFolloweeCount(userId, EntityType.user));
        model.addAttribute("curUser", userService.getUser(userId));
        return "followees";
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

    private List<ViewObject> getUsersInfo(int localUserId, List<Integer> userIds) {
        List<ViewObject> userInfos = new ArrayList<>();
        for (Integer uid : userIds) {
            User user = userService.getUser(uid);
            if (user == null) {
                continue;
            }
            ViewObject vo = new ViewObject();
            vo.set("user", user);
            vo.set("commentCount", contentService.countAnswer(uid));
            vo.set("followerCount", followService.getFollowerCount(EntityType.user, uid));
            vo.set("followeeCount", followService.getFolloweeCount(uid, EntityType.user));
            if (localUserId != 0) {
                vo.set("followed", followService.isFollower(localUserId, EntityType.user, uid));
            } else {
                vo.set("followed", false);
            }
            userInfos.add(vo);
        }
        return userInfos;
    }

}
