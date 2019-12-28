package com.itheima.travel.web.servlet;

import com.itheima.travel.entity.PageBean;
import com.itheima.travel.entity.Route;
import com.itheima.travel.service.RouteService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/route")
public class RouteServlet extends BaseServlet {
    private RouteService routeService = new RouteService();

    /**
     * 通过cid获取某个分类下精选线路
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void findMostFavoriteRouteByCid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.得到cid的值
        int cid = Integer.parseInt(request.getParameter("cid"));
        //2.调用业务层得到相应的线路
        List<Route> routeList = routeService.findMostFavoriteRouteByCid(cid);
        //3.转成json字符串
        String json = mapper.writeValueAsString(routeList);
        //4.打印到浏览器端
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print(json);
    }

    /**
     * 查询某个分类下一页的数据
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void findRouteListByCid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.得到cid和current
        int cid = Integer.parseInt(request.getParameter("cid"));
        int current = Integer.parseInt(request.getParameter("current"));

        //从客户端提交过来的参数
        String rname = request.getParameter("rname");
        if (rname == null) {
            rname = "";//默认值
        }

        //2.调用业务层得到PageBean对象
        PageBean<Route> pageBean = routeService.getPageBean(cid, current, rname);

        //3.将Pagebean对象转成JSON
        String json = mapper.writeValueAsString(pageBean);

        //4.打印到浏览器端
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print(json);
    }

}
