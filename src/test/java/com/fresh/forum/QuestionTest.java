package com.fresh.forum;

import com.fresh.forum.controller.LoginController;
import com.fresh.forum.controller.QuestionController;
import com.fresh.forum.dto.UserRole;
import com.fresh.forum.model.User;
import com.fresh.forum.service.UserService;
import com.fresh.forum.util.WendaUtil;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.Random;

@TestPropertySource("classpath:application.properties")
@RunWith(SpringJUnit4ClassRunner.class)
public class QuestionTest {

    @Autowired
    UserService userService;
    Random random = new Random();

    @Test
    public void init() {
        for (int i = 0; i < 29; i++) {
            User user = new User();
            user.setName("auto_user" + i);
            user.setRole(UserRole.normal);
            user.setDescription("auto_description" + i);
            user.setEmail("auto" + i + "@xx.com");
            Date date = new Date();

            user.setCreatedDate(DateUtils.addDays(date, 0 - random.nextInt(10)));
            userService.add(user);
        }
    }

}
