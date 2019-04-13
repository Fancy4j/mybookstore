package com.cskaoyan.controller;

import com.cskaoyan.bean.Category;
import com.cskaoyan.bean.PageHelper;
import com.cskaoyan.bean.Product;
import com.cskaoyan.service.CategoryService;
import com.cskaoyan.service.ProductService;
import com.cskaoyan.service.impl.CategoryServiceImpl;
import com.cskaoyan.service.impl.ProductServiceImpl;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ProductServlet",urlPatterns = "/admin/ProductServlet")
public class ProductServlet extends HttpServlet {

    ProductService service = new ProductServiceImpl();
    CategoryService categoryService = new CategoryServiceImpl();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //productList.jsp
        //输入输出准备 乱码问题
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        //获取op参数，判断进行何种操作

        String op = request.getParameter("op");

        if (op != null && !op.isEmpty()) {
            switch (op) {
                case "findAllProduct":
                    show(request,response);
                    break;
                case "deleteOne":
                    deleteOne(request,response);
                    break;
                case "searchProduct":
                    searchProduct(request,response);
                    break;
                case "byCid":
                    showProuctByCid(request,response);
                    break;
                case "findProductById":
                    find(request,response);
                    break;
                case "findProByName":
                    search(request,response);
                    break;
                default:
                    break;
            }
        } else {
            response.getWriter().println("操作无效，请重试！");
        }
    }

    private void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pname = request.getParameter("pname");
        try {
            List<Product> productList = service.searchProductService(pname);
            request.setAttribute("products",productList);
            /*List<Category> categories = categoryService.ShowCategoryService();
            request.setAttribute("categoryList",categories);*/
            request.getRequestDispatcher("/products.jsp").forward(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void find(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pid = request.getParameter("pid");
        try {
            Product product = service.showSomeProduct(pid);
          /*  List<Category> categories = categoryService.ShowCategoryService();
            request.setAttribute("categoryList",categories);*/
            request.setAttribute("product",product);
            request.getRequestDispatcher("/productdetail.jsp").forward(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * 点击首页上面的一个分类，返回分类号为cid的商品信息给index.jsp
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void showProuctByCid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cid = request.getParameter("cid");
        try {
            List<Product> productList = service.showProductByCidService(cid);
            request.setAttribute("products",productList);
           /* List<Category> categories = categoryService.ShowCategoryService();
            request.setAttribute("categoryList",categories);*/
            request.getRequestDispatcher("/products.jsp").forward(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 搜索符合条件的商品
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void searchProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pid = request.getParameter("pid");
        String cid = request.getParameter("cid");
        String pname = request.getParameter("pname");
        String minprice = request.getParameter("minprice");
        String maxprice = request.getParameter("maxprice");
        int currentPage = 1;

        PageHelper pageProduct = service.showSearchProduct(pid,cid,pname,minprice,maxprice,currentPage);
        request.setAttribute("page",pageProduct);
        request.getRequestDispatcher("/admin/product/productList.jsp").forward(request,response);
    }


    /**
     * 删除一条商品的记录
     * @param request
     * @param response
     * @throws IOException
     */
    private void deleteOne(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pid = request.getParameter("pid");
        try {
            boolean b = service.deleteOneProduct(pid);
            if (b) {
                response.getWriter().println("删除成功！即将跳转！");
                String contextPath = request.getContextPath();
                response.setHeader("refresh","1;url=" + contextPath + "/admin/ProductServlet?op=findAllProduct&num=1");
            } else {
                response.getWriter().println("删除失败！请重试！");
                String contextPath = request.getContextPath();
                response.setHeader("refresh","1;url=" + contextPath + "/admin/ProductServlet?op=findAllProduct&num=1");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("异常！请重试！");
            String contextPath = request.getContextPath();
            response.setHeader("refresh","1;url=" + contextPath + "/admin/ProductServlet?op=findAllProduct&num=1");
        }
    }

    /**
     * 分页显示所有商品的信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void show(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String num = request.getParameter("num");

        PageHelper productList = service.showPageProductService(Integer.parseInt(num));
        request.setAttribute("page",productList);
        request.getRequestDispatcher("/admin/product/productList.jsp").forward(request,response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
