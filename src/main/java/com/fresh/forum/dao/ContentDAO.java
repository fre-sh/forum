package com.fresh.forum.dao;

import com.fresh.forum.dto.ContentType;
import com.fresh.forum.model.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ContentDAO extends JpaRepository<Content, Integer> {

    @Query(value = "select * from content q where :userId = 0 or q.user_id = :userId " +
            " order by created_date desc limit :offset, :limit", nativeQuery = true
    )
    List<Content> getLatest(@Param("userId") int userId, @Param("offset") int offset, @Param("limit") int limit);

    List<Content> findByContentTypeAndTitle(ContentType contentType, String title);

    int countByUserId(int userId);
}
