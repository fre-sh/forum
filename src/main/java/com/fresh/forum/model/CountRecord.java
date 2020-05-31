package com.fresh.forum.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author guowenyu
 * @date 2020/5/31
 */
@Entity
@Table(name = "count_record")
public class CountRecord extends BaseEntity {

    private Integer userCnt;
    private Integer contentCnt;
    private Integer commentCnt;

    public Integer getUserCnt() {
        return userCnt;
    }

    public void setUserCnt(Integer userCnt) {
        this.userCnt = userCnt;
    }

    public Integer getContentCnt() {
        return contentCnt;
    }

    public void setContentCnt(Integer contentCnt) {
        this.contentCnt = contentCnt;
    }

    public Integer getCommentCnt() {
        return commentCnt;
    }

    public void setCommentCnt(Integer commentCnt) {
        this.commentCnt = commentCnt;
    }
}
