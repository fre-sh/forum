package com.fresh.forum.controller;

import com.fresh.forum.dto.HostHolder;
import com.fresh.forum.dto.ViewObject;
import com.fresh.forum.model.Message;
import com.fresh.forum.model.User;
import com.fresh.forum.service.MessageService;
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
public class MessageController {
    @Autowired
    HostHolder hostHolder;

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @RequestMapping(path = {"/msg/list"}, method = {RequestMethod.GET})
    public String getConversationList(Model model) {
        if (hostHolder.getUser() == null) {
            return "redirect:/reglogin";
        }
        int localUserId = hostHolder.getUser().getId();
        List<Message> conversationList = messageService.getConversationList(localUserId, 0, 10);
        List<ViewObject> conversations = new ArrayList<>();
        for (Message message : conversationList) {
            ViewObject vo = new ViewObject();
            vo.set("message", message);
            int targetId = message.getFromId() == localUserId ? message.getToId() : message.getFromId();
            vo.set("user", userService.getUser(targetId));
            vo.set("unread", messageService.getConversationUnreadCount(localUserId, message.getConversationId()));
            conversations.add(vo);
        }
        model.addAttribute("conversations", conversations);
        return "letter";
    }

    @RequestMapping(path = {"/msg/detail/{conversationId}"}, method = {RequestMethod.GET})
    public String getConversationDetail(Model model, @PathVariable String conversationId) {
        try {
            messageService.hasRead(conversationId, hostHolder.getUser().getId());
            List<Message> messageList = messageService.getConversationDetail(conversationId, 0, 10);
            List<ViewObject> messages = new ArrayList<>();
            User talker = null;
            for (Message message : messageList) {
                ViewObject vo = new ViewObject();
                vo.set("message", message);
                User user = userService.getUser(message.getFromId());
                vo.set("user", user);
                if (user.getId() != hostHolder.getUser().getId()) {
                    talker = user;
                }
                messages.add(vo);
            }
            model.addAttribute("messages", messages);
            // 和当前用户对话的人
            model.addAttribute("talker", talker);
        } catch (Exception e) {
            logger.error("获取详情失败" + e.getMessage());
        }
        return "letterDetail";
    }

    @RequestMapping(path = {"/msg/addMessage"}, method = {RequestMethod.POST})
    public String addMessage(@RequestParam("toName") String toName,
                             @RequestParam("content") String content) {
        try {
            if (hostHolder.getUser() == null) {
                return WendaUtil.getJSONString(999, "未登录");
            }

            User user = userService.selectByName(toName);
            if (user == null) {
                return WendaUtil.getJSONString(1, "用户不存在");
            }

            Message message = new Message();
            message.setCreatedDate(new Date());
            message.setFromId(hostHolder.getUser().getId());
            message.setToId(user.getId());
            message.setConversationId(String.format("%d_%d",
                    Math.min(message.getFromId(), message.getToId()),
                    Math.max(message.getFromId(), message.getToId())
            ));
            message.setContent(content);
            messageService.addMessage(message);
            return "redirect:/msg/detail/" + message.getConversationId();

        } catch (Exception e) {
            logger.error("发送消息失败:", e);
            return WendaUtil.getJSONString(1, "发信失败");
        }
    }

    @RequestMapping(path = {"/msg/add"}, method = {RequestMethod.POST})
    @ResponseBody
    public String add(@RequestParam("toName") String toName,
                      @RequestParam("content") String content) {
        try {
            if (hostHolder.getUser() == null) {
                return WendaUtil.getJSONString(999, "未登录");
            }

            User user = userService.selectByName(toName);
            if (user == null) {
                return WendaUtil.getJSONString(1, "用户不存在");
            }

            Message message = new Message();
            message.setCreatedDate(new Date());
            message.setFromId(hostHolder.getUser().getId());
            message.setToId(user.getId());
            message.setContent(content);
            message.setConversationId(String.format("%d_%d",
                    Math.min(message.getFromId(), message.getToId()),
                    Math.max(message.getFromId(), message.getToId())
            ));
            messageService.addMessage(message);
            return WendaUtil.getJSONString(0);

        } catch (Exception e) {
            logger.error("发送消息失败" + e.getMessage());
            return WendaUtil.getJSONString(1, "发信失败");
        }
    }
}
