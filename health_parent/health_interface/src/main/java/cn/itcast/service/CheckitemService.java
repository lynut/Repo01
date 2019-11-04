package cn.itcast.service;

import cn.itcast.domain.CheckItem;
import cn.itcast.entity.PageResult;
import com.alibaba.dubbo.config.annotation.Service;

import java.util.List;

//服务类接口

public interface CheckitemService {
    //添加接口
    public void add(CheckItem checkItem);

    //检查项分页
    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    //删除检查项
    void deleteById(Integer id);

    //编辑前查询
    CheckItem findById(Integer id);

    //编辑检查项
    void edit(CheckItem checkItem);

    //查询所有检查项
    List<CheckItem> findAll();
}
