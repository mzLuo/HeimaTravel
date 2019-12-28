package com.itheima.travel.dao;

import com.itheima.travel.entity.Route;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RouteDao {
    /**
     * 查询某个分类的线路收藏人数最多的6条，按降序排序
     */
    @Select("SELECT * FROM tab_route WHERE cid=#{cid} ORDER BY COUNT DESC LIMIT 0,6;")
    List<Route> findMostFavoriteRouteByCid(int cid);

    /**
     * 查询某个分类一定有多少条线路
     */
    @Select("select count(1) from tab_route where cid=#{cid} and rname like \"%\"#{rname}\"%\"")
    int getCountByCid(@Param("cid") int cid, @Param("rname") String rname);

    /**
     * 查询某个分类一页的数据
     * @param cid 分类的外键
     * @param begin 从第几行开始
     * @param size 页面的大小
     */
    @Select("select * from tab_route where cid=#{cid} and rname like \"%\"#{rname}\"%\" limit #{begin},#{size}")
    List<Route> getRoutesByPage(@Param("cid") int cid, @Param("begin") int begin, @Param("size") int size, @Param("rname")String rname);
}
