package com.fresh.forum.dao;

import com.fresh.forum.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface MessageDAO extends JpaRepository<Message, Integer> {

    String INSERT_FIELDS = " from_id, to_id, content, has_read, conversation_id, created_date ";

    List<Message> findByConversationIdAndToId(String conversationId, int toId);

    int countByToIdAndConversationIdAndHasReadIsFalse(int userId, String conversationId);

    int countByToIdAndHasReadIsFalse(int toId);

    @Query(value = "select * from message where conversation_id=?1 order by created_date desc limit ?2, ?3", nativeQuery = true)
    List<Message> getConversationDetail(String conversationId, int offset, int limit);

    Page<Message> findByConversationIdOrderByCreatedDate(String conversationId, Pageable pageable);

    @Query(value = "select " + INSERT_FIELDS + ", count(id) as id  from" +
            " ( select * from message where from_id=?1 or to_id=?1 order by created_date desc ) tt " +
            " group by conversation_id " +
            " order by created_date desc limit ?2, ?3", nativeQuery = true)
    List<Message> getConversationList(int userId, int offset, int limit);
}
