package com.cskaoyan.bean;

import java.util.List;

public class PageHelper {

    List<?> list ;
    //总记录数
    int totalRecordsNum;
    //当前页码数
    int currentPageNum;
    //总页数
    int totalPageNum;
    //上一页
    int prevPageNum;
    //下一页
    int nextPageNum;

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }

    public int getTotalRecordsNum() {
        return totalRecordsNum;
    }

    public void setTotalRecordsNum(int totalRecordsNum) {
        this.totalRecordsNum = totalRecordsNum;
    }

    public int getCurrentPageNum() {
        return currentPageNum;
    }

    public void setCurrentPageNum(int currentPageNum) {
        this.currentPageNum = currentPageNum;
    }

    public int getTotalPageNum() {
        return totalPageNum;
    }

    public void setTotalPageNum(int totalPageNum) {
        this.totalPageNum = totalPageNum;
    }

    public int getPrevPageNum() {
        return prevPageNum;
    }

    public void setPrevPageNum(int prevPageNum) {
        this.prevPageNum = prevPageNum;
    }

    public int getNextPageNum() {
        return nextPageNum;
    }

    public void setNextPageNum(int nextPageNum) {
        this.nextPageNum = nextPageNum;
    }
}
