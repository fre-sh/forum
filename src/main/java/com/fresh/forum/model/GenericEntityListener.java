package com.fresh.forum.model;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

public class GenericEntityListener
{
  @PrePersist
  public void prePersist(BaseEntity entity)
  {
    entity.setCreatedDate(new Date());
    entity.setUpdatedDate(entity.getCreatedDate());
  }

  @PreUpdate
  public void preUpdate(BaseEntity entity)
  {
    entity.setUpdatedDate(new Date());
  }
}