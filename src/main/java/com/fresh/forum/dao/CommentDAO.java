package com.fresh.forum.dao;

import com.fresh.forum.dto.EntityType;
import com.fresh.forum.dto.Query;
import com.fresh.forum.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CommentDAO extends JpaRepository<Comment, Integer> {

    List<Comment> findByEntityIdAndEntityType(int entityId, EntityType entityType);

    int countByEntityIdAndEntityType(int entityId, EntityType entityType);

    int countByUserIdAndEntityType(int userId, EntityType entityType);

    Comment findById(int commentId);

    void deleteAllByUserId(Integer integer);

    void deleteAllByIdIn(List<Integer> ids);
}
