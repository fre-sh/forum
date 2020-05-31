package com.fresh.forum.controller;

import com.fresh.forum.dto.ContentType;
import com.fresh.forum.dto.Query;
import com.fresh.forum.dto.ResponseTO;
import com.fresh.forum.dto.UserRole;
import com.fresh.forum.model.Comment;
import com.fresh.forum.model.Content;
import com.fresh.forum.model.Question;
import com.fresh.forum.model.User;
import com.fresh.forum.service.CommentService;
import com.fresh.forum.service.ContentService;
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
    @Autowired
    CommentService commentService;
    @Autowired
    ContentService contentService;

    @GetMapping("/content/{id}")
    public ResponseTO getContent(@PathVariable Integer id) {
        return success(contentService.getById(id));
    }

    @RequestMapping("/content/delAll")
    public ResponseTO delContent(@RequestBody List<Integer> ids) {
        contentService.delete(ids);
        return success();
    }

    @RequestMapping("/content/save")
    public ResponseTO saveContent(@RequestBody Content content) {
        if (content.getId() != null) {
            contentService.update(content);
        } else {
            contentService.add(content);
        }
        return success();
    }

    @RequestMapping("/content/list")
    public ResponseTO listContent(@RequestBody Query query) {
        return success(contentService.listByQuery(query));
    }

    /**
     * 不分页，用于下拉选
     * @param query
     * @return
     */
    @RequestMapping("/content/all")
    public ResponseTO allContent(@RequestBody Query query) {
        List<Content> list = contentService.allByQuery(query);
        list.forEach(e -> {
            if (e.getContentType() == ContentType.answer) {
                e.setTitle(e.getTitle() + "[id=" + e.getId() + "]");
            }
        });
        return success(list);
    }
    
    // 评论管理
    @GetMapping("/comment/{id}")
    public ResponseTO getComment(@PathVariable Integer id) {
        return success(commentService.getById(id));
    }

    @RequestMapping("/comment/delAll")
    public ResponseTO delComment(@RequestBody List<Integer> ids) {
        commentService.delete(ids);
        return success();
    }

    @RequestMapping("/comment/save")
    public ResponseTO saveComment(@RequestBody Comment comment) {
        if (comment.getId() != null) {
            commentService.update(comment);
        } else {
            commentService.add(comment);
        }
        return success();
    }

    @RequestMapping("/comment/list")
    public ResponseTO listComment(@RequestBody Query query) {
        return success(commentService.listByQuery(query));
    }
    
    // 用户管理
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
