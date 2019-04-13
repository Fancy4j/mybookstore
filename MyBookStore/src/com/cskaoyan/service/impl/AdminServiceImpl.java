package com.cskaoyan.service.impl;

import com.cskaoyan.bean.Admin;
import com.cskaoyan.bean.PageHelper;
import com.cskaoyan.dao.AdminDao;
import com.cskaoyan.dao.impl.AdminDaoImpl;
import com.cskaoyan.service.AdminService;

import java.sql.SQLException;
import java.util.List;

public class AdminServiceImpl implements AdminService {
    private int PER_PAGE = 3;
    AdminDao dao = new AdminDaoImpl();
    @Override
    public boolean addAdminService(String username, String password) throws SQLException {
        return dao.addAdmin(username, password);
    }

    @Override
    public boolean IsAdminNameValidService(String username) throws SQLException {
        return dao.IsAdminNameValid(username);
    }

    @Override
    public PageHelper showPageAdminService(String num) throws SQLException {
        PageHelper pageHelper = new PageHelper();
        //当前页
        int currentPage = Integer.parseInt(num);
        pageHelper.setCurrentPageNum(currentPage);

        //总记录数
        int totalAdminNum = (int) dao.showAllAdmin();
        pageHelper.setTotalRecordsNum(totalAdminNum);

        //总页数
        int totalPageNum = (totalAdminNum + (PER_PAGE - 1)) / PER_PAGE;
        pageHelper.setTotalPageNum(totalPageNum);

        //前一页
        pageHelper.setPrevPageNum(currentPage - 1);

        //后一页
        pageHelper.setNextPageNum(currentPage + 1);

        //中间的admin内容
        int offset = (currentPage -1) * PER_PAGE;
        List<Admin> adminList = dao.showPageAdmin(PER_PAGE,offset);
        pageHelper.setList(adminList);
        return pageHelper;
    }

    @Override
    public boolean updateAdminService(String username, String password) throws SQLException {
        return dao.updateAdmin(username,password);
    }

    @Override
    public boolean deleteOneService(String aid) throws SQLException {
        return dao.deleteOne(aid);
    }

    @Override
    public Admin IsAdminExist(String username, String password) throws SQLException {
        return dao.isAdminExist(username,password);
    }
}
