package com.cskaoyan.service;

import com.cskaoyan.bean.User;

import java.sql.SQLException;

public interface UserService {
    boolean AddUserService(User user) throws SQLException;

    boolean IsUserNameValidService(String username) throws SQLException;

    Boolean sendMail(String email, String emailMsg);

    User IsUserExistsService(String username, String password) throws SQLException;

    boolean UpdateUserService(String uid, String nickname, String password, String email, String birthday) throws SQLException;
}
