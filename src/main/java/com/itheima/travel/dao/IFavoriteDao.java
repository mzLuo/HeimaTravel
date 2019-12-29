package com.itheima.travel.dao;

import com.itheima.travel.entity.Favorite;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
    @Insert("INSERT INTO tab_favorite VALUES(#{route.rid}, NOW(), #{user.uid});")
    int addFavorite(Favorite favorite);

    /**
     * 更新这些线路收藏的人数
     */
    @Update("update tab_route SET COUNT=COUNT+1 WHERE rid=#{rid}")
    int updateRouteFavoriteNum(int rid);
}
