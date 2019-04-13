package com.cskaoyan.controller;

import com.cskaoyan.bean.Category;
import com.cskaoyan.bean.Product;
import com.cskaoyan.service.ProductService;
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

@WebServlet(name = "AddProductServlet",urlPatterns = "/admin/AddProductServlet")
public class AddProductServlet extends HttpServlet {

    ProductService service = new ProductServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //addProduct.jsp
        //searchProduct.jsp

        //输入输出准备工作 乱码问题
        request.setCharacterEncoding("utf-8");//接收报文的乱码问题
        response.setContentType("text/html;charset=utf-8");//响应报文的乱码问题

       /* //
        try {
            opertion(request,response);
        } catch (FileUploadException e) {
            e.printStackTrace();
        }*/

        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletContext servletContext = this.getServletConfig().getServletContext();
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);
        ServletFileUpload upload = new ServletFileUpload(factory);
        //文件名乱码问题
        upload.setHeaderEncoding("utf-8");
        List<FileItem> items = null;
        try {
            items = upload.parseRequest(request);
        } catch (FileUploadException e) {
            e.printStackTrace();
        }

        // Process the uploaded items
        Iterator<FileItem> iter = items.iterator();
        Product product = new Product();
        Map<String, String> parameterMap = new HashMap<>();
        while (iter.hasNext()) {
            FileItem item = iter.next();
            if (item.isFormField()) {
                String name = item.getFieldName();
                //普通字段乱码问题
                String value = item.getString("utf-8");
                parameterMap.put(name,value);
            } else {
                String fieldName = item.getFieldName(); //name
                String fileName = item.getName(); //value
                parameterMap.put(fieldName,fileName);
            }
        }
        try {
            BeanUtils.populate(product,parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        String op = parameterMap.get("op");
        if (op != null && !op.isEmpty()) {
            if ("addProduct".equals(op)) {

                boolean b = false;
                try {
                    b = service.addProductService(product);
                    if(b) {
                        response.getWriter().println("添加成功！即将跳转！");
                        String contextPath = request.getContextPath();
                        response.setHeader("refresh","1;url=" + contextPath + "/admin/CategoryServlet?op=findCategory");
                    } else {
                        response.getWriter().println("添加失败！请重试！");
                        String contextPath = request.getContextPath();
                        response.setHeader("refresh","1;url=" + contextPath + "/admin/CategoryServlet?op=findCategory");               }
                } catch (SQLException e) {
                    e.printStackTrace();
                    response.getWriter().println("异常！请重试！");
                    String contextPath = request.getContextPath();
                    response.setHeader("refresh","1;url=" + contextPath + "/admin/CategoryServlet?op=findCategory");
                }
            }else {
                boolean b = false;
                try {
                    b = service.updateProductService(product.getPid(), product);
                    if (b) {
                        response.getWriter().println("修改成功！即将跳转！");
                        String contextPath = request.getContextPath();
                        response.setHeader("refresh", "1;url=" + contextPath + "/admin/ProductServlet?op=findAllProduct&num=1");
                    } else {
                        response.getWriter().println("修改失败！请重试！");
                        String contextPath = request.getContextPath();
                        response.setHeader("refresh", "1;url=" + contextPath + "/admin/ProductServlet?op=findAllProduct&num=1");
                    }
                }catch (SQLException e) {
                    e.printStackTrace();
                    response.getWriter().println("异常！请重试！");
                    String contextPath = request.getContextPath();
                    response.setHeader("refresh", "1;url=" + contextPath + "/admin/ProductServlet?op=findAllProduct&num=1");
                }
            }
        } else {
            response.getWriter().println("操作无效！请重试！");
        }
    }

    /**
     * 添加商品
     * @param request
     * @param response
     * @throws IOException
     * @throws FileUploadException
     */
/*    private void opertion(HttpServletRequest request, HttpServletResponse response) throws IOException, FileUploadException, ServletException {

        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletContext servletContext = this.getServletConfig().getServletContext();
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);
        ServletFileUpload upload = new ServletFileUpload(factory);
        //文件名乱码问题
        upload.setHeaderEncoding("utf-8");
        List<FileItem> items = upload.parseRequest(request);

        // Process the uploaded items
        Iterator<FileItem> iter = items.iterator();
        Product product = new Product();
        Map<String, String> parameterMap = new HashMap<>();
        while (iter.hasNext()) {
            FileItem item = iter.next();
            if (item.isFormField()) {
                String name = item.getFieldName();
                //普通字段乱码问题
                String value = item.getString("utf-8");
                parameterMap.put(name,value);
            } else {
                String fieldName = item.getFieldName(); //name
                String fileName = item.getName(); //value
                parameterMap.put(fieldName,fileName);
            }
        }
        try {
            BeanUtils.populate(product,parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        String op = parameterMap.get("op");
        if (op != null && op.isEmpty()) {
            if ("addProduct".equals(op)) {

                boolean b = false;
                try {
                    b = service.addProductService(product);
                    if(b) {
                        response.getWriter().println("添加成功！即将跳转！");
                        String contextPath = request.getContextPath();
                        response.setHeader("refresh","1;url=" + contextPath + "/CategoryServlet?op=findCategory");
                    } else {
                        response.getWriter().println("添加失败！请重试！");
                        String contextPath = request.getContextPath();
                        response.setHeader("refresh","1;url=" + contextPath + "/CategoryServlet?op=findCategory");               }
                } catch (SQLException e) {
                    e.printStackTrace();
                    response.getWriter().println("异常！请重试！");
                    String contextPath = request.getContextPath();
                    response.setHeader("refresh","1;url=" + contextPath + "/CategoryServlet?op=findCategory");
                }
            }else {
                boolean b = false;
                try {
                    b = service.updateProductService(product.getPid(), product);
                    if (b) {
                        response.getWriter().println("修改成功！即将跳转！");
                        String contextPath = request.getContextPath();
                        response.setHeader("refresh", "1;url=" + contextPath + "/CategoryServlet?op=findCategory");
                    } else {
                        response.getWriter().println("修改失败！请重试！");
                        String contextPath = request.getContextPath();
                        response.setHeader("refresh", "1;url=" + contextPath + "/CategoryServlet?op=findCategory");
                    }
                }catch (SQLException e) {
                    e.printStackTrace();
                    String contextPath = request.getContextPath();
                    response.setHeader("refresh", "1;url=" + contextPath + "/CategoryServlet?op=findCategory");
                }
            }
        }



    }
*/
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
