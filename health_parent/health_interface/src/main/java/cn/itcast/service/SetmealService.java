package cn.itcast.service;

import cn.itcast.domain.Setmeal;
import cn.itcast.entity.PageResult;

public interface SetmealService {
    //新增检查套餐
    public void add(Setmeal setmeal,Integer[] checkgroupIds);

    PageResult pagequery(Integer currentPage, Integer pageSize, String queryString);
}
