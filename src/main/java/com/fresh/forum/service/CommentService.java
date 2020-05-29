package com.fresh.forum.service;

import com.fresh.forum.dto.EntityType;
import com.fresh.forum.model.Comment;
import com.fresh.forum.dao.CommentDAO;
import com.fresh.forum.model.Content;
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

    @Autowired
    private ContentService contentService;

    public List<Comment> getCommentsByEntity(int entityId, EntityType entityType) {
        return commentDAO.findByEntityIdAndEntityType(entityId, entityType);
    }

    public Comment addComment(Comment comment) {
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
        comment.setContent(sensitiveService.filter(comment.getContent()));
        if (comment.getEntityType() == EntityType.content) {
            Content one = contentService.getOne(comment.getEntityId());
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

    public Comment getCommentById(int id) {
        return commentDAO.findById(id);
    }
}
