package com.fresh.forum.repository;

import com.fresh.forum.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {

    User findByName(String name);

}
