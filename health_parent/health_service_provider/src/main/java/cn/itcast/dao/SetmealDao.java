package cn.itcast.dao;

import cn.itcast.domain.Setmeal;

import com.github.pagehelper.Page;
import org.springframework.stereotype.Repository;

import java.util.Map;
@Repository
public interface SetmealDao {
    //添加检查套餐
    void add(Setmeal setmeal);
    //设置中间表关系
    void setSetmealIdAndCheckgroupId(Map<String,Integer> map);
    //分页查询
    Page<Setmeal> findByCondition(String queryString);
}
