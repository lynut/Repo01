package cn.itcast.dao;

import cn.itcast.domain.CheckGroup;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CheckgroupDao {



    //根据id查询检查组
    CheckGroup findById(Integer id);

    //添加检查组
    void add(CheckGroup checkGroup);

    //设置检查组和检查项关系
    void setCheckGroupAndCheckItem(Map map);

    //检查项分页
    Page<CheckGroup> findByCondition(String queryString);

    //根据检查组id查询所有检查项id
    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    //根据检查组id删除中间表中关系
    void deleteByCheckGroupId(Integer id);

    //更新检查组的基本数据
    void edit(CheckGroup checkGroup);

    //查询所有检查组
    List<CheckGroup> findAll();

}
