package com.fresh.forum.service;

import com.fresh.forum.model.Message;
import com.fresh.forum.dao.MessageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    @Autowired
    MessageDAO messageDAO;

    @Autowired
    SensitiveService sensitiveService;

    public int countUnRead(int fromUser, int toUser) {
        return messageDAO.countByFromIdAndToIdAndHasReadIsFalse(fromUser, toUser);
    }

    public void addMessage(Message message) {
        message.setContent(sensitiveService.filter(message.getContent()));
        messageDAO.save(message);
    }

    public List<Message> getConversationDetail(String conversationId, int offset, int limit) {
        return  messageDAO.getConversationDetail(conversationId, offset, limit)
                .stream()
                .sorted((a,b) -> compareDate(a.getCreatedDate(), b.getCreatedDate()))
                .collect(Collectors.toList());
    }

    /**
     * 晚者优先
     * @param a
     * @param b
     * @return
     */
    public static int compareDate(Date a, Date b) {
        if (a.after(b)) {
            return 1;
        } else if (a.before(b)) {
            return -1;
        } else {
            return 0;
        }
    }

    public List<Message> getConversationList(int userId, int offset, int limit) {
        return  messageDAO.getConversationList(userId, offset, limit);
    }

    public int getConversationUnreadCount(int userId, String conversationId) {
        return messageDAO.countByToIdAndConversationIdAndHasReadIsFalse(userId, conversationId);
    }

    public void hasRead(String conversationId, int receiveUser) {
        messageDAO.findByConversationIdAndToId(conversationId, receiveUser).forEach(e -> e.setHasRead(true));
    }
}
