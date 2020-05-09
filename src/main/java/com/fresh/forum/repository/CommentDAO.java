package com.fresh.forum.repository;

import com.fresh.forum.model.Comment;
import com.fresh.forum.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface CommentDAO extends JpaRepository<Comment, Integer> {



}
