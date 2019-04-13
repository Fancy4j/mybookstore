package com.cskaoyan.dao.impl;

import com.cskaoyan.bean.Category;
import com.cskaoyan.dao.CategoryDao;
import com.cskaoyan.utils.DBUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class CategoryDaoImpl implements CategoryDao {
    /**
     *
     * @param cname
     * @return true 已存在该“分类”，不允许再添加；false “分类”不存在，可以添加
     * @throws Exception
     */
    @Override
    public boolean IsCategoryExist(String cname) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
        Category ca = queryRunner.query("select * from Category where cname = ?", new BeanHandler<>(Category.class), cname);
        return ca != null;
    }

    /**
     *
     * @param ca
     * @return true 插入数据库成功；false 插入数据库失败；
     * @throws Exception
     */
    @Override
    public boolean AddCategory(Category ca) throws Exception {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
        int update = queryRunner.update("insert into Category values(null, ?)", ca.getCname());
        return update == 1;
    }

    /**
     *
     * @return “分类”的一个集合
     * @throws Exception
     */
    @Override
    public List<Category> showCategory() throws Exception {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
        List<Category> categories = queryRunner.query("select * from Category", new BeanListHandler<>(Category.class));
        return categories;
    }

    /**
     *
     * @param cid
     * @return true 表示删除成功；false表示删除失败；
     * @throws Exception
     */
    @Override
    public boolean deleteCategory(String cid) throws Exception {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
        int update = queryRunner.update("delete from Category where cname= ?", cid);
        return update == 1;
    }

    /**
     *
     * @param cid
     * @param cname
     * @return true 更新成功；false 更新失败；
     * @throws Exception
     */
    @Override
    public boolean updateCategory(String cid, String cname) throws Exception {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
        int update = queryRunner.update("update Category set cname = ? where cid = ?",cname,cid);
        return update == 1;
    }

    @Override
    public boolean deleteCheckedCategory(String[] checkboxes)  {
        int update = 0;
        QueryRunner queryRunner = new QueryRunner();
        Connection connection = DBUtils.getConnection();
        try {
            connection.setAutoCommit(false);
            for (int i = 0; i < checkboxes.length; i++) {
                update = queryRunner.update(connection,"delete from Category where cid= ?", checkboxes[i]);
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
                update = -1;
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return update == 1;
    }

    /**
     *
     * @param countPerPage 每页显示的个数
     * @param offset 偏移量
     * @return “分类” 数据集
     * @throws Exception
     */
    @Override
    public List<Category> findPartCategory(int countPerPage, int offset) throws Exception {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
        List<Category> categoryList = queryRunner.query("select * from Category limit ? offset ?", new BeanListHandler<>(Category.class), countPerPage, offset);
        return categoryList;
    }

    @Override
    public String showSomeCategory(int cid) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
        Category someCategory = queryRunner.query("select * from Category where cid = ?", new BeanHandler<>(Category.class), cid);
        return someCategory.getCname();
    }

}

