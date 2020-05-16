package com.fresh.forum.dto;

import com.fresh.forum.model.Content;
import com.fresh.forum.model.Question;
import com.fresh.forum.model.User;
import com.fresh.forum.util.WendaUtil;

import java.util.Date;

public class SearchVO {

    public SearchVO() {

    }

    public SearchVO(User user) {
        this.id = user.getId();
        this.entityTitle = user.getName();
        this.entityType = EntityType.user.name();
        this.date = new Date(1);
    }

    public SearchVO(Question question) {
        this.id = question.getId();
        this.entityTitle = question.getTitle();
        this.entityType = EntityType.question.name();
        this.date = question.getCreatedDate();
    }

    public SearchVO(Content content) {
        this.id = content.getId();
        this.entityTitle = content.getTitle();
        this.entityType = EntityType.content.name();
        this.contentType = content.getContentType().name();
        this.content = WendaUtil.getBrief(content.getContent());
        this.date = content.getCreatedDate();
    }

    private int id;
    private String entityTitle;
    private String entityType;
    private Date date;

    // content用
    private String contentType;
    private String content;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getEntityTitle() {
        return entityTitle;
    }

    public void setEntityTitle(String entityTitle) {
        this.entityTitle = entityTitle;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
