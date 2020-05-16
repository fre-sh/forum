package com.fresh.forum.dao;

import com.fresh.forum.dto.EntityType;
import com.fresh.forum.model.FollowRelation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;


public interface FollowDAO extends JpaRepository<FollowRelation, Integer> {

    List<FollowRelation> findByEntityTypeAndEntityId(EntityType entityType, int entityId);

    List<FollowRelation> findByUserIdAndEntityType(int userId, EntityType entityType);

    List<FollowRelation> findByUserIdAndEntityTypeIn(int userId, Collection<EntityType> entityType);

    void deleteByUserIdAndEntityTypeAndEntityId(int userId, EntityType entityType, int entityId);

    int countByUserIdAndEntityType(int userId, EntityType entityType);

    int countByEntityTypeAndEntityId(EntityType entityType, int entityId);

    int countByUserIdAndEntityTypeAndEntityId(int userId, EntityType entityType, int entityId);

//    boolean existsByUserId(int userId);
}
