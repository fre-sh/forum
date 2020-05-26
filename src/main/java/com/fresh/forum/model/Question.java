package com.fresh.forum.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "question")
public class Question extends BaseEntity{

    @Column(unique = true)
    private String title;
    private String content;
    private int userId;
    private int answerCount;

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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(int answerCount) {
        this.answerCount = answerCount;
    }
}
