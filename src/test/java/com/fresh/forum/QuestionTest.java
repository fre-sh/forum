package com.fresh.forum;

import com.fresh.forum.controller.LoginController;
import com.fresh.forum.controller.QuestionController;
import com.fresh.forum.util.WendaUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@TestPropertySource("classpath:application.properties")
@RunWith(SpringJUnit4ClassRunner.class)
public class QuestionTest {

    @Autowired
    private QuestionController questionController;

    @Test
    public void testAdd() {
        Assert.assertEquals(WendaUtil.getJSONString(0), questionController.addQuestion("问题1", "问题详情"));
    }

}
