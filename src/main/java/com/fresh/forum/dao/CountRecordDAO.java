package com.fresh.forum.dao;

import com.fresh.forum.dto.EntityType;
import com.fresh.forum.model.CountRecord;
import com.fresh.forum.model.ReadRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CountRecordDAO extends JpaRepository<CountRecord, Integer> {



}
