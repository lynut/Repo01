package cn.itcast.dao;

import cn.itcast.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {
    //查看表
    List<User> findAll();

    //增加
    void add(User user);

    //修改
    void update(User user);

    //删除
    void delete(User user);

}
