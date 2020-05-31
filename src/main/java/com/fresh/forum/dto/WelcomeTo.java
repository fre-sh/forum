package com.fresh.forum.dto;

import com.fresh.forum.model.Question;

import java.util.List;

/**
 * @author guowenyu
 * @date 2020/5/31
 */
public class WelcomeTo {

    /**
     * 提问
     */
    private List<Question> questions;

    /**
     * 总数
     */
    private Integer userCnt;
    private Integer commentCnt;
    private Integer contentCnt;

    private List<String> dates;
    /**
     * 新增数
     */
    private List<Integer> userCnts;
    private List<Integer> commentCnts;
    private List<Integer> contentCnts;

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Integer getUserCnt() {
        return userCnt;
    }

    public void setUserCnt(Integer userCnt) {
        this.userCnt = userCnt;
    }

    public Integer getCommentCnt() {
        return commentCnt;
    }

    public void setCommentCnt(Integer commentCnt) {
        this.commentCnt = commentCnt;
    }

    public Integer getContentCnt() {
        return contentCnt;
    }

    public void setContentCnt(Integer contentCnt) {
        this.contentCnt = contentCnt;
    }

    public List<String> getDates() {
        return dates;
    }

    public void setDates(List<String> dates) {
        this.dates = dates;
    }

    public List<Integer> getUserCnts() {
        return userCnts;
    }

    public void setUserCnts(List<Integer> userCnts) {
        this.userCnts = userCnts;
    }

    public List<Integer> getCommentCnts() {
        return commentCnts;
    }

    public void setCommentCnts(List<Integer> commentCnts) {
        this.commentCnts = commentCnts;
    }

    public List<Integer> getContentCnts() {
        return contentCnts;
    }

    public void setContentCnts(List<Integer> contentCnts) {
        this.contentCnts = contentCnts;
    }
}
