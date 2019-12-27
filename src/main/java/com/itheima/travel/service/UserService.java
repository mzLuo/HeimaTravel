package com.itheima.travel.service;

import com.itheima.travel.dao.UserDao;
import com.itheima.travel.entity.User;
import com.itheima.travel.exception.CustomerMsgException;
import com.itheima.travel.utils.DaoInstanceFactory;
import com.itheima.travel.utils.Md5Utils;

/**
 * 用户的业务层
 */
public class UserService {
    //依赖于DAO
    private UserDao userDao = DaoInstanceFactory.getBean(UserDao.class);

    /**
     * 判断用户是否存在
     * @return 如果存在返回true，否则返回false
     */
    public boolean isUserExists(String username){
        //返回user对象或者null
        return userDao.findUserByName(username) != null;
    }

    /**
     * 注册的功能
     * 向数据库写入用户，但密码需要加密
     */
    public int register(User user) {
        //先对密码加密
        String password = user.getPassword();
        String md5 = Md5Utils.getMd5(password);
        user.setPassword(md5);
        return userDao.addUser(user);
    }

    /**
     * 登录
     */
    public User login(String username, String password) throws CustomerMsgException {
        //1.调用Dao层查询用户是否存在
        User user = userDao.findUserByName(username);
        //2.如果不存在，抛出异常
        if (user == null) {
            throw new CustomerMsgException("用户不存在");
        }
        //3.存在就比较密码是否相同
        String pwd = user.getPassword();
        String md5 = Md5Utils.getMd5(password);
        if (!md5.equals(pwd)) {
            throw new CustomerMsgException("密码错误");
        }
        return user;
    }
}