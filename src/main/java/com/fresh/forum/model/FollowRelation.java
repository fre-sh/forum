package com.fresh.forum.model;

import com.fresh.forum.dto.EntityType;

import javax.persistence.*;

public class FollowRelation {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    private int userId;

    private int entityId;

    @Enumerated(value = EnumType.STRING)
    private EntityType entityType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
