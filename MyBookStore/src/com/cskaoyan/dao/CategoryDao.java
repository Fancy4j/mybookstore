package com.cskaoyan.dao;

import com.cskaoyan.bean.Category;

import java.sql.SQLException;
import java.util.List;

public interface CategoryDao {
    /**
     *
     * @param cname
     * @return false category不存在，允许用户添加；true  category已存在，不允许添加；
     * @throws Exception
     */
    public boolean IsCategoryExist(String cname) throws Exception;
    /**
     *
     * @param ca
     * @return true 添加成功 ，false 添加失败
     * @throws Exception
     */
    boolean AddCategory(Category ca) throws Exception;

    List<Category> showCategory() throws Exception;


    boolean deleteCategory(String cid) throws Exception;

    boolean updateCategory(String cid, String cname) throws Exception;

    boolean deleteCheckedCategory(String[] checkboxes) throws Exception;

    List<Category> findPartCategory(int countPerPage, int offset) throws Exception;

    String showSomeCategory(int cid) throws SQLException;

}
