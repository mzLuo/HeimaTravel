package com.itheima.travel.service.impl;

import com.itheima.travel.dao.IFavoriteDao;
import com.itheima.travel.entity.Favorite;
import com.itheima.travel.entity.PageBean;
import com.itheima.travel.entity.Route;
import com.itheima.travel.entity.User;
import com.itheima.travel.service.IFavoriteService;
import com.itheima.travel.utils.DaoInstanceFactory;
import com.itheima.travel.utils.SqlSessionUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    /**
     * 查询收藏的一页数据，封装成PageBean对象
     *
     * @param uid     哪个用户
     * @param current 第几页
     * @return
     */
    @Override
    public PageBean<Favorite> getPageBean(int uid, int current) {
        //1.指定size属性
        int size = 4;
        //2.得到data属性
        int begin = (current - 1) * size;
        List<Map<String, Object>> list = favoriteDao.findFavoriteListByPage(uid, begin, size);
        //将每个map重新封装成favorite对象
        //2.1创建一个集合
        List<Favorite> favorites = new ArrayList<>();
        for(Map<String,Object> map : list) {
            //每次创建一个收藏对象
            Favorite favorite = new Favorite();
            Route route = new Route();
            try {
                BeanUtils.populate(favorite,map);
                BeanUtils.populate(route,map);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            //将route设置成favorite的属性
            favorite.setRoute(route);
            //将封装好的对象添加到集合中
            favorites.add(favorite);
        }
        //3.得到count属性
        int count = favoriteDao.getCount(uid);
        //4.封装四个属性
        PageBean<Favorite> pageBean = new PageBean<>(favorites, count, current, size);
        return pageBean;
    }
}
