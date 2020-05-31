package com.fresh.forum.controller;

import com.fresh.forum.dto.Query;
import com.fresh.forum.dto.ResponseTO;
import com.fresh.forum.dto.UserRole;
import com.fresh.forum.model.Question;
import com.fresh.forum.model.User;
import com.fresh.forum.service.QuestionService;
import com.fresh.forum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController extends BaseController{

    @Autowired
    private UserService userService;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/user/{id}")
    public ResponseTO getUser(@PathVariable Integer id) {
        return success(userService.getById(id));
    }

    @RequestMapping("/user/delAll")
    public ResponseTO delUser(@RequestBody List<Integer> ids) {
        userService.delete(ids);
        return success();
    }

    @RequestMapping("/user/save")
    public ResponseTO saveUser(@RequestBody User user) {
        if (user.getId() != null) {
            userService.update(user);
        } else {
            userService.add(user);
        }
        return success();
    }
    
    @RequestMapping("/user/list")
    public ResponseTO listUser(@RequestBody Query query) {
        return success(userService.listByQuery(query));
    }


    @RequestMapping("/question/{id}")
    public ResponseTO getQuestion(@PathVariable Integer id) {
        return success(questionService.getById(id));
    }

    @RequestMapping("/question/delAll")
    public ResponseTO delQuestion(@RequestBody List<Integer> ids) {
        questionService.delete(ids);
        return success();
    }

    @RequestMapping("/question/add")
    public ResponseTO saveQuestion(@RequestBody Question question) {
        if (question.getId() != null) {
            questionService.update(question);
        } else {
            questionService.add(question);
        }
        return success();
    }

    @RequestMapping("/question/list")
    public ResponseTO listQuestion(@RequestBody Query query) {
        return success(questionService.listByQuery(query));
    }

    @RequestMapping("/login")
    public ResponseTO login(String username, String pass) {
        User user = userService.getByName(username);
        if (user.getRole() == UserRole.normal) {
            return ResponseTO.failed("用户权限不足");
        }
        return success(userService.login(username, pass));
    }

}
