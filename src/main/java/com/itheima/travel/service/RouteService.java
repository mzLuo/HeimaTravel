package com.itheima.travel.service;

import com.itheima.travel.dao.RouteDao;
import com.itheima.travel.entity.*;
import com.itheima.travel.utils.DaoInstanceFactory;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

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

    /**
     * 将四张表的信息：分类，线路，上家，图片表封装成一个route对象
     * @param rid
     * @return
     */
    public Route findRouteByRid(int rid) {
        //1.调用DAO查询三张表的记录得到Map对象
        Map<String,Object> map = routeDao.findRouteByRid(rid);

        //2.调用DAO查询线路图标，得到List<RouteImg>对象
        List<RouteImg> imgs = routeDao.findRouteImgsByRid(rid);

        //3.将Map中数据复制到三个对象中：Category,Seller,Route
        Category category = new Category();
        Seller seller = new Seller();
        Route route = new Route();

        try {
            BeanUtils.populate(route,map);
            BeanUtils.populate(category,map);
            BeanUtils.populate(seller,map);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //4.将Category，Seller和List<RouteImg>，设置成route对象的属性
        route.setCategory(category);
        route.setSeller(seller);
        route.setRouteImgList(imgs);

        //5.返回route对象
        return route;
    }

}