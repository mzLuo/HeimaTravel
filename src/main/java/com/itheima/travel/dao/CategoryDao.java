package com.itheima.travel.dao;

import com.itheima.travel.entity.Category;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 分类DAO
 */
public interface CategoryDao {
    /**
     * 查询所有的分类
     */
    @Select("select * from tab_category order by cid")
    List<Category> findAllCategory();
}
