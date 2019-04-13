package com.cskaoyan.controller;
//dao当中放的是与数据库相关的操作

import com.cskaoyan.bean.Category;
import com.cskaoyan.bean.PageHelper;
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

@WebServlet(name = "CategoryServlet",urlPatterns = "/admin/CategoryServlet")
public class CategoryServlet extends HttpServlet {

    CategoryService service = new CategoryServiceImpl();
    ProductService service1 = new ProductServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //第一步：输入输出准备乱码问题

        request.setCharacterEncoding("utf-8");//接收报文的乱码问题
        response.setContentType("text/html;charset=utf-8");//响应报文的乱码问题

        //第二步：获取op的值，判断要做什么操作

        String op = request.getParameter("op");
        if (null == op || op.isEmpty()) {

            response.getWriter().println("操作无效！");

        } else if ("findAllCategory".equals(op)){

            //在rightframe里面显示category.jsp页面
            //“分类”的查看操作

            //根据用户选择的页码去查询
            String num = request.getParameter("num");

            //分页
            PageHelper pageCategory = service.findPageCategory(num);
            request.setAttribute("page",pageCategory);
            request.getRequestDispatcher("/admin/category/categoryList.jsp").forward(request,response);


        } else if ("addCategory".equals(op)) {

            //在addCategory.jsp页面
            //“分类”的添加操作
            String cname = request.getParameter("cname");
            Category ca = new Category();
            ca.setCname(cname);

            //判断“分类”的合法性
            int ret = service.AddCategoryService(ca);

            //ret =2 添加成功
            //ret =1 分类已存在
            //ret =-2 添加失败
            //ret =-1 数据库异常

            if (ret == 2) {
                response.getWriter().println("添加成功！");
                String contextPath = request.getContextPath();
                response.setHeader("refresh","1;url=" + contextPath + "/admin/category/addCategory.jsp");
            } else {
                switch (ret) {
                    case 1 : response.getWriter().println("alert(已存在该分类！)");break;
                    case -2 : response.getWriter().println("alert(添加失败，请重试！)");break;
                    case -1 : response.getWriter().println("alert(数据库异常，请重试！)");break;
                }
            }


        } else if ("deleteMulti".equals(op)) {//category的删除操作

            //在categoryList.jsp页面
            //删除选中的“分类”

            String[] checkboxes = request.getParameterValues("checkbox");
            int ret = 0;
            ret = service.DeleteCheckedCategoryService(checkboxes);

            if (ret == 1) {
                response.getWriter().println("删除成功！");
                //删除成功，跳转回categoryList.jsp页面
                String contextPath = request.getContextPath();
                response.setHeader("refresh","1;url=" + contextPath + "/admin/CategoryServlet?op=findAllCategory&num=1");
            } else {
                response.getWriter().println("删除失败");
                //删除失败，跳转回updateCategory.jsp页面
                String contextPath = request.getContextPath();
                response.setHeader("refresh","1;url=" + contextPath + "/admin/CategoryServlet?op=findAllCategory&num=1");            }


        } else if ("updateCategory".equals(op)){

            //在updateCategory.jsp中
            //对单个“分类”进行删除

            String cid = request.getParameter("cid");
            String cname = request.getParameter("cname");
            int ret = service.UpdateCategoryService(cid,cname);
            if (ret == 1) {
                response.getWriter().println("更新成功！");
                //删除成功，跳转回categoryList.jsp页面
                String contextPath = request.getContextPath();
                response.setHeader("refresh","1;url=" + contextPath + "/admin/CategoryServlet?op=findAllCategory&num=1");
            } else {
                response.getWriter().println("更新失败");
                //删除失败，跳转回updateCategory.jsp页面
                String contextPath = request.getContextPath();
                response.setHeader("refresh","1;url=" + contextPath + "admin/category/updateCategory.jsp？cid=" + cid + "&cname="+ cname);
            }


        } else if ("findCategory".equals(op)) {

            //商品添加跳转到此servlet来处理

            //显示品牌框内的数据
            List<Category> categoryList = service.ShowCategoryService();
            HttpSession session = request.getSession();
            session.setAttribute("categories",categoryList);
            //request.setAttribute("categories",categoryList);
            request.getRequestDispatcher("/admin/product/addProduct.jsp").forward(request,response);

        } else if("findCategoryByUpdate".equals(op)){

            //处理product的编辑
            String pid = request.getParameter("pid");
            List<Category> categoryList = service.ShowCategoryService();
            try {
                Product product = service1.showSomeProduct(pid);
                request.setAttribute("product",product);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            request.setAttribute("categories",categoryList);
            request.getRequestDispatcher("/admin/product/updateProduct.jsp").forward(request,response);
        }
        else {

            //在当前servlet中
            //删除单个“分类”

            String cid = request.getParameter("cid");
            String cname = request.getParameter("cname");
            int ret = service.DeleteCategoryService(cname);
            if (ret == 1) {
                response.getWriter().println("删除成功！");
                //删除成功，跳转回categoryList.jsp页面
                String contextPath = request.getContextPath();
                response.setHeader("refresh","2;url=" + contextPath + "/admin/CategoryServlet?op=findAllCategory&num=1");
            } else {
                response.getWriter().println("删除失败");
                //删除失败，跳转回updateCategory.jsp页面
                String contextPath = request.getContextPath();
                response.setHeader("refresh","2;url=" + contextPath + "/admin/CategoryServlet?op=findAllCategory&num=1");            }
        }


        }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
