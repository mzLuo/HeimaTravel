package com.itheima.travel.web.servlet;

import com.itheima.travel.entity.Favorite;
import com.itheima.travel.entity.PageBean;
import com.itheima.travel.entity.Route;
import com.itheima.travel.entity.User;
import com.itheima.travel.service.IFavoriteService;
import com.itheima.travel.service.RouteService;
import com.itheima.travel.service.impl.FavoriteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/favorite")
public class FavoriteServlet extends BaseServlet {
    private IFavoriteService favoriteService = new FavoriteServiceImpl();

    private RouteService routeService = new RouteService();

    /**
     * 添加收藏，返回收藏的数量
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();

        //1.判断是否登录，如果没有登录，返回false
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            out.print(false);
        } else {
            //2.如果已经登录，得到rid，从会话域中得到用户对象
            int rid = Integer.parseInt(request.getParameter("rid"));
            //3.调用业务层的代码，实现添加收藏
            favoriteService.addFavorite(rid, user);
            //4.收藏成功以后，查询当前线路的收藏数量
            Route route = routeService.findRouteByRid(rid);
            //5.打印收藏数量给浏览器
            out.print(route.getCount());

        }
    }

    /**
     * 查询某个用户的收藏线路
     */
    private void findFavoriteByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();

        //1.得到会话域中用户对象
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        //2.如果为空，表示会话过期，打印false
        if (user == null) {
            out.print(false);
        }
        //3.不为空，从会话域中得到uid
        else {
            int uid = user.getUid();
            //4.获取当前第几页
            int current = Integer.parseInt(request.getParameter("current"));
            //5.调用业务层得到PageBean对象
            PageBean<Favorite> pageBean = favoriteService.getPageBean(uid, current);
            //6.转成json打印
            String json = mapper.writeValueAsString(pageBean);
            out.print(json);
        }
    }
}
