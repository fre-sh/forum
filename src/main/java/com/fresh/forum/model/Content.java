package com.fresh.forum.model;

import com.fresh.forum.dto.ContentType;
import com.fresh.forum.dto.EntityType;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "content")
public class Content extends BaseEntity{
    private int userId;

    @Enumerated(value = EnumType.STRING)
    private ContentType contentType;

    /**
     * 回答 -> 问题
     * 文章 -> 标题
     */
    private String title;

    private String content;

    private boolean isDelete;

    private int commentCnt;

    public int getCommentCnt() {
        return commentCnt;
    }

    public void setCommentCnt(int commentCnt) {
        this.commentCnt = commentCnt;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }
}
