package com.cskaoyan.dao.impl;

import com.cskaoyan.bean.Product;
import com.cskaoyan.dao.ProductDao;
import com.cskaoyan.utils.DBUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import javax.management.Query;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductDao {

    /**
     * 在数据库中查询所有的商品
     * @return 商品对象的List集合
     */
    @Override
    public List<Product> showAllProduct() throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
        List<Product> productList = queryRunner.query("select * from Product", new BeanListHandler<>(Product.class));
        return productList;
    }

    /**
     * 显示分页中的商品
     * @param pageCount
     * @param offset
     * @return 商品结合
     * @throws SQLException
     */
    @Override
    public List<Product> showPartProduct(int pageCount, int offset) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
        List<Product> products = queryRunner.query("select * from Product limit ? offset ?", new BeanListHandler<>(Product.class), pageCount, offset);
        return products;
    }

    /**
     *  像Product表中添加纪录
     * @param product 封装的商品信息
     * @return true 添加成功；faslse 添加失败；
     */
    @Override
    public boolean addProduct(Product product) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
        int update = queryRunner.update("insert into Product values(?,?,?,?,?,?,?,?)", product.getPid(), product.getPname(), product.getEstoreprice(), product.getMarkprice(), product.getPnum(), product.getCid(), product.getImgurl(), product.getDesc());
        return update == 1;
    }

    /**
     * 删除商品
     * @param pid
     * @return true 删除成功；false 删除失败；
     */
    @Override
    public boolean deleteOneProduct(String pid) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
        int update = queryRunner.update("delete from Product where pid = ?", pid);
        return update == 1;
    }

    /**
     * 查询pid对应的商品
     * @param pid
     * @return 商品
     * @throws SQLException
     */
    @Override
    public Product showOneProduct(String pid) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
        Product product = queryRunner.query("select * from Product where pid = ?", new BeanHandler<>(Product.class), pid);
        return product;
    }

    /**
     * 返回搜索的结果
     * @param pid
     * @param cid
     * @param pname
     * @param minprice
     * @param maxprice
     * @return 搜索的记录的个数
     * @throws SQLException
     */
    @Override
    public long showSearch(String pid,String cid, String pname, String minprice, String maxprice) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());

        String sql ="select count(*) from Product where 1=1 ";

        ArrayList arrayList = new ArrayList();

        if (pid!=null&&!pid.isEmpty()){

            sql =sql +" and pid=? " ;
            arrayList.add(pid);

        }
        if (cid!=null&&!cid.isEmpty()){

            sql =sql +" and  cid=?" ;
            arrayList.add(cid);


        }
        if (pname!=null&&!pname.isEmpty()){

            sql =sql +" and pname like ?" ;
            arrayList.add("%"+pname+"%");

        }

        if (minprice!=null&&!minprice.isEmpty()){

            sql =sql +" and estoreprice >= ?" ;
            arrayList.add(minprice);

        }
        if (maxprice!=null&&!maxprice.isEmpty()){

            sql =sql +" and estoreprice <= ?" ;
            arrayList.add(maxprice);

        }

        Object[] params = arrayList.toArray();

        Long query = (Long) queryRunner.query(sql, new ScalarHandler(), params);
        return query;
    }

    /**
     * 返回搜索的商品集合
     * @param pid
     * @param cid
     * @param pname
     * @param minprice
     * @param maxprice
     * @param pageCount
     * @param offset
     * @return 商品集合
     * @throws SQLException
     */

    @Override
    public List<Product> showSearchPartProduct(String pid,String cid, String pname, String minprice, String maxprice, int pageCount, int offset) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
        String sql = "select * from product where 1 = 1";

        ArrayList arrayList = new ArrayList();

        if (pid!=null&&!pid.isEmpty()){

            sql =sql +" and pid=? " ;
            arrayList.add(pid);

        }
        if (cid!=null&&!cid.isEmpty()){

            sql =sql +" and  cid=?" ;
            arrayList.add(cid);


        }
        if (pname!=null&&!pname.isEmpty()){

            sql =sql +" and pname like ?" ;
            arrayList.add("%"+pname+"%");

        }

        if (minprice!=null&&!minprice.isEmpty()){

            sql =sql +" and estoreprice >= ?" ;
            arrayList.add(minprice);

        }
        if (maxprice!=null&&!maxprice.isEmpty()){

            sql =sql +" and estoreprice <= ?" ;
            arrayList.add(maxprice);

        }

        sql = sql + " limit ? offset ?";
        arrayList.add(pageCount);
        arrayList.add(offset);
        Object[] params = arrayList.toArray();
        List<Product> product = queryRunner.query(sql, new BeanListHandler<>(Product.class),params);
        return product;
    }

    /**
     * 修改商品信息
     * @param pid
     * @param product
     * @return true 修改成功； false 修改失败；
     * @throws SQLException
     */
    @Override
    public boolean updateProduct(String pid, Product product) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
        int update = queryRunner.update("update Product set pname = ?, estoreprice=?, markprice=?,pnum=?,cid=?,imgurl=?,`desc`=? where pid =?", product.getPname(), product.getEstoreprice(), product.getMarkprice(), product.getPnum(), product.getCid(), product.getImgurl(), product.getDesc(), pid);
        return update == 1;
    }

    /**
     * 查找商品的分类号为cid的商品集合
     * @param cid
     * @return 商品集合
     */
    @Override
    public List<Product> findProductByCid(String cid) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
        List<Product> productList = queryRunner.query("select * from Product where cid = ?", new BeanListHandler<>(Product.class), cid);
        return productList;
    }

    /**
     * 显示滚动条中的书
     * @return
     * @throws SQLException
     */
    @Override
    public List<Product> showTopProduct() throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
        List<Product> topProduct = queryRunner.query("select * from Product where pid like '2222%'", new BeanListHandler<>(Product.class));
        return topProduct;
    }

    /**
     * 显示首页热门书单
     * @return
     * @throws SQLException
     */
    @Override
    public List<Product> showHotProduct() throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
        List<Product> hotProduct = queryRunner.query("select * from Product where cid = 12 limit 6 offset 0", new BeanListHandler<>(Product.class));
        return hotProduct;
    }

    /**
     * 用户书城界面搜索书
     * @param pname
     * @return
     */
    @Override
    public List<Product> searchProduct(String pname) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
        String sql = "select * from Product p inner join category c on p.cid=c.cid where p.pname like ? or c.cname like ?";
        String name = "%"+pname+"%";
        List<Product> productList = queryRunner.query(sql, new BeanListHandler<>(Product.class),name,name);
        return productList;
    }
}
