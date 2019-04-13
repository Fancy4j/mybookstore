package com.cskaoyan.service.impl;

import com.cskaoyan.bean.Category;
import com.cskaoyan.dao.CategoryDao;
import com.cskaoyan.dao.impl.CategoryDaoImpl;
import com.cskaoyan.bean.PageHelper;
import com.cskaoyan.service.CategoryService;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    CategoryDao dao = new CategoryDaoImpl();

    /**
     *  向数据库中添加一个“分类”
     * @param ca
     * @return ret=1 已存在 ret=2 成功 ret=-2 失败 ret=-1 数据库异常
     */
    @Override
    public int AddCategoryService(Category ca) {
        int ret = 0;
        try {
            boolean categoryExist = dao.IsCategoryExist(ca.getCname());
            if (categoryExist) {
                ret = 1;//表示数据库已存在该“分类”
            } else {
                boolean b = dao.AddCategory(ca);
                if (b) {
                    ret = 2;//表示插入数据库成功
                } else {
                    ret = -2;//表示插入数据失败
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ret = -1; //数据库异常
        }
        return ret;
    }

    /**
     * 查询所有的“分类”记录
     * @return 查询结果，用list接收
     */
    @Override
    public List<Category> ShowCategoryService() {
        List<Category> list = null;
        try {
            list = dao.showCategory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 删除数据库中'cid'为cid的字段
     * @param cid
     * @return ret = 1 成功；ret = -1 失败
     */
    @Override
    public int DeleteCategoryService(String cid) {
        int ret = 0;
        try {
            boolean b = dao.deleteCategory(cid);
            if (b) {
                ret = 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            ret = -1;
        }
        return ret;
    }

    /**
     * 更新‘cid’为cid的“分类”的‘cname’
     * @param cid 要更新的“分类”的cid
     * @param cname 将该“分类”的名字改为cname
     * @return 1 更新成功；-1 更新失败；
     */
    @Override
    public int UpdateCategoryService(String cid, String cname) {
        int ret = 0;
        try {
            boolean b = dao.updateCategory(cid,cname);
            if (b) {
                ret = 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            ret = -1;
        }
        return ret;
    }

    /**
     * 删除所选的“分类”
     * @param checkboxes
     * @return 1 表示删除成功； -1 表示删除不成功；
     */
    @Override
    public int DeleteCheckedCategoryService(String[] checkboxes) {
        int ret = 0;
        try {
            boolean b = dao.deleteCheckedCategory(checkboxes);
            if (b) {
                ret = 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            ret = -1;
        }
        return ret;
    }

    /**
     *  查询分页中对应的“分类”
     * @param num 当前需要查询的代码
     * @return pageHelper数据集
     */
    @Override
    public PageHelper findPageCategory(String num) {

        PageHelper pageHelper = new PageHelper();

        //当前页
        pageHelper.setCurrentPageNum(Integer.parseInt(num));

        //当前总记录数，调用dao查所有记录数

        int totalRecordsNum = 0;
        try {
            List<Category> categories = dao.showCategory();
            totalRecordsNum = categories.size();
            pageHelper.setTotalRecordsNum(totalRecordsNum);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //计算总的页码数 页码数 = （总记录数 + （每页记录数 - 1））/ 每页记录数

        int countPerPage = 3;
        int totalPagesNum = (totalRecordsNum + (countPerPage - 1)) / countPerPage;
        pageHelper.setTotalPageNum(totalPagesNum);

        //上一页
        pageHelper.setPrevPageNum(Integer.parseInt(num) - 1);

        //下一页
        pageHelper.setNextPageNum(Integer.parseInt(num) + 1);

        //中间要显示的记录
        //limit 每页显示了记录数 这里是countPerPage
        //offset偏移量 （ 页码数 - 1 ）* 3
        int offset = (Integer.parseInt(num) - 1) *3;
        List<Category> currentCategoryList = null;
       try {
            currentCategoryList = dao.findPartCategory(countPerPage,offset);
            pageHelper.setList(currentCategoryList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pageHelper;
    }


}


