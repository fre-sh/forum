package com.fresh.forum.model;

import com.fresh.forum.dto.EntityType;

import javax.persistence.*;

@Entity
@Table(name = "follow_relation")
public class FollowRelation extends BaseEntity{

    private int userId;

    private int entityId;

    @Enumerated(value = EnumType.STRING)
    private EntityType entityType;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }
}
