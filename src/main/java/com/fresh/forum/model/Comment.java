package com.fresh.forum.model;

import com.fresh.forum.dto.EntityType;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "comment")
public class Comment extends BaseEntity{

    private int userId;

    private int entityId;

    @Enumerated(value = EnumType.STRING)
    private EntityType entityType;

    private String content;

    private int status;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
