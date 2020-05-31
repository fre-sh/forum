package com.fresh.forum.service;

import com.fresh.forum.dto.HostHolder;
import com.fresh.forum.dto.Query;
import com.fresh.forum.model.Question;
import com.fresh.forum.dao.QuestionDAO;
import com.fresh.forum.util.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
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

    @Autowired
    HostHolder hostHolder;

    public Page<Question> listByQuery(Query query) {
        Question tmpQ = new Question();
        tmpQ.setTitle(query.getKw());
        tmpQ.setStatus(query.getStatus());
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("title", ExampleMatcher.GenericPropertyMatchers.contains());
        Example<Question> example = Example.of(tmpQ, matcher);

        Sort sort = new Sort(Sort.Direction.DESC, "createdDate");
        // JPA 分页从0页开始
//        return questionDAO.findAll(example, new PageRequest(query.getCurPage() - 1, query.getPageSize()));
        return questionDAO.findAll(example, new PageRequest(query.getCurPage() - 1, query.getPageSize(), sort));
    }

    public Question getByTitle(String title) {
        return questionDAO.findByTitle(title);
    }

    public Question getById(int id) {
        return questionDAO.findById(id);
    }

    public void update(Question question) {
        // 敏感词过滤
        question.setTitle(sensitiveService.filter(question.getTitle()));
        question.setContent(sensitiveService.filter(question.getContent()));
        questionDAO.save(question);
    }

    public void add(Question question) {
        if (questionDAO.countByTitle(question.getTitle()) > 0) {
            throw new AppException("问题已存在");
        }
        if (hostHolder.getUser() == null) {
            throw new AppException("请先登录");
        }
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));
        // 敏感词过滤
        question.setTitle(sensitiveService.filter(question.getTitle()));
        question.setContent(sensitiveService.filter(question.getContent()));
        question.setStatus(0);
        question.setAnswerCount(0);
        question.setUser(hostHolder.getUser());
        questionDAO.save(question);
    }

    public List<Question> getLatestQuestions(int userId, int offset, int limit) {
        return questionDAO.selectLatestQuestions(userId, offset, limit);
    }

    public int updateAnswerCount(int id, int count) {
        questionDAO.findById(id).setAnswerCount(count);
        return count;
    }

    public void delete(List<Integer> ids) {
        questionDAO.deleteAllByIdIn(ids);
    }
}
