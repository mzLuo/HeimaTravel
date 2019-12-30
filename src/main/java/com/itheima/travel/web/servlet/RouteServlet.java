package com.itheima.travel.web.servlet;

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
import java.util.HashMap;
import java.util.List;

@WebServlet("/route")
public class RouteServlet extends BaseServlet {
    private RouteService routeService = new RouteService();
    private IFavoriteService favoriteService = new FavoriteServiceImpl();  //收藏的业务类

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

    /**
     * 显示一条线路的详情
     */
    private void findRouteByRid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.得到rid的值
        int rid = Integer.parseInt(request.getParameter("rid"));
        //2.调用业务层得到一条线路
        Route route = routeService.findRouteByRid(rid);

        //3.转成JSON对象
        String json = mapper.writeValueAsString(route);

        //4.打印到页面
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print(json);
    }

    /*
    判断收藏按钮是否可用
     */
    private void isFavoriteByRid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();

        //1.判断用户是否登录
        HttpSession session = request.getSession();
        //2.如果没有登录，打印false
        User user = (User) session.getAttribute("user");
        if (user == null) {
            out.print(false);
        } else {
            //3.如果已经登录，从会话域中取得uid
            int uid = user.getUid();
            //4.从客户端获取rid
            int rid = Integer.parseInt(request.getParameter("rid"));
            //5.调用业务方法判断线路是否收藏(在成员变量中创建收藏的业务对象)
            boolean isFav = favoriteService.isFavoriteByRidAndUserId(rid, uid);
            //6.如果收藏打印true，否则打印false
            out.print(isFav);
        }
    }

    /**
     * 查询收藏排行榜
     */
    private void findRoutesFavoriteRank(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.得到当前是第几页
        int current = Integer.parseInt(request.getParameter("current"));

        //得到表单提交的查询条件封装成Map对象
        HashMap<String,String> condition = new HashMap<>();
        condition.put("rname",request.getParameter("rname"));
        condition.put("startPrice",request.getParameter("startPrice"));
        condition.put("endPrice",request.getParameter("endPrice"));


        //2.调用业务层得到PageBean对象
        PageBean<Route> pageBean = routeService.getPageBeanByFavoriteRank(current,condition);
        //3.转成JSON打印
        String json = mapper.writeValueAsString(pageBean);
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print(json);

    }
}