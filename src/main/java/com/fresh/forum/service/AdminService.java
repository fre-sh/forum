package com.fresh.forum.service;

import com.fresh.forum.dao.*;
import com.fresh.forum.dto.WelcomeTo;
import com.fresh.forum.model.*;
import com.fresh.forum.util.DateUtil;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author guowenyu
 * @date 2020/5/31
 */
@Service
public class AdminService {

    @Autowired
    CountRecordDAO recordDAO;
    @Autowired
    UserDAO userDAO;
    @Autowired
    CommentDAO commentDAO;
    @Autowired
    ContentDAO contentDAO;
    @Autowired
    QuestionDAO questionDAO;

    public WelcomeTo welcome() {
        // 数量记录
        List<String> dates = new ArrayList<>();
        List<Integer> userCnts = new ArrayList<>();
        List<Integer> commentCnts = new ArrayList<>();
        List<Integer> contentCnts = new ArrayList<>();
        Date date = DateUtils.addDays(new Date(), -6);
        for (int i = 0; i < 7; i++) {
            String strDate = DateUtil.format2s(date);
            dates.add(strDate);
            CountRecord record = getCountRecord(date);
            userCnts.add(record.getUserCnt());
            commentCnts.add(record.getCommentCnt());
            contentCnts.add(record.getContentCnt());
            date = DateUtils.addDays(date, 1);
        }
        WelcomeTo dto = new WelcomeTo();
        dto.setDates(dates);
        dto.setUserCnts(userCnts);
        dto.setCommentCnts(commentCnts);
        dto.setContentCnts(contentCnts);

        /**
         * 总数
         */
        dto.setUserCnt((int) userDAO.count());
        dto.setCommentCnt((int) commentDAO.count());
        dto.setContentCnt((int) contentDAO.count());

        /**
         * 提问
         */
        List<Question> questions = questionDAO.findAll(new Sort(Sort.Direction.DESC, "createdDate"));
        dto.setQuestions(questions.subList(0, 5));
        return dto;
    }

    public CountRecord getCountRecord(Date date) {
        String strDate = DateUtil.format2d(date);
        Optional<CountRecord> optional = recordDAO.findAll().stream()
                .filter(r -> r.getCreateTime().startsWith(strDate)).findFirst();
        return optional.orElseGet(() -> {
            CountRecord record = new CountRecord();
            record.setCommentCnt(0);
            record.setUserCnt(0);
            record.setContentCnt(0);
            return recordDAO.save(record);
        });
    }

//    static Random random = new Random();
//    @Autowired
//    UserService userService;
//
//    @PostConstruct
//    public void init() {
//        for (int i = 0; i < 68; i++) {
//            Content tmp = new Content();
//            tmp.setUserId(WendaUtil.ANONYMOUS_USERID);
//            tmp.setContent("auto_article" + i);
//            tmp.setTitle("auto_article" + i);
//            tmp.setContentType(ContentType.article);
//            tmp.setCommentCnt(0);
//            Date date = new Date();
//
//            tmp.setCreatedDate(DateUtils.addDays(date, 0 - i*5));
//            contentDAO.save(tmp);
//        }
//    }
//
//    public static void main(String[] args) {
//        for (int i = 0; i < 10; i++) {
//            Date date = new Date();
//            Date date1 = DateUtils.addDays(date, 0 - random.nextInt(10));
//            System.out.println(DateUtil.format(date1));
//        }
//    }

}
