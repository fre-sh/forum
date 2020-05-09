package com.fresh.forum.service;

import com.fresh.forum.dto.EntityType;
import com.fresh.forum.repository.FollowDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FollowService {

    @Autowired
    private FollowDAO followDAO;

    /**
     * 获得实体的粉丝数
     * @param entityType
     * @param entityId
     * @return
     */
    public int getFollowerCount(EntityType entityType, int entityId) {
        return followDAO.countByEntityTypeAndEntityId(entityType, entityId);
    }

    /**
     * 用户关注实体数
     * @param userId
     * @param entityType
     * @return
     */
    public int getFolloweeCount(int userId, EntityType entityType) {
        return followDAO.countByUserIdAndEntityType(userId, entityType);
    }

    public boolean isFollower(int fromUser, EntityType entityType, int entity) {
        return followDAO.countByUserIdAndEntityTypeAndEntityId(fromUser, entityType, entity) > 0;
    }
}
