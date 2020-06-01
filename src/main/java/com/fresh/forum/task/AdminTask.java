package com.fresh.forum.task;

import com.fresh.forum.dao.CommentDAO;
import com.fresh.forum.dao.ContentDAO;
import com.fresh.forum.dao.CountRecordDAO;
import com.fresh.forum.dao.UserDAO;
import com.fresh.forum.model.CountRecord;
import com.fresh.forum.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

/**
 * @author guowenyu
 * @date 2020/5/31
 */
//@Component
public class AdminTask {

    @Autowired
    private CountRecordDAO countRecordDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private CommentDAO commentDAO;
    @Autowired
    private ContentDAO contentDAO;

//    @Scheduled(initialDelay = 100000, fixedDelay = 3600 * 24)
    public void saveCntRecord() {
        String today = DateUtil.format(new Date());
        Optional<CountRecord> optional = countRecordDAO.findAll().stream()
                .filter(r -> today.equals(r.getCreateTime())).findFirst();
        CountRecord record = optional.orElseGet(CountRecord::new);
        record.setUserCnt((int) userDAO.count());
        record.setCommentCnt((int) commentDAO.count());
        record.setContentCnt((int) contentDAO.count());
        countRecordDAO.save(record);
    }

}
