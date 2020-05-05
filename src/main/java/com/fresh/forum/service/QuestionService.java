package com.fresh.forum.service;

import com.fresh.forum.model.Question;
import com.fresh.forum.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * Created by nowcoder on 2016/7/15.
 */
@Service
public class QuestionService {
    @Autowired
    QuestionRepository questionDAO;

//    @Autowired
//    SensitiveService sensitiveService;

    public Question getById(int id) {
        return questionDAO.getOne(id);
    }

//    public int addQuestion(Question question) {
//        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
//        question.setContent(HtmlUtils.htmlEscape(question.getContent()));
//        // 敏感词过滤
////        question.setTitle(sensitiveService.filter(question.getTitle()));
////        question.setContent(sensitiveService.filter(question.getContent()));
////        return questionDAO.addQuestion(question) > 0 ? question.getId() : 0;
//    }

    public List<Question> getLatestQuestions(int userId, int offset, int limit) {
        return questionDAO.selectLatestQuestions(userId, offset, limit);
    }

    public int updateCommentCount(int id, int count) {
        questionDAO.getOne(id).setCommentCount(count);
        return count;
    }
}
