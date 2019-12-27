package com.itheima.travel.dao;

import com.itheima.travel.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

/**
 * 用户的DAO
 */
public interface UserDao {
    /**
     * 添加用户
     */
    @Insert("insert into tab_user values(null, #{username},#{password},#{name},#{birthday},#{sex},#{telephone},#{email})")
    int addUser(User user);

    /**
     * 通过用户名查找指定的用户
     * @return 如果存在就返回用户对象，不存在就返回空
     */
    @Select("select * from tab_user where username = #{username}")
    User findUserByName(String username);

}
