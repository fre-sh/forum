package com.fresh.forum.model;


import com.alibaba.fastjson.JSON;
import com.fresh.forum.util.DateUtil;
import org.apache.commons.lang.time.DateUtils;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@EntityListeners({GenericEntityListener.class})
public class BaseEntity {
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Integer id;

  /**
   * 0-正常 1-删除 2-其他无效情况
   */
  @Column(name = "status", columnDefinition = "int default 0")
  private Integer status = 0;

  private Date createdDate;

  private Date updatedDate;

  public String getCreateTime() {
    return DateUtil.format(createdDate);
  }

  public String getUpdateTime() {
    return DateUtil.format(updatedDate);
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }

  public Date getUpdatedDate() {
    return updatedDate;
  }

  public void setUpdatedDate(Date updatedDate) {
    this.updatedDate = updatedDate;
  }

  public String toString(){
   return JSON.toJSONString(this);
  }
}
