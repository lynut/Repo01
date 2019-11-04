package cn.itcast.service.impl;

import cn.itcast.constant.RedisConstant;
import cn.itcast.dao.SetmealDao;
import cn.itcast.domain.Setmeal;
import cn.itcast.entity.PageResult;
import cn.itcast.service.SetmealService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {
    //注入
    @Autowired
    private SetmealDao setmealDao;
    @Autowired
    private JedisPool jedisPool;

    //新增套餐
    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        setmealDao.add( setmeal );
        if (checkgroupIds != null && checkgroupIds.length > 0) {
            //如果有复选内容 则调用方法设置中间表关系
            setSetmealIdAndCheckgroupId( setmeal.getId(), checkgroupIds );
        }
        //存入Redis
        savePic2Redis( setmeal.getImg() );
    }

    @Override
    public PageResult pagequery(Integer currentPage, Integer pageSize, String queryString) {
        //分页助手
        PageHelper.startPage( currentPage, pageSize );
        Page<Setmeal> setmealPage = setmealDao.findByCondition( queryString );
        long total = setmealPage.getTotal();
        List<Setmeal> result = setmealPage.getResult();
        return new PageResult( total, result );
    }


    //工具方法  重新设置中间表关系
    public void setSetmealIdAndCheckgroupId(Integer setmealId, Integer[] checkgroupIds) {

        for (Integer checkgroupId : checkgroupIds) {
            Map<String, Integer> map = new HashMap<>();
            map.put( "setmeal_id", setmealId );
            map.put( "checkgroup_id", checkgroupId );
            setmealDao.setSetmealIdAndCheckgroupId( map );
        }
    }

    //将图片名称保存到Redis
    private void savePic2Redis(String pic) {
        jedisPool.getResource().sadd( RedisConstant.SETMEAL_PIC_DB_RESOURCES, pic );
    }
}
