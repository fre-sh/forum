package com.fresh.forum.dto;

/**
 * 用于管理页面查询
 * @author guowenyu
 * @date 2020/5/26
 */
public class Query {

    /**
     * 关键字
     */
    private String kw;

    /**
     * 状态 0-正常 1-删除
     */
    private Integer status;

    private Integer pageNum;

    private Integer pageSize;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getKw() {
        return kw;
    }

    public void setKw(String kw) {
        this.kw = kw;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
