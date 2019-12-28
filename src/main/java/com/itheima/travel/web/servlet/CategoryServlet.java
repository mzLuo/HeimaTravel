package com.itheima.travel.web.servlet;

import com.itheima.travel.service.CategoryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/category")
public class CategoryServlet extends BaseServlet {
    private CategoryService categoryService = new CategoryService();

    /**
     * 查询所有的分类
     */
    private void findAllCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.调用业务层方法得到分类JSON字符串
        String json = categoryService.findAllCategory();
        //2.设置响应类型为JSON
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();

        //3.打印到浏览器端
        out.print(json);
    }
}
