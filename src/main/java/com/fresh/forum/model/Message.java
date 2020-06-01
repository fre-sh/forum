package com.fresh.forum.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "message")
@Entity
public class Message extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "from_id")
    private User fromUser;
    @ManyToOne
    @JoinColumn(name = "to_id")
    private User toUser;
    private String content;
    private boolean hasRead;
    private String conversationId;

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isHasRead() {
        return hasRead;
    }

    public void setHasRead(boolean hasRead) {
        this.hasRead = hasRead;
    }

    public String getConversationId() {
//        if (fromId < toId) {
//            conversionId = String.format("%d_%d", fromId, toId);
//        } else {
//            conversionId = String.format("%d_%d", toId, fromId);
//        }
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }
}
