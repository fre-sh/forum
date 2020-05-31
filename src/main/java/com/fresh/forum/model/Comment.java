package com.fresh.forum.model;

import com.fresh.forum.dto.EntityType;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "comment")
public class Comment extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "entity_id")
    private Content entity;

    @Enumerated(value = EnumType.STRING)
    private EntityType entityType = EntityType.content;

    private String content;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Content getEntity() {
        return entity;
    }

    public void setEntity(Content entity) {
        this.entity = entity;
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

}
