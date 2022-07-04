package com.mall.filter;


import com.mall.common.ApiRestResponse;
import com.mall.common.Constant;
import com.mall.exception.StatusCode;
import com.mall.model.pojo.Category;
import com.mall.model.pojo.User;
import com.mall.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 管理员校验过滤器
 */
public class AdminFilter implements Filter {
    @Autowired
    private UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute(Constant.MALL_USER);
        if(currentUser == null) {
            PrintWriter out = new HttpServletResponseWrapper((HttpServletResponse) servletResponse).getWriter();
            out.write("{\n" +
                    "  \"status\": 10008,\n" +
                    "  \"msg\": \"NEED_LOGIN\",\n" +
                    "  \"data\": null\n" +
                    "}");
            out.flush();
            out.close();
            return ;
        }
        // 校验是否是管理员
        boolean adminRole = userService.checkAdminRole(currentUser);
        if(adminRole) {
            // 是管理员，执行增加操作
            // 放行，去到下一个过滤器
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            PrintWriter out = new HttpServletResponseWrapper((HttpServletResponse) servletResponse).getWriter();
            out.write("{\n" +
                    "  \"status\": 10010,\n" +
                    "  \"msg\": \"NEED_ADMIN\",\n" +
                    "  \"data\": null\n" +
                    "}");
            out.flush();
            out.close();
            return ;
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
