package com.cskaoyan.controller;

import com.cskaoyan.service.OrderService;
import com.cskaoyan.service.impl.OrderServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CartServlet",urlPatterns = "/CartServlet")
public class CartServlet extends HttpServlet {

    OrderService service = new OrderServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String op = request.getParameter("op");
        if (null != op && !op.isEmpty()) {
            switch (op) {
                case "addCart":
                    add(request,response);
                    break;
                    default:
                        break;
            }
        }
    }

    private void add(HttpServletRequest request, HttpServletResponse response) {

        String pid = request.getParameter("pid");
        String uid = request.getParameter("uid");



    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
