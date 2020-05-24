package com.fresh.forum.service;

import com.fresh.forum.model.LoginTicket;
import com.fresh.forum.model.User;
import com.fresh.forum.dao.LoginTicketDAO;
import com.fresh.forum.dao.UserDAO;
import com.fresh.forum.util.WendaUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private LoginTicketDAO loginTicketDAO;

//    @Autowired
//    private LoginTicketDAO loginTicketDAO;

    public User getByName(String name) {
        return userDAO.findByName(name);
    }

    public Map<String, Object> register(String username, String password,String email, String description) {
        Map<String, Object> map;
        map = new HashMap<>();
        if (StringUtils.isBlank(username)) {
            map.put("msg", "用户名不能为空");
            return map;
        }

        if (StringUtils.isBlank(password)) {
            map.put("msg", "密码不能为空");
            return map;
        }

        User user = userDAO.findByName(username);

        if (user != null) {
            map.put("msg", "用户名已经被注册");
            return map;
        }

        // 密码强度
        user = new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0, 5));
        String head = String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000));
        user.setHeadUrl(head);
        user.setPassword(WendaUtil.MD5(password+user.getSalt()));
//        user.setPassword(password);
        user.setEmail(email);
        user.setDescription(description);
        userDAO.save(user);

        // 登陆
        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);
        return map;
    }


    public Map<String, Object> login(String username, String password) {
        Map<String, Object> map = new HashMap<>();

        User user = userDAO.findByName(username);

        if (user == null) {
            map.put("msg", "用户名不存在");
            return map;
        }

        if (!Objects.equals(WendaUtil.MD5(password + user.getSalt()), user.getPassword())) {
            map.put("msg", "密码不正确");
            return map;
        }

        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);
        map.put("userId", user.getId());
        return map;
    }

    private String addLoginTicket(int userId) {
        LoginTicket ticket = new LoginTicket();
        ticket.setUserId(userId);
        Date date = new Date();
        date.setTime(date.getTime() + 1000*3600*24);
        ticket.setExpired(date);
        ticket.setStatus(0);
        ticket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
        loginTicketDAO.save(ticket);
        return ticket.getTicket();
    }

    public User getUser(int id) {
        return userDAO.getOne(id);
    }

    public void logout(String ticket) {
        loginTicketDAO.findByTicket(ticket).setStatus(1);
    }
}
