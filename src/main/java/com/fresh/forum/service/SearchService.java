package com.fresh.forum.service;

import com.fresh.forum.dao.ContentDAO;
import com.fresh.forum.dao.LoginTicketDAO;
import com.fresh.forum.dao.QuestionDAO;
import com.fresh.forum.dao.UserDAO;
import com.fresh.forum.dto.SearchVO;
import com.fresh.forum.dto.ViewObject;
import com.fresh.forum.model.LoginTicket;
import com.fresh.forum.model.User;
import com.fresh.forum.util.WendaUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class SearchService {
    private static final Logger logger = LoggerFactory.getLogger(SearchService.class);
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private LoginTicketDAO loginTicketDAO;
    @Autowired
    private ContentDAO contentDAO;
    @Autowired
    private QuestionDAO questionDAO;

    public List<SearchVO> searchAll(String keyword) {
        List<SearchVO> vos = new ArrayList<>();
        vos.addAll(searchUser(keyword));
        vos.addAll(searchContent(keyword));
        vos.addAll(searchQuestion(keyword));
        vos.sort(new Comparator<SearchVO>() {
            @Override
            public int compare(SearchVO o1, SearchVO o2) {
                return (int) (o2.getDate().getTime() - o1.getDate().getTime());
            }
        });
        return vos;
    }

    public List<SearchVO> searchUser(String keyword){
        return userDAO.findByNameContains(keyword).stream()
                .map(SearchVO::new)
                .map(vo -> highLight(vo, keyword))
                .collect(Collectors.toList());
    }

    public List<SearchVO> searchContent(String keyword){
        return contentDAO.findByTitleContains(keyword).stream()
                .map(SearchVO::new)
                .map(vo -> highLight(vo, keyword))
                .collect(Collectors.toList());
    }

    public List<SearchVO> searchQuestion(String keyword){
        return questionDAO.findByTitleContains(keyword).stream()
                .map(SearchVO::new)
                .map(vo -> highLight(vo, keyword))
                .collect(Collectors.toList());
    }

    private SearchVO highLight(SearchVO searchVO, String keyWord) {
        searchVO.setEntityTitle(WendaUtil.highLight(searchVO.getEntityTitle(), keyWord));
        if (StringUtils.isNotEmpty(searchVO.getContent())) {
            searchVO.setContent(WendaUtil.highLight(searchVO.getContent(), keyWord));
        }
        return searchVO;
    }
}
