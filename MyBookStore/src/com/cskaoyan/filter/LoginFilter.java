package com.cskaoyan.filter;

import com.cskaoyan.bean.Admin;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "LoginFilter",urlPatterns = "/admin/*")
public class LoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse)resp;
        Admin admin = (Admin)request.getSession().getAttribute("admin");

        String requestURI = request.getRequestURI();

        if (admin != null) {//有缓存，已经登陆
            chain.doFilter(request,response);
        }else if ("/admin/index.jsp".equals(requestURI) ||
                "/".equals(requestURI) ||
                "/admin/AdminServlet".equals(requestURI) ||
                requestURI.endsWith(".js") ||
                requestURI.endsWith(".css") ||
                requestURI.endsWith(".gif") ||
                requestURI.endsWith(".png") ||
                requestURI.endsWith(".db") ||
                requestURI.endsWith(".jpeg") ||
                requestURI.endsWith(".jgp") ||
                requestURI.endsWith(".MF")
                ) {  //静态资源 adminServlet?op=login?
            chain.doFilter(request,response);
        }
        else {

            System.out.println(requestURI);
            if ("/admin/index.jsp".equals(requestURI) || "/".equals(requestURI)) {
                chain.doFilter(request,response);
            } else {
                response.setHeader("refresh", "3;url=" + request.getContextPath() + "/admin/index.jsp");
            }
        }

    }

    public void init(FilterConfig config) throws ServletException {

    }

}
