package com.cskaoyan.controller;

import com.cskaoyan.bean.Category;
import com.cskaoyan.bean.Product;
import com.cskaoyan.service.CategoryService;
import com.cskaoyan.service.ProductService;
import com.cskaoyan.service.impl.CategoryServiceImpl;
import com.cskaoyan.service.impl.ProductServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "MainServlet",urlPatterns = "/MainServlet")
public class MainServlet extends HttpServlet {
    CategoryService categoryService = new CategoryServiceImpl();
    ProductService productService = new ProductServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        //获取首页上面的分类
        List<Category> categories = categoryService.ShowCategoryService();
        HttpSession session = request.getSession();
        session.setAttribute("categoryList",categories);
        //request.setAttribute("categoryList",categories);

        //滚动图片
        try {
            List<Product> topProduct = productService.showTopProductService();
            HttpSession session2 = request.getSession();
            session2.setAttribute("productTop",topProduct);
            //request.setAttribute("productTop",topProduct);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //热门图片
        List<Product> hotProduct = null;
        try {
            hotProduct = productService.showHotProductService();
            HttpSession session1 = request.getSession();
            session1.setAttribute("hotProducts",hotProduct);
            //request.setAttribute("hotProducts",hotProduct);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("/index.jsp").forward(request,response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
