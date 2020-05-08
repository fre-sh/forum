package com.fresh.forum.repository;

import com.fresh.forum.model.LoginTicket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginTicketDAO extends JpaRepository<LoginTicket, Integer> {

    LoginTicket findByTicket(String ticket);

}
