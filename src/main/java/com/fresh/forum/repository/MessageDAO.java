package com.fresh.forum.repository;

import com.fresh.forum.model.Message;
import com.fresh.forum.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface MessageDAO extends JpaRepository<Message, Integer> {

    String INSERT_FIELDS = " from_id, to_id, content, has_read, conversation_id, created_date ";

    int countByToIdAndConversationIdAndHasReadIsFalse(int userId, String conversationId);

    @Query(value = "select * from message where conversation_id=?1 order by created_date desc limit ?2, ?3", nativeQuery = true)
    List<Message> getConversationDetail(String conversationId, int offset, int limit);

    @Query(value = "select " + INSERT_FIELDS + ", count(id) as id  from" +
            " ( select * from message where from_id=?1 or to_id=?1 order by created_date desc ) tt " +
            " group by conversation_id, created_date " +
            " order by created_date desc limit ?2, ?3", nativeQuery = true)
    List<Message> getConversationList(int userId, int offset, int limit);
}
