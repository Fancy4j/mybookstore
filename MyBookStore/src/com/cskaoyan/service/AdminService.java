package com.cskaoyan.service;

import com.cskaoyan.bean.Admin;
import com.cskaoyan.bean.PageHelper;

import java.sql.SQLException;

public interface AdminService {

    boolean addAdminService(String username, String password) throws SQLException;

    boolean IsAdminNameValidService(String username) throws SQLException;

    PageHelper showPageAdminService(String num) throws SQLException;

    boolean updateAdminService(String username, String password) throws SQLException;

    boolean deleteOneService(String aid) throws SQLException;

    Admin IsAdminExist(String username, String password) throws SQLException;
}
