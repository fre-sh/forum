package com.fresh.forum.dao;

import com.fresh.forum.dto.EntityType;
import com.fresh.forum.model.Comment;
import com.fresh.forum.model.ReadRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface RecordDAO extends JpaRepository<ReadRecord, Integer> {

    List<ReadRecord> findByUserIdOrderByDateDesc(int userId);

    List<ReadRecord> findByUserIdAndEntityTypeAndEntityId(int userId, EntityType entityType, int entityId);


}
