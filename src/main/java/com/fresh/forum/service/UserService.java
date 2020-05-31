package com.fresh.forum.service;

import com.fresh.forum.dao.*;
import com.fresh.forum.dto.Query;
import com.fresh.forum.model.*;
import com.fresh.forum.util.WendaUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
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
    @Autowired
    private QuestionDAO questionDAO;
    @Autowired
    private ContentDAO contentDAO;
    @Autowired
    private CommentDAO commentDAO;
    @Autowired
    private MessageDAO messageDAO;

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

    public User getById(int id) {
        return userDAO.findById(id);
    }

    public void logout(String ticket) {
        loginTicketDAO.findByTicket(ticket).setStatus(1);
    }

    public Page<User> listByQuery(Query query) {
        User tmp = new User();
        tmp.setName(query.getKw());
        tmp.setStatus(query.getStatus());
        tmp.setRole(query.getRole());
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());
        Example<User> example = Example.of(tmp, matcher);

        Sort sort = new Sort(Sort.Direction.DESC, "createdDate");
        // JPA 分页从0页开始
        return userDAO.findAll(example, new PageRequest(query.getCurPage() - 1, query.getPageSize(), sort));
//        return userDAO.findAll(example);
    }

    public void delete(List<Integer> ids) {
        ids.forEach(questionDAO::deleteAllByUserId);
//        ids.forEach(contentDAO::deleteAllByUserId);
//        ids.forEach(commentDAO::deleteAllByUserId);
//        ids.forEach(messageDAO::deleteAllByUserId);
        userDAO.deleteByIdIn(ids);
    }

    public void update(User user) {
        userDAO.save(user);
    }

    public void add(User user) {
        user.setSalt(UUID.randomUUID().toString().substring(0, 5));
        String head = String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000));
        user.setHeadUrl(head);
        user.setPassword(WendaUtil.MD5(user.getPassword() + user.getSalt()));
        userDAO.save(user);
    }
}
