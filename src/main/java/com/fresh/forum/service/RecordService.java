package com.fresh.forum.service;

import com.fresh.forum.dao.*;
import com.fresh.forum.dto.EntityType;
import com.fresh.forum.model.ReadRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class RecordService {

    @Autowired
    private RecordDAO recordDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private ContentDAO contentDAO;
    @Autowired
    private QuestionDAO questionDAO;


    public void delete(int id) {
        recordDAO.delete(id);
    }

    public List<ReadRecord> findByUser(int userId) {
        return recordDAO.findByUserIdOrderByDateDesc(userId);
    }

    public ReadRecord save(int fromUser, EntityType entityType, int entityId) {
        List<ReadRecord> existRecord = recordDAO.findByUserIdAndEntityTypeAndEntityId(fromUser, entityType, entityId);
        if (existRecord.size() > 0) {
            existRecord.forEach(readRecord -> readRecord.setDate(new Date()));
            return existRecord.get(0);
        }

        ReadRecord record = new ReadRecord();
        record.setDate(new Date());
        record.setEntityType(entityType);
        record.setEntityId(entityId);
        record.setUserId(fromUser);
        switch (entityType) {
            case user:
                if (entityId == fromUser) {
                    return null;
                }
                record.setEntityTitle(userDAO.findById(entityId).getName());
                break;
            case content:
                record.setEntityTitle(contentDAO.findById(entityId).getTitle());
                break;
            case question:
                record.setEntityTitle(questionDAO.findById(entityId).getTitle());
                break;
        }

        return recordDAO.save(record);
    }
}
