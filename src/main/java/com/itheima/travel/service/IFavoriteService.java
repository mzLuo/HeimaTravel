package com.itheima.travel.service;

import com.itheima.travel.entity.Favorite;
import com.itheima.travel.entity.PageBean;
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

    /**
     * 查询收藏的一页数据，封装成PageBean对象
     * @param uid 哪个用户
     * @param current 第几页
     * @return
     */
    PageBean<Favorite> getPageBean(int uid, int current);
}
