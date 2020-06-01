package com.fresh.forum.service;

import com.fresh.forum.dao.UserDAO;
import com.fresh.forum.dto.HostHolder;
import com.fresh.forum.dto.Query;
import com.fresh.forum.model.Message;
import com.fresh.forum.dao.MessageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MessageService {

    @Autowired
    MessageDAO messageDAO;

    @Autowired
    SensitiveService sensitiveService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    UserDAO userDAO;

    public int countUnRead(int fromUser, int toUser) {
        return messageDAO.countByFromUserIdAndToUserIdAndHasReadIsFalse(fromUser, toUser);
    }

    public void add(Message message) {
        message.setFromUser(hostHolder.getUser());
        message.setConversationId(String.format("%d_%d",
                Math.min(message.getFromUser().getId(), message.getToUser().getId()),
                Math.max(message.getFromUser().getId(), message.getToUser().getId())
        ));
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
        return messageDAO.countByToUserIdAndConversationIdAndHasReadIsFalse(userId, conversationId);
    }

    public void hasRead(String conversationId, int receiveUser) {
        messageDAO.findByConversationIdAndToUserId(conversationId, receiveUser).forEach(e -> e.setHasRead(true));
    }

    public Message getById(Integer id) {
        return messageDAO.findById(id);
    }

    public void delete(List<Integer> ids) {
        messageDAO.deleteAllByIdIn(ids);
    }

    public void update(Message message) {
        messageDAO.save(message);
    }

    public Page<Message> listByQuery(Query query) {
        Message tmp = new Message();
        tmp.setContent(query.getKw());
        tmp.setStatus(query.getStatus());
        if (query.getFromId() != null) {
            tmp.setFromUser(userDAO.findById(query.getFromId()));
        }
        if (query.getToId() != null) {
            tmp.setToUser(Optional.ofNullable(userDAO.findById(query.getToId())).orElse(null));
        }
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("content", ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnorePaths("hasRead");
        Example<Message> example = Example.of(tmp, matcher);

        Sort sort = new Sort(Sort.Direction.DESC, "createdDate");
        // JPA 分页从0页开始
        return messageDAO.findAll(example, new PageRequest(query.getCurPage() - 1, query.getPageSize(), sort));
    }
}
