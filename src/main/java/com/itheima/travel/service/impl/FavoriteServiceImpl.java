package com.itheima.travel.service.impl;

import com.itheima.travel.dao.IFavoriteDao;
import com.itheima.travel.entity.Favorite;
import com.itheima.travel.entity.Route;
import com.itheima.travel.entity.User;
import com.itheima.travel.service.IFavoriteService;
import com.itheima.travel.utils.DaoInstanceFactory;
import com.itheima.travel.utils.SqlSessionUtils;
import org.apache.ibatis.session.SqlSession;

/**
 * 收藏的业务实现类
 */
public class FavoriteServiceImpl implements IFavoriteService {
    /**
     * 依赖于DAO
     */
    private IFavoriteDao favoriteDao = DaoInstanceFactory.getBean(IFavoriteDao.class);

    /**
     * 如果已经收藏，返回true
     *
     * @param rid
     * @param uid
     */
    @Override
    public boolean isFavoriteByRidAndUserId(int rid, int uid) {
        //不为空表示已经收藏，返回true
        return favoriteDao.findFavoriteByRidAndUserId(rid,uid) != null;
    }

    /**
     * 添加收藏
     *
     * @param rid
     * @param user
     */
    @Override
    public void addFavorite(int rid, User user) {
        //1.创建favorite对象
        Favorite favorite = new Favorite();
        //只要封装route.rid和user.uid即可
        Route route = new Route();
        route.setRid(rid);
        //封装favorite属性
        favorite.setRoute(route);
        favorite.setUser(user);

        //2.得到会话对象
        SqlSession session = SqlSessionUtils.getSession();
        IFavoriteDao favoriteDao = session.getMapper(IFavoriteDao.class);

        try {
            //3.调用方法，添加收藏对象
            favoriteDao.addFavorite(favorite);
            //4.调用方法，更新线路的数量
            favoriteDao.updateRouteFavoriteNum(rid);
            //5.如果没有异常，提交事务
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            //6.如果有事务，回滚事务
            session.rollback();
        } finally {
            //7.关闭会话
            SqlSessionUtils.closeSession();
        }
    }
}
