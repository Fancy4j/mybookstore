package com.cskaoyan.controller;

import com.cskaoyan.service.UserService;
import com.cskaoyan.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


@WebServlet(name = "AjaxServlet",urlPatterns = "/AjaxServlet")
public class AjaxServlet extends HttpServlet {

    UserService service = new UserServiceImpl();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");
        if (op != null && !op.isEmpty()) {
            switch (op) {
                case "isUsernameValid":
                    usernameValid(request,response);
                    break;
                case "isEmailValid":
                    emailValid(request,response);
                    break;
                case "isBirthdayValid":
                    birthdayValid(request,response);
                    break;
                    default:
                        break;
            }
        }

    }

    /**
     * 判断生日格式是否合法
     * @param request
     * @param response
     * @throws IOException
     */
    private void birthdayValid(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String birthday = request.getParameter("birthday");
        //yyyy-MM-dd HH:mm:ss
        if (birthday.matches("1[0-9]{3}-((0[1-9])|(1[12]))-((0[1-9])|([12][0-9])|(3[01]))")) {
            response.getWriter().print("isValid");
        } else {
            response.getWriter().print("isNotValid");
        }
    }

    /**
     * 判断邮箱格式是否合法
     * @param request
     * @param response
     * @throws IOException
     */
    private void emailValid(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String email = request.getParameter("email");
        if (email.matches("[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+")) {
            response.getWriter().print("isValid");
        } else {
            response.getWriter().print("isNotValid");
        }
    }

    /**
     * 判断用户名是否可用
     * @param request
     * @param response
     */
    private void usernameValid(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        //拿到异步请求发送过来的用户名？
        //查数据库去判断是不是可用
        boolean valid = false;
        try {
            valid = service.IsUserNameValidService(username);
            if (valid) {
                response.getWriter().print("ok");
            } else {
                response.getWriter().print("exist");
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

    }


}
