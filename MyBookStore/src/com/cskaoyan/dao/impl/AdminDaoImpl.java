package com.cskaoyan.dao.impl;

import com.cskaoyan.bean.Admin;
import com.cskaoyan.dao.AdminDao;
import com.cskaoyan.utils.DBUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class AdminDaoImpl implements AdminDao {
    /**
     *
     * @param username
     * @param password
     * @return true 添加成功 ； false 添加失败；
     */
    @Override
    public boolean addAdmin(String username, String password) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
        int update = queryRunner.update("insert into `admin` values(null,?,?)", username, password);
        return update == 1;
    }

    /**
     * 判断是否存在该管理员名
     * @param username
     * @return true 存在；false不存在
     */
    @Override
    public boolean IsAdminNameValid(String username) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
        Admin query = queryRunner.query("select * from `admin` where username = ?", new BeanHandler<>(Admin.class), username);
        return query != null;
    }

    @Override
    public long showAllAdmin() throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
        Long query = (Long) queryRunner.query("select count(*) from `admin`", new ScalarHandler());
        return query;
    }

    @Override
    public List<Admin> showPageAdmin(int per_page, int offset) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
        List<Admin> admins = queryRunner.query("select * from `admin` limit ? offset ?", new BeanListHandler<>(Admin.class),per_page,offset);
        return admins;
    }

    @Override
    public boolean updateAdmin(String username, String password) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
        int update = queryRunner.update("update `admin` set password = ? where username = ?", password, username);
        return update == 1;
    }

    @Override
    public boolean deleteOne(String aid) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
        int update = queryRunner.update("delete from `admin` where aid = ?", aid);
        return update == 1;
    }

    @Override
    public Admin isAdminExist(String username, String password) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
        Admin admin = queryRunner.query("select * from `admin` where username = ? and password = ?", new BeanHandler<>(Admin.class), username, password);
        return admin;
    }
}
