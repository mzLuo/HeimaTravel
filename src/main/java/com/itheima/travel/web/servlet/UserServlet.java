package com.itheima.travel.web.servlet;

import com.itheima.travel.entity.User;
import com.itheima.travel.exception.CustomerMsgException;
import com.itheima.travel.service.UserService;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * 处理用户的操作
 */
@WebServlet("/user")
public class UserServlet extends BaseServlet {

    private UserService userService = new UserService();

    /**
     * 用户的注册
     */
    private void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //纯文本的输出
        response.setContentType("text/plain;charset=utf-8");
        PrintWriter out = response.getWriter();

        //1.从会话域中得到验证码
        HttpSession session = request.getSession();
        String vcode = (String) session.getAttribute("vcode");
        //用完以后就删除
        session.removeAttribute("vcode");
        //2.得到用户提交的验证码
        String check = request.getParameter("check");
        //3.忽略大小写比较验证码
        if (!check.equalsIgnoreCase(vcode)) {
            //4.如果不相同，打印信息
            out.print("验证码错误");
        }
        //5.如果相同，继续后面操作
        else {
            //得到用户名
            String username = request.getParameter("username");
            //判断用户名是否存在
            boolean exists = userService.isUserExists(username);
            //如果存在就输出信息
            if (exists) {
                out.print("用户名已经存在");
            }
            //不存在就注册
            else {
                //得到表单的数据
                Map<String, String[]> parameterMap = request.getParameterMap();
                //封装成user对象
                User user = new User();
                try {
                    BeanUtils.populate(user, parameterMap);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                //调用业务层写到数据库
                int row = userService.register(user);
                //如果添加成功，打印success
                if (row > 0) {
                    out.print("success");
                }
                //如果失败抛出异常
                else {
                    throw new RuntimeException("注册失败");
                }
            }
        }
    }

    /**
     * 登录
     */
    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain;charset=utf-8");  //纯文本
        PrintWriter out = response.getWriter();

        //1.判断验证码
        HttpSession session = request.getSession();
        String vcode = (String) session.getAttribute("vcode");
        session.removeAttribute("vcode");  //删除验证码
        String check = request.getParameter("check");  //得到提交的验证码
        //如果不相等，打印错误信息
        if (!check.equalsIgnoreCase(vcode)) {
            out.print("验证码错误");
        } else {
            //2.如果验证码正确，调用业务层登录
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            try {
                //3.如果返回user对象，表示登录成功
                User user = userService.login(username, password);
                //将用户对象放在会话域中
                System.out.println("user = " + user);
                session.setAttribute("user", user);
                //打印success给浏览器
                out.print("success");
            } catch (CustomerMsgException e) {
                //4.如果有异常表示登录失败，将异常信息打印到浏览器端去
                e.printStackTrace();
                out.print(e.getMessage());
            }
        }
    }

    /**
     * 获取用户的登录状态
     */
    private void getLoginUserData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain;charset=utf-8");
        PrintWriter out = response.getWriter();

        //1.从会话域中获取用户的信息
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        //2.如果为空，表示没有登录，打印false
        if (user == null) {
            out.print(false);  //这也是一个文本
        }
        //3.如果不为空，得到用户的真实姓名，打印到浏览器
        else {
            out.print(user.getName());
        }
    }

    /**
     * 退出
     */
    private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.销毁会话域
        request.getSession().invalidate();
        //2.重定向到login.html
        response.sendRedirect(request.getContextPath() + "/login.html");
    }
}
