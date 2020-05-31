package com.fresh.forum.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fresh.forum.model.Content;

/**
 * 用于管理页面查询
 * @author guowenyu
 * @date 2020/5/26
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Query {

    /**
     * 关键字
     */
    private String kw;

    /**
     * 状态 0-正常 1-删除
     */
    private Integer status;

    private UserRole role;

    private EntityType entityType;

    private ContentType contentType;

    private Integer curPage;

    private Integer pageSize;

    private Integer entityId;

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Integer getCurPage() {
        return curPage;
    }

    public void setCurPage(Integer curPage) {
        this.curPage = curPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getKw() {
        return kw;
    }

    public void setKw(String kw) {
        this.kw = kw;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
