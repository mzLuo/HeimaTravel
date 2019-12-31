package com.itheima.travel.dao;

import com.itheima.travel.entity.Route;
import com.itheima.travel.entity.RouteImg;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface RouteDao {
    /**
     * 查询某个分类的线路收藏人数最多的6条，按降序排序
     */
//    @Select("SELECT * FROM tab_route WHERE cid=#{cid} ORDER BY COUNT DESC LIMIT 0,6;")
    @Select("select * from tab_route where cid=#{cid} order by count desc limit 0,6;")
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


    /**
     *查询线路详情，由三张表的数据组成
     * 因为多列组成的一条记录，封装成Map，键表示列名，值这一列表示值
     * @param rid
     * @return
     */
//    @Select("SELECT * FROM tab_category c INNER JOIN tab_route r ON c.cid = r.cid INNER JOIN tab_seller s ON s.sid = r.sid WHERE r.rid = #{rid}")
    @Select("select * from tab_category c inner join tab_route r on c.cid = r.cid inner join tab_seller s on s.sid = r.sid where r.rid = #{rid}")
    Map<String, Object> findRouteByRid(int rid);


    /**
     * 查询某个线路的图片，一条线路对应多张图片
     * @param rid
     * @return
     */
    @Select("select * from tab_route_img where rid=#{rid}")
    List<RouteImg> findRouteImgsByRid(int rid);

    /**
     * 查询所有的线路
     * 封装查询条件
     * @param condition
     * @return
     */
    int getCountByFavoriteRank(@Param("condition") Map<String,String>condition);

    /**
     * 查询一页的数据
     * @param begin
     * @param size
     * @param condition
     * @return
     */
    List<Route> getRoutesFavoriteRankByPage(@Param("begin")int begin, @Param("size") int size, @Param("condition")Map<String,String>condition);
}
