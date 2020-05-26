package com.fresh.forum.model;


import com.alibaba.fastjson.JSON;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@EntityListeners({GenericEntityListener.class})
public class BaseEntity
{
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private int id;
  private Date createdDate;

  private Date updatedDate;

  public int getId() {
    return id;
  }

  public void setId(int id) {
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
