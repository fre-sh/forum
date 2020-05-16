package com.fresh.forum.model;

import com.fresh.forum.dto.EntityType;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "read_record")
public class ReadRecord {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private int userId;
    private EntityType entityType;
    private int entityId;
    private String entityTitle;
    private Date date;

    public String getEntityTitle() {
        return entityTitle;
    }

    public void setEntityTitle(String entityTitle) {
        this.entityTitle = entityTitle;
    }

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

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
