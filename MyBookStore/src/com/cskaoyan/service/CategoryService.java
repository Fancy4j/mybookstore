package com.cskaoyan.service;

import com.cskaoyan.bean.PageHelper;
import com.cskaoyan.bean.Category;

import java.util.List;

public interface CategoryService {
    int AddCategoryService (Category ca);

    List<Category> ShowCategoryService();

    int DeleteCategoryService(String cid);

    int UpdateCategoryService(String cid, String cname);

    int DeleteCheckedCategoryService(String[] checkboxes);


    PageHelper findPageCategory(String num);

}
