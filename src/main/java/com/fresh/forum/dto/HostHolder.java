package com.fresh.forum.dto;

import com.fresh.forum.dao.UserDAO;
import com.fresh.forum.model.User;
import com.fresh.forum.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HostHolder {
    private static ThreadLocal<User> users = new ThreadLocal<>();

    @Autowired
    UserDAO userDAO;

    public User getUser() {
        // todo 后台完成后需注释掉（否则默认登录此用户）
//        if (users.get() == null) {
//            return userDAO.findById(WendaUtil.ANONYMOUS_USERID);
//        }
        return users.get();
    }

    public void setUser(User user) {
        users.set(user);
    }

    public void clear() {
        users.remove();;
    }
}
