package com.fresh.forum.controller;

import com.fresh.forum.dto.Query;
import com.fresh.forum.dto.ResponseTO;
import com.fresh.forum.dto.UserRole;
import com.fresh.forum.model.Question;
import com.fresh.forum.model.User;
import com.fresh.forum.service.QuestionService;
import com.fresh.forum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController extends BaseController{

    @Autowired
    private UserService userService;
    @Autowired
    private QuestionService questionService;

    @RequestMapping("/question/delAll")
    public ResponseTO listQuestion(@RequestBody List<Integer> ids) {
        questionService.delete(ids);
        return success();
    }

    @RequestMapping("/question/add")
    public ResponseTO listQuestion(@RequestBody Question query) {
        questionService.add(query);
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
