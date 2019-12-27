package com.itheima.test;

import com.itheima.travel.entity.User;
import com.itheima.travel.service.UserService;
import org.junit.Test;

public class TestUser {
    private UserService userService = new UserService();

    /**
     * 测试用户的注册
     */
    @Test
    public void testRegister() {
        User user = new User(0, "Keith", "123", "柱哥", "1995-03-03", "男", "13578787878", "keith@qq.com");
        int row = userService.register(user);
        System.out.println(row);
    }
}
