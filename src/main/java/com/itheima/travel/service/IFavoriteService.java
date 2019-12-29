package com.itheima.travel.service;

import com.itheima.travel.entity.User;

/**
 * 收藏的业务接口
 */
public interface IFavoriteService {
    /**
     * 如果已经收藏，返回true
     */
    boolean isFavoriteByRidAndUserId(int rid,int uid);

    /**
     * 添加收藏
     */
    void addFavorite(int rid, User user);
}
