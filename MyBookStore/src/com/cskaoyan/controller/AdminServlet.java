package com.cskaoyan.controller;

import com.cskaoyan.bean.Admin;
import com.cskaoyan.bean.PageHelper;
import com.cskaoyan.service.AdminService;
import com.cskaoyan.service.impl.AdminServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "AdminServlet",urlPatterns = "/admin/AdminServlet")
public class AdminServlet extends HttpServlet {
    AdminService service = new AdminServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        String op = request.getParameter("op");

        if (op != null && !op.isEmpty()) {
            switch (op){
                case "addAdmin" :
                    try {
                        add(request,response);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "findAllAdmin":
                    try {
                        show(request,response);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "updateAdmin":
                    update(request,response);
                    break;
                case "deleteOne":
                    delete(request,response);
                    break;
                case "login":
                    login(request,response);
                    break;
                    default:
                        break;
            }
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (null != username && null != password) {
            try {
                Admin admin = service.IsAdminExist(username,password);
                if (admin != null) {
                    //登陆之后，使用session记录用户登录状态
                    HttpSession session = request.getSession();
                    session.setAttribute("admin",admin);
                    String contextPath = request.getContextPath();
                    response.setHeader("refresh","3;url=" + contextPath + "/admin/main.jsp");
                    return;
                } else {
                    response.getWriter().println("用户名或者密码不对！请重试！");
                    String contextPath = request.getContextPath();
                    response.setHeader("refresh","3;url=" + contextPath + "/admin/index.jsp");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.getWriter().println("登陆异常！请重试！");
                String contextPath = request.getContextPath();
                response.setHeader("refresh","3;url=" + contextPath + "/admin/index.jsp");

            }
        }

    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String aid = request.getParameter("aid");
        try {
            boolean b = service.deleteOneService(aid);
            if (b) {
                response.getWriter().println("删除成功！即将跳转！");
                String contextPath = request.getContextPath();
                response.setHeader("refresh","3;url=" + contextPath + "/admin/AdminServlet?op=findAllAdmin&num=1");
            } else {
                response.getWriter().println("删除失败！请重试！");
                String contextPath = request.getContextPath();
                response.setHeader("refresh","3;url=" + contextPath + "/admin/AdminServlet?op=findAllAdmin&num=1");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("异常！请重试！");
            String contextPath = request.getContextPath();
            response.setHeader("refresh","3;url=" + contextPath + "/admin/AdminServlet?op=findAllAdmin&num=1");
        }
    }

    private void update(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String password1 = request.getParameter("password1");
        if (password.equals(password1)) {
            try {
                boolean b = service.updateAdminService(username,password);
                if (b) {
                    response.getWriter().println("修改成功！即将跳转！");
                    String contextPath = request.getContextPath();
                    response.setHeader("refresh","3;url=" + contextPath + "/admin/AdminServlet?op=findAllAdmin&num=1");
                } else {
                    response.getWriter().println("修改失败！请重试！");
                    String contextPath = request.getContextPath();
                    response.setHeader("refresh","3;url=" + contextPath + "/admin/admin/updateAdmin.jsp?username=" + username);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.getWriter().println("异常！请重试！");
                String contextPath = request.getContextPath();
                response.setHeader("refresh","3;url=" + contextPath + "/admin/admin/updateAdmin.jsp?username=" + username);
            }

        } else {
            response.getWriter().println("两次密码不一致！请重试！");
            String contextPath = request.getContextPath();
            response.setHeader("refresh","3;url=" + contextPath + "/admin/admin/updateAdmin.jsp?username=" + username);
        }
    }

    private void show(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        String num = request.getParameter("num");
        PageHelper pageAdmin = service.showPageAdminService(num);
        request.setAttribute("page",pageAdmin);
        request.getRequestDispatcher("/admin/admin/adminList.jsp").forward(request,response);
    }

    private void add(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String password1 = request.getParameter("password1");

        boolean isAdminNameValid = service.IsAdminNameValidService(username);
        if(isAdminNameValid) {
            //用户已存在
            response.getWriter().println("用户名已存在！请重试！");
            String contextPath = request.getContextPath();
            response.setHeader("refresh","3;url=" + contextPath + "/admin/admin/addAdmin.jsp");
        }else {
            //用户名不存在
            if (username!=null && !username.isEmpty() &&
                    password != null && !password.isEmpty() &&
                    password1 != null && !password1.isEmpty()
                    ) {
                if (password.equals(password1)){
                    //两次密码一致
                    try {
                        boolean b = service.addAdminService(username, password);
                        if (b) {
                            response.getWriter().println("添加成功！即将跳转！");
                            String contextPath = request.getContextPath();
                            response.setHeader("refresh","3;url=" + contextPath + "/admin/admin/addAdmin.jsp");
                        } else {
                            response.getWriter().println("添加失败！请重试！");
                            String contextPath = request.getContextPath();
                            response.setHeader("refresh","3;url=" + contextPath + "/admin/admin/addAdmin.jsp");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        response.getWriter().println("异常！请重试！");
                        String contextPath = request.getContextPath();
                        response.setHeader("refresh","3;url=" + contextPath + "/admin/admin/addAdmin.jsp");
                    }
                } else {
                    response.getWriter().println("两次密码输入不一致！请重试！");
                    String contextPath = request.getContextPath();
                    response.setHeader("refresh","3;url=" + contextPath + "/admin/admin/addAdmin.jsp");
                }

            } else {
                response.getWriter().println("不能输入为空！请重试！");
                String contextPath = request.getContextPath();
                response.setHeader("refresh","3;url=" + contextPath + "/admin/admin/addAdmin.jsp");
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
