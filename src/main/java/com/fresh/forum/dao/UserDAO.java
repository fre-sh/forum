package com.fresh.forum.dao;

import com.fresh.forum.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface UserDAO extends JpaRepository<User, Integer> {

    User findByName(String name);

    List<User> findByNameContains(String name);

    @Query(" from User u where u.id = ?1")
    User findById(int id);

    void deleteByIdIn(List<Integer> ids);
}
