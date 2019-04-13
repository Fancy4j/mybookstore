package com.cskaoyan.service;

import com.cskaoyan.bean.PageHelper;
import com.cskaoyan.bean.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductService {
    List<Product> showAllProductService() throws SQLException;

    PageHelper showPageProductService(int num);

    boolean addProductService(Product product) throws SQLException;

    boolean deleteOneProduct(String pid) throws SQLException;

    Product showSomeProduct(String pid) throws SQLException;


    PageHelper showSearchProduct(String pid, String cid, String pname, String minprice, String maxprice, int currentPage);


    boolean updateProductService(String pid, Product product) throws SQLException;

    List<Product> showProductByCidService(String cid) throws SQLException;

    List<Product> showTopProductService() throws SQLException;

    List<Product> showHotProductService() throws SQLException;

    List<Product> searchProductService(String pname) throws SQLException;
}
