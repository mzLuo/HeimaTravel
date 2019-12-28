package com.itheima.travel.service;

import com.itheima.travel.dao.RouteDao;
import com.itheima.travel.entity.PageBean;
import com.itheima.travel.entity.Route;
import com.itheima.travel.utils.DaoInstanceFactory;

import java.util.List;

/**
 * 线路的业务类
 */
public class RouteService {

    private RouteDao routeDao = DaoInstanceFactory.getBean(RouteDao.class);

    /**
     * 查询首页某个分类的精选线路
     */
    public List<Route> findMostFavoriteRouteByCid(int cid) {
        return routeDao.findMostFavoriteRouteByCid(cid);
    }

    /**
     * 查询一页数据，封装成PageBean对象
     * @param cid 分类的外键
     * @param current 第几页
     */
    public PageBean<Route> getPageBean(int cid, int current, String rname) {
        //1.封装用户提交的2个参数：current, size
        int size = 3;

        //2.封装从数据库中查询出来的2个参数：data, count
        int begin = (current - 1) * size;
        List<Route> data = routeDao.getRoutesByPage(cid, begin, size, rname);
        int count = routeDao.getCountByCid(cid, rname);

        //3.创建PageBean对象
        PageBean<Route> pageBean = new PageBean<>(data, count, current, size);

        //4.返回PageBean对象
        return pageBean;
    }

}