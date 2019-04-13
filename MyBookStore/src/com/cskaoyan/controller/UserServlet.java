package com.cskaoyan.controller;

import com.cskaoyan.bean.User;
import com.cskaoyan.service.UserService;
import com.cskaoyan.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

@WebServlet(name = "UserServlet",urlPatterns = "/UserServlet")
public class UserServlet extends HttpServlet {

    UserService service = new UserServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        String op = request.getParameter("op");
        if (op != null && !op.isEmpty()) {
            switch (op) {
                case "regist":
                    regist(request,response);
                    break;
                case "login":
                    login(request,response);
                    break;
                case "lgout":
                    lgout(request,response);
                    break;
                case "updateUser":
                    updateUser(request,response);
                    break;
                default:
                    break;
            }
        }

    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uid = request.getParameter("uid");
        String nickname = request.getParameter("nickname");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String birthday = request.getParameter("birthday");
        String username = request.getParameter("username");
        String updatetime = request.getParameter("updatetime");

        User user = new User();
        user.setUid(Integer.parseInt(uid));
        user.setUsername(username);
        user.setPassword(password);
        user.setUpdatetime(updatetime);
        user.setNickname(nickname);
        user.setEmail(email);
        user.setBirthday(birthday);

        try {
            boolean b = service.UpdateUserService(uid, nickname, password, email, birthday);
            if (b) {
                HttpSession session = request.getSession();
                session.removeAttribute("user");
                HttpSession session1 = request.getSession();
                session1.setAttribute("user", user);
                response.getWriter().println("修改信息成功！即将跳转！");
                response.setHeader("refresh", "3;url=" + request.getContextPath() + "/user/personal.jsp");
            } else {
                response.getWriter().println("修改信息失败！请重试！");
                response.setHeader("refresh", "3;url=" + request.getContextPath() + "/user/personal.jsp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("异常！请重试！");
            response.setHeader("refresh", "3;url=" + request.getContextPath() + "/user/personal.jsp");
        }
    }

    /**
     * 用户退出登录
     * @param request
     * @param response
     */
    private void lgout(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession(false);
        session.removeAttribute("user");
        response.getWriter().println("已退出登陆！即将跳转首页！");
        response.setHeader("refresh","3;url=" + request.getContextPath() + "/index.jsp");
    }

    /**
     * 用户登录
     * @param request
     * @param response
     */
    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //验证码
        String verifyCode = request.getParameter("verifyCode");
        //判断验证码对不对？当前实现的验证码是什么？
        String  code = (String) request.getSession(true).getAttribute("checkcode_session");

        if (!code.equals(verifyCode)) {
            response.getWriter().println("验证错误，请重试！");
            String contextPath = request.getContextPath();
            response.setHeader("refresh","3;url=" + contextPath + "/user/login.jsp");
            return;
        }

        if (null != username && null != password) {
            try {
                User user = service.IsUserExistsService(username,password);
                if (user != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("user",user);
                    response.getWriter().println("登陆成功！即将跳转！");
                    String contextPath = request.getContextPath();
                    response.setHeader("refresh","3;url=" + contextPath + "/index.jsp");
                } else {
                    response.getWriter().println("登陆失败！请重试！");
                    String contextPath = request.getContextPath();
                    response.setHeader("refresh","3;url=" + contextPath + "/user/login.jsp");
                    return;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.getWriter().println("登陆异常！请重试！");
                String contextPath = request.getContextPath();
                response.setHeader("refresh","3;url=" + contextPath + "/user/login.jsp");
            }
        }

    }

    /**
     * 用户注册
     * @param request
     * @param response
     */
    private void regist(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String username = request.getParameter("username");
        String nickname = request.getParameter("nickname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String birthday = request.getParameter("birthday");

        String emailMsg = "邮箱验证";
        Boolean emailExist = service.sendMail(email, emailMsg);
        //获取当前系统时间，将格式化为数据库中datetime的格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String updateTime = df.format(new Date());// new Date()为获取当前系统时间

        User user = new User();
        user.setUsername(username);
        user.setBirthday(birthday);
        user.setPassword(password);
        user.setEmail(email);
        user.setNickname(nickname);
        user.setUpdatetime(updateTime);

        if (emailExist) {
            try {
                boolean b = service.AddUserService(user);
                if (b) {
                    HttpSession session = request.getSession();
                    session.setAttribute("user", user);
                    response.getWriter().println("注册成功！请登录！");
                    response.setHeader("refresh", "3;url=" + request.getContextPath() + "/user/login.jsp");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.getWriter().println("注册失败！请重试！");
                response.setHeader("refresh", "3;url=" + request.getContextPath() + "/user/regist.jsp");
            }
        } else {
            response.getWriter().println("邮箱无效！请重试！");
            response.setHeader("refresh", "3;url=" + request.getContextPath() + "/user/regist.jsp");
        }
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
