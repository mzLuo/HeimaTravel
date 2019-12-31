package com.itheima.travel.dao;

import com.itheima.travel.entity.Favorite;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface IFavoriteDao {
    /**
     * 通过rid和uid查询一条收藏记录
     */
    @Select("SELECT * FROM tab_favorite WHERE rid=#{rid} AND uid=#{uid}")
    Favorite findFavoriteByRidAndUserId(@Param("rid") int rid,@Param("uid") int uid);

    /**
     * 向收藏表中添加1条记录
     * route和user是favorite的属性名
     */
//    @Insert("insert into tab_favorite VALUES(#{route.rid}, NOW(), #{user.uid});")
    @Insert("insert into tab_favorite values(#{route.rid}, now(), #{user.uid});")
    int addFavorite(Favorite favorite);

    /**
     * 更新这些线路收藏的人数
     */
//    @Update("update tab_route SET COUNT=COUNT+1 WHERE rid=#{rid}")
    @Update("update tab_route set count=count+1 where rid=#{rid}")
    int updateRouteFavoriteNum(int rid);

    /**
     * 查询某个用户所有收藏的线路个数
     */
    @Select("select count(1) from tab_favorite where uid=#{uid}")
    int getCount(int uid);

    /**
     * 查询某个用户所有收藏的线路，分页查询，每页显示4条
     */
    @Select("select * from tab_favorite f inner join tab_route r on r.`rid` = f.`rid` where f.uid=#{uid} order by f.date desc limit #{begin}, #{size}")
    List<Map<String,Object>> findFavoriteListByPage(@Param("uid") int uid, @Param("begin") int begin, @Param("size") int size);


}
