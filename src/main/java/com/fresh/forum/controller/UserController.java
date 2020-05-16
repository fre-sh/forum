package com.fresh.forum.controller;

import com.fresh.forum.dto.*;
import com.fresh.forum.model.Content;
import com.fresh.forum.model.FollowRelation;
import com.fresh.forum.model.ReadRecord;
import com.fresh.forum.model.User;
import com.fresh.forum.service.*;
import com.fresh.forum.util.WendaUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
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
    @Autowired
    private RecordService recordService;

    @RequestMapping(path = {"/user/record/del"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseTO delRecords(int id) {
        recordService.delete(id);
        return ResponseTO.success();
    }

    @RequestMapping(path = {"/user/{uid}/records"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String records(Model model, @PathVariable("uid") int userId) {
        List<ReadRecord> records = recordService.findByUser(userId);
        model.addAttribute("records", records);
        return "readRecord";
    }

    @RequestMapping(path = {"/user/{uid}/collection"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String collection(Model model, @PathVariable("uid") int userId,
                             @RequestParam(required = false, defaultValue = "") String entityType) {
        List<FollowRelation> followRelations;

        if (StringUtils.isNotBlank(entityType)) {
            if (Arrays.stream(ContentType.values()).map(Enum::name).collect(Collectors.toList()).contains(entityType)) {
                ContentType contentType = ContentType.valueOf(entityType);
                followRelations = followService.getFollowObjs(userId, EntityType.content)
                        .stream().filter(relation -> contentService.getOne(relation.getEntityId()).getContentType() == contentType)
                        .collect(Collectors.toList());
            } else {
                followRelations = followService.getFollowObjs(userId, EntityType.valueOf(entityType));
            }
        } else {
            followRelations = followService.getFollowObjs(userId, EntityType.content, EntityType.question);
        }

        List<ViewObject> collection = followRelations.stream()
            .map(relation -> ViewObject.build("relation", relation)
                    .set("entityTitle", followService.getEntityTitle(relation))
                    .set("contentType", getContentType(relation))
                    .set("content", getContent(relation))
            ).collect(Collectors.toList());

        model.addAttribute("collection", collection);
        return "collection";
    }

    private Object getContent(FollowRelation relation) {
        if (relation.getEntityType() == EntityType.content) {
            String content = contentService.getOne(relation.getEntityId()).getContent();
            if (content.length() > 50) {
                return content.substring(0, 50) + "...";
            }
            return content;
        }
        return null;
    }

    private String getContentType(FollowRelation relation) {
        if (relation.getEntityType() != EntityType.content) {
            return "";
        }
        return contentService.getOne(relation.getEntityId()).getContentType().name();
    }

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
                .stream().map(FollowRelation::getEntityId)
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
