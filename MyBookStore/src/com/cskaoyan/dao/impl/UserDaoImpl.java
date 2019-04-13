package com.cskaoyan.dao.impl;

import com.cskaoyan.bean.User;
import com.cskaoyan.dao.UserDao;
import com.cskaoyan.utils.DBUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

public class UserDaoImpl implements UserDao {
    /**
     * 向数据库中添加用户
     * @param user
     * @return
     * @throws SQLException
     */
    @Override
    public boolean addUser(User user) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
        String sql = "insert into `user` values(null,?,?,?,?,?,?)";
        int update = queryRunner.update(sql, user.getUsername(), user.getNickname(), user.getPassword(), user.getEmail(), user.getBirthday(), user.getUpdatetime());
        return update == 1;
    }


    /**
     * 判断用户名是否合法
     * @param username
     * @return
     */
    @Override
    public boolean isUserNameValid(String username) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
        User user = queryRunner.query("select * from `User` where username = ?", new BeanHandler<>(User.class), username);
        return user == null;
    }

    @Override
    public User isUserExist(String username, String password) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
        User user = queryRunner.query("select * from `User` where username = ? and password = ?", new BeanHandler<>(User.class), username,password);
        return user;
    }

    @Override
    public boolean updateUser(String uid, String nickname, String password, String email, String birthday) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
        String sql = "update `user` set nickname = ?,password=?,email =?,birthday = ? where uid =?";
        int update = queryRunner.update(sql, nickname, password, email, birthday, uid);
        return update == 1;
    }
}
