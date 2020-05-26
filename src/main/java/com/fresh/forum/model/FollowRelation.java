package com.fresh.forum.model;

import com.fresh.forum.dto.EntityType;

import javax.persistence.*;

@Entity
@Table(name = "follow_relation")
public class FollowRelation extends BaseEntity{

    private Integer userId;

    private Integer entityId;

    @Enumerated(value = EnumType.STRING)
    private EntityType entityType;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }
}
