package com.cskaoyan.dao;

import com.cskaoyan.bean.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao {
    List<Product> showAllProduct() throws SQLException;

    List<Product> showPartProduct(int pageCount, int offset) throws SQLException;

    boolean addProduct(Product product) throws SQLException;

    boolean deleteOneProduct(String pid) throws SQLException;

    Product showOneProduct(String pid) throws SQLException;

    long showSearch(String pid,String cid, String pname, String minprice, String maxprice) throws SQLException;

    List<Product> showSearchPartProduct(String pid,String cid, String pname, String minprice, String maxprice, int pageCount, int offset) throws SQLException;

    boolean updateProduct(String pid, Product product) throws SQLException;

    List<Product> findProductByCid(String cid) throws SQLException;

    List<Product> showTopProduct() throws SQLException;

    List<Product> showHotProduct() throws SQLException;

    List<Product> searchProduct(String pname) throws SQLException;
}
