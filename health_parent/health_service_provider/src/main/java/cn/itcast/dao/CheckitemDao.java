package cn.itcast.dao;

import cn.itcast.domain.CheckItem;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckitemDao {

    //添加检查项
    void add(CheckItem checkItem);

    //检查项分页
    Page<CheckItem> selectByCondition(String queryString);

    //查询检查项和检查组关系
    long findCountByCheckitemId(Integer id);

    //删除检查项操作
    void deleteByid(Integer id);

    //查询编辑的检查项
    CheckItem findById(Integer id);

    //编辑检查项
    void edit(CheckItem checkItem);


    List<CheckItem> fandAll();

}
