package com.fresh.forum.repository;

import com.fresh.forum.model.Comment;
import com.fresh.forum.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CommentDAO extends JpaRepository<Comment, Integer> {

    List<Comment> findByEntityIdAndEntityType(int entityId, int entityType);

    int countByEntityIdAndEntityType(int entityId, int entityType);

    int countByUserId(int userId);
}
