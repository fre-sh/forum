package com.fresh.forum.service;

import com.fresh.forum.model.Message;
import com.fresh.forum.repository.MessageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    MessageDAO messageDAO;

    @Autowired
    SensitiveService sensitiveService;

    public void addMessage(Message message) {
        message.setContent(sensitiveService.filter(message.getContent()));
        messageDAO.save(message);
    }

    public List<Message> getConversationDetail(String conversationId, int offset, int limit) {
        return  messageDAO.getConversationDetail(conversationId, offset, limit);
    }

    public List<Message> getConversationList(int userId, int offset, int limit) {
        return  messageDAO.getConversationList(userId, offset, limit);
    }

    public int getConversationUnreadCount(int userId, String conversationId) {
        return messageDAO.countByToIdAndConversationIdAndHasReadIsFalse(userId, conversationId);
    }

    public void hasRead(String conversationId) {
        messageDAO.findByConversationId(conversationId).forEach(e -> e.setHasRead(true));
    }
}
