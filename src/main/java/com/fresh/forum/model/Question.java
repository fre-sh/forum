package com.fresh.forum.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "question")
public class Question extends BaseEntity{

    @Column(unique = true)
    private String title;
    private String content;
    private Integer userId;
    private Integer answerCount;

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(Integer answerCount) {
        this.answerCount = answerCount;
    }
}
