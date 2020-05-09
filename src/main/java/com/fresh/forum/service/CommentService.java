package com.fresh.forum.service;

import com.fresh.forum.model.Comment;
import com.fresh.forum.repository.CommentDAO;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Comment> getCommentsByEntity(int entityId, int entityType) {
        return commentDAO.findByEntityIdAndEntityType(entityId, entityType);
    }

    public void addComment(Comment comment) {
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
        comment.setContent(sensitiveService.filter(comment.getContent()));
        commentDAO.save(comment);
    }

    public int getCommentCount(int entityId, int entityType) {
        return commentDAO.countByEntityIdAndEntityType(entityId, entityType);
    }

    public int getUserCommentCount(int userId) {
        return commentDAO.countByUserId(userId);
    }

    public void deleteComment(int commentId) {
        commentDAO.getOne(commentId).setStatus(1);
    }

    public Comment getCommentById(int id) {
        return commentDAO.getOne(id);
    }
}
