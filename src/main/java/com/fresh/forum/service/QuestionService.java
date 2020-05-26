package com.fresh.forum.service;

import com.fresh.forum.dto.Query;
import com.fresh.forum.model.Question;
import com.fresh.forum.dao.QuestionDAO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
@Transactional
public class QuestionService {
    @Autowired
    QuestionDAO questionDAO;

    @Autowired
    SensitiveService sensitiveService;

    public List<Question> listByQuery(Query query) {
        Question tmpQ = new Question();

        return null;
    }

    public Question getByTitle(String title) {
        return questionDAO.findByTitle(title);
    }

    public Question getById(int id) {
        return questionDAO.getOne(id);
    }

    public void addQuestion(Question question) {
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));
        // 敏感词过滤
        question.setTitle(sensitiveService.filter(question.getTitle()));
        question.setContent(sensitiveService.filter(question.getContent()));
        questionDAO.save(question);
    }

    public List<Question> getLatestQuestions(int userId, int offset, int limit) {
        return questionDAO.selectLatestQuestions(userId, offset, limit);
    }

    public int updateAnswerCount(int id, int count) {
        questionDAO.getOne(id).setAnswerCount(count);
        return count;
    }
}
