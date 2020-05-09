package com.fresh.forum.repository;

import com.fresh.forum.dto.EntityType;
import com.fresh.forum.model.FollowRelation;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FollowDAO extends JpaRepository<FollowRelation, Integer> {

    int countByUserIdAndEntityType(int userId, EntityType entityType);

    int countByEntityTypeAndEntityId(EntityType entityType, int entityId);

    int countByUserIdAndEntityTypeAndEntityId(int userId, EntityType entityType, int entityId);

//    boolean existsByUserId(int userId);
}
