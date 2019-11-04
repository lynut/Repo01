package cn.itcast.service;

import cn.itcast.domain.CheckGroup;
import cn.itcast.entity.PageResult;

import java.util.List;


public interface CheckgroupService {

    //检查组添加
    void add(CheckGroup checkGroup, Integer[] checkitemIds);

    //检查组分页
    PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);

    //根据id查询检查组
    CheckGroup findById(Integer id);

    //根据检查组id查询所有检查项id
    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    //编辑检查组
    void edit(CheckGroup checkGroup, Integer[] checkitemIds);

    //查询所有检查组
    List<CheckGroup> findAll();
}
