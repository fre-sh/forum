package com.fresh.forum.service;

import com.fresh.forum.dto.EntityType;
import com.fresh.forum.dao.FollowDAO;
import com.fresh.forum.model.FollowRelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FollowService {

    @Autowired
    private FollowDAO followDAO;

    /**
     * 获取关注列表
     * @param userId
     * @param type
     * @return
     */
    public List<FollowRelation> getFollowObjs(int userId, EntityType type) {
        return followDAO.findByUserIdAndEntityType(userId, type);
    }

    /**
     * 获取粉丝列表
     * @param type
     * @param entityId
     * @return
     */
    public List<FollowRelation> getFollowers(EntityType type, int entityId) {
        return followDAO.findByEntityTypeAndEntityId(type, entityId);
    }

    /**
     * 关注、收藏
     * @param userId
     * @param entityType
     * @param entityId
     * @return
     */
    public FollowRelation follow(int userId, EntityType entityType, int entityId) {
        FollowRelation relation = new FollowRelation();
        relation.setUserId(userId);
        relation.setEntityId(entityId);
        relation.setEntityType(entityType);
        return followDAO.save(relation);
    }

    public void disFollow(int userId, EntityType entityType, int entityId) {
        followDAO.deleteByUserIdAndEntityTypeAndEntityId(userId, entityType, entityId);
    }

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
