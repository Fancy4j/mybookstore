package com.cskaoyan.service.impl;

import com.cskaoyan.bean.PageHelper;
import com.cskaoyan.bean.Product;
import com.cskaoyan.dao.CategoryDao;
import com.cskaoyan.dao.ProductDao;
import com.cskaoyan.dao.impl.CategoryDaoImpl;
import com.cskaoyan.dao.impl.ProductDaoImpl;
import com.cskaoyan.service.ProductService;
import com.cskaoyan.utils.DBUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    //查询商品数据
    ProductDao dao1 = new ProductDaoImpl();
    //查询分类数据
    CategoryDao dao2 = new CategoryDaoImpl();
    //设置每页显示的商品个数
    private static  final int PAGE_COUNT=3;

    /**
     *获取商品集合
     * @return 商品的集合
     */
    @Override
    public List<Product> showAllProductService() throws SQLException {
        return dao1.showAllProduct();
    }

    /**
     * 分页查询获取商品集合
     * @return 当前页数上面的商品集合
     * @param num 当前页数
     */
    @Override
    public PageHelper showPageProductService(int num) {
        PageHelper pageHelper = new PageHelper();

        //当前页数
        pageHelper.setCurrentPageNum(num);

        //用dao.showAllProduct()查询，获得总记录数
        List<Product> productList = null;
        int totalProductNum = 0;
        try {
            productList = dao1.showAllProduct();
            totalProductNum = productList.size();
            pageHelper.setTotalRecordsNum(totalProductNum);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        //计算出总页数 总页数 = （总记录数 + （每页记录数 - 1））/每页记录数
        int totalPageNum = (totalProductNum + (PAGE_COUNT - 1))/PAGE_COUNT;
        pageHelper.setTotalPageNum(totalPageNum);

        //前一页
        pageHelper.setPrevPageNum(num - 1);

        //下一页
        pageHelper.setNextPageNum(num + 1);

        //中间显示的商品信息+该商品对应的分类
        int offset = (num - 1) * PAGE_COUNT;
        try {
            List<Product> products = dao1.showPartProduct(PAGE_COUNT,offset);
            for (Product product : products) {
                int cid = product.getCid();
                String cname = dao2.showSomeCategory(cid);
                product.setCname(cname);
            }
            pageHelper.setList(products);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pageHelper;
    }

    /**
     *  添加商品
     * @param product
     * @return true 添加成功；false 添加失败；
     * @throws SQLException
     */
    @Override
    public boolean addProductService(Product product) throws SQLException {
        return dao1.addProduct(product);
    }

    /**
     *  删除某一个商品
     * @param pid
     * @return true 删除成功；false 删除失败；
     */
    @Override
    public boolean deleteOneProduct(String pid) throws SQLException {
        return dao1.deleteOneProduct(pid);
    }

    /**
     * 找到pid对应的商品
     * @param pid
     * @return 商品对象
     * @throws SQLException
     */
    @Override
    public Product showSomeProduct(String pid) throws SQLException {
        return dao1.showOneProduct(pid);
    }

    /**
     * 显示搜索的商品信息
     * @param pid
     * @param cid
     * @param pname
     * @param minprice
     * @param maxprice
     * @param currentPage
     * @return PageHelper
     */
    @Override
    public PageHelper showSearchProduct(String pid, String cid, String pname, String minprice, String maxprice, int currentPage) {

        PageHelper pageHelper = new PageHelper();

        //当前页数
        pageHelper.setCurrentPageNum(currentPage);

        //获得总记录数
        long totalProductNum = 0;
        try {
            totalProductNum = dao1.showSearch(pid,cid,pname,minprice,maxprice);
            pageHelper.setTotalRecordsNum((int)totalProductNum);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        //计算出总页数 总页数 = （总记录数 + （每页记录数 - 1））/每页记录数
        int totalPageNum = ((int)totalProductNum + (PAGE_COUNT - 1))/PAGE_COUNT;
        pageHelper.setTotalPageNum(totalPageNum);

        //前一页
        pageHelper.setPrevPageNum(currentPage - 1);

        //下一页
        pageHelper.setNextPageNum(currentPage + 1);

        //中间显示的商品信息+该商品对应的分类
        int offset = (currentPage - 1) * PAGE_COUNT;
        try {
            List<Product> products = dao1.showSearchPartProduct(pid,cid,pname,minprice,maxprice,PAGE_COUNT,offset);

            for (Product product : products) {
                int caid = product.getCid();
                String cname = dao2.showSomeCategory(caid);
                product.setCname(cname);
            }
            pageHelper.setList(products);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pageHelper;
    }

    /**
     * 修改商品的信息
     * @param pid
     * @param product
     * @return true 修改成功；false 修改失败；
     * @throws SQLException
     */
    @Override
    public boolean updateProductService(String pid, Product product) throws SQLException {
        return dao1.updateProduct(pid,product);
    }

    /**
     * 显示分类号为cid的商品的集合
     * @param cid
     * @return 商品的集合
     */
    @Override
    public List<Product> showProductByCidService(String cid) throws SQLException {
        return dao1.findProductByCid(cid);
    }

    @Override
    public List<Product> showTopProductService() throws SQLException {
        return dao1.showTopProduct();
    }

    @Override
    public List<Product> showHotProductService() throws SQLException {
        return dao1.showHotProduct();
    }

    @Override
    public List<Product> searchProductService(String pname) throws SQLException {
        return dao1.searchProduct(pname);
    }


}
