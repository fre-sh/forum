package com.fresh.forum.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "message")
@Entity
public class Message extends BaseEntity {

    private int fromId;
    private int toId;
    private String content;
    private boolean hasRead;
    private String conversationId;

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public int getToId() {
        return toId;
    }

    public void setToId(int toId) {
        this.toId = toId;
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
