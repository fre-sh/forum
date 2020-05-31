package com.fresh.forum.service;

import com.fresh.forum.dto.EntityType;
import com.fresh.forum.dto.Query;
import com.fresh.forum.model.Comment;
import com.fresh.forum.dao.CommentDAO;
import com.fresh.forum.model.Content;
import com.fresh.forum.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
@Transactional
public class CommentService {
    @Autowired
    CommentDAO commentDAO;

    @Autowired
    SensitiveService sensitiveService;

    @Autowired
    private ContentService contentService;

    public List<Comment> getCommentsByEntity(int entityId, EntityType entityType) {
        return commentDAO.findByEntityIdAndEntityType(entityId, entityType);
    }

    public Comment add(Comment comment) {
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
        comment.setContent(sensitiveService.filter(comment.getContent()));
        if (comment.getEntityType() == EntityType.content) {
            Content one = comment.getEntity();
            one.setCommentCnt(one.getCommentCnt() + 1);
        }
        return commentDAO.save(comment);
    }

    public int getCommentCount(int entityId, EntityType entityType) {
        return commentDAO.countByEntityIdAndEntityType(entityId, entityType);
    }

    public int getUserAnswerCount(int userId) {
        return commentDAO.countByEntityIdAndEntityType(userId, EntityType.question);
    }

    public void deleteComment(int commentId) {
        commentDAO.findById(commentId).setStatus(1);
    }

    public Comment getById(int id) {
        return commentDAO.findById(id);
    }

    public void delete(List<Integer> ids) {
        commentDAO.deleteAllByIdIn(ids);
    }

    public void update(Comment comment) {
        commentDAO.save(comment);
    }

    public Page<Comment> listByQuery(Query query) {
        Comment tmp = new Comment();
        tmp.setContent(query.getKw());
        tmp.setStatus(query.getStatus());
        tmp.setEntityType(query.getEntityType());
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());
        Example<Comment> example = Example.of(tmp, matcher);

        Sort sort = new Sort(Sort.Direction.DESC, "createdDate");
        // JPA 分页从0页开始
        return commentDAO.findAll(example, new PageRequest(query.getCurPage() - 1, query.getPageSize(), sort));
    }
}
