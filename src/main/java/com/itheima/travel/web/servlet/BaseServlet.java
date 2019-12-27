package com.itheima.travel.web.servlet;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

@WebServlet("/BaseServlet")
public class BaseServlet extends HttpServlet {
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        Class<? extends Servlet> clazz = this.getClass();

        try {
            Method method = clazz.getDeclaredMethod(action, HttpServletRequest.class, HttpServletResponse.class);
            System.out.println(this + "的方法:" + method.getName() + "被调用");
            method.setAccessible(true);
            method.invoke(this, request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
