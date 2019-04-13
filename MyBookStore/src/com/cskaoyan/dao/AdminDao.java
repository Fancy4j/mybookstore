package com.cskaoyan.dao;

import com.cskaoyan.bean.Admin;

import java.sql.SQLException;
import java.util.List;

public interface AdminDao {

    boolean addAdmin(String username, String password) throws SQLException;

    boolean IsAdminNameValid(String username) throws SQLException;

    long showAllAdmin() throws SQLException;

    List<Admin> showPageAdmin(int per_page, int offset) throws SQLException;

    boolean updateAdmin(String username, String password) throws SQLException;

    boolean deleteOne(String aid) throws SQLException;

    Admin isAdminExist(String username, String password) throws SQLException;

}
