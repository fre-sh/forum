package com.fresh.forum.controller;

import com.fresh.forum.dto.ResponseTO;
import com.fresh.forum.dto.UserRole;
import com.fresh.forum.model.User;
import com.fresh.forum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public ResponseTO login(String username, String pass) {
        User user = userService.getByName(username);
        if (user.getRole() == UserRole.normal) {
            return ResponseTO.failed("用户权限不足");
        }
        return ResponseTO.success(userService.login(username, pass));
    }

}
