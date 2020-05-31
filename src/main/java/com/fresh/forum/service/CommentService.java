package com.fresh.forum.service;

import com.fresh.forum.dto.EntityType;
import com.fresh.forum.dto.HostHolder;
import com.fresh.forum.dto.Query;
import com.fresh.forum.model.Comment;
import com.fresh.forum.dao.CommentDAO;
import com.fresh.forum.model.Content;
import com.fresh.forum.model.CountRecord;
import com.fresh.forum.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import java.util.Date;
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
    @Autowired
    HostHolder hostHolder;
    @Autowired
    AdminService adminService;

    public List<Comment> getCommentsByEntity(int entityId, EntityType entityType) {
        return commentDAO.findByEntityIdAndEntityType(entityId, entityType);
    }

    public Comment add(Comment comment) {
        comment.setUser(hostHolder.getUser());
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
        comment.setContent(sensitiveService.filter(comment.getContent()));
        comment.setEntityType(EntityType.content);
        Content one = contentService.getById(comment.getEntity().getId());
        one.setCommentCnt(one.getCommentCnt() + 1);

        CountRecord countRecord = adminService.getCountRecord(new Date());
        countRecord.setCommentCnt(countRecord.getCommentCnt() + 1);
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
        Content content = new Content();
        content.setContentType(query.getContentType());
        content.setTitle(query.getTitle());
        tmp.setEntity(content);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("content", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("entity.title", ExampleMatcher.GenericPropertyMatchers.contains());
        Example<Comment> example = Example.of(tmp, matcher);

        Sort sort = new Sort(Sort.Direction.DESC, "createdDate");
        // JPA 分页从0页开始
        return commentDAO.findAll(example, new PageRequest(query.getCurPage() - 1, query.getPageSize(), sort));
    }
}
