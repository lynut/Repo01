package cn.itcast.service;

import cn.itcast.domain.User;
import redis.clients.jedis.BinaryClient;

import java.util.List;

public interface UserService {
    //查寻所有
    public List<User> findAll();

    //添加
    public void add(User user);

    //修改
    public void update(User user);

    //删除
    public void delete(User user);
}
