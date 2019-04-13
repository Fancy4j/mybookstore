package com.cskaoyan.dao;

import com.cskaoyan.bean.User;

import java.sql.SQLException;

public interface UserDao {
    boolean addUser(User user) throws SQLException;

    boolean isUserNameValid(String username) throws SQLException;

    User isUserExist(String username, String password) throws SQLException;

    boolean updateUser(String uid, String nickname, String password, String email, String birthday) throws SQLException;
}
