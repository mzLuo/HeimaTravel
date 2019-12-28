package com.itheima.travel.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.travel.dao.CategoryDao;
import com.itheima.travel.entity.Category;
import com.itheima.travel.utils.DaoInstanceFactory;
import com.itheima.travel.utils.JedisUtils;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * 分类的业务层
 */
public class CategoryService {
    private CategoryDao categoryDao = DaoInstanceFactory.getBean(CategoryDao.class);

    /**
     * 查询所有的分类
     */
    public String findAllCategory() {
        //1.从Redis中去查找数据
        Jedis jedis = JedisUtils.getJedis();
        String categories = jedis.get("categories");
        //2.如果为空，从mysql中查询
        if (categories == null) {
            List<Category> categoryList = categoryDao.findAllCategory();
            //3.查询到以后，转成JSON对象
            try {
                categories = new ObjectMapper().writeValueAsString(categoryList);
                //4.放到redis中
                jedis.set("categories",categories);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        jedis.close();
        //5.返回json字符串
        return categories;
    }

}
