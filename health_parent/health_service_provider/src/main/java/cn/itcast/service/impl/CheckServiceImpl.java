package cn.itcast.service.impl;

import cn.itcast.dao.CheckitemDao;

import cn.itcast.domain.CheckItem;
import cn.itcast.entity.PageResult;
import cn.itcast.service.CheckitemService;
import com.alibaba.dubbo.config.annotation.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service(interfaceClass = CheckitemService.class)
@Transactional
public class CheckServiceImpl implements CheckitemService {
    //注入dao
    @Autowired
    private CheckitemDao checkitemDao;

    //新增
    @Override
    public void add(CheckItem checkItem) {
        checkitemDao.add( checkItem );
    }

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        //分页助手自动分页
        PageHelper.startPage( currentPage, pageSize );
        //调用dao方差查询合适数据
        Page<CheckItem> page = checkitemDao.selectByCondition( queryString );

        PageResult pageResult = new PageResult( page.getTotal(), page.getResult() );
        return pageResult;
    }

    //删除检查项
    @Override
    public void deleteById(Integer id) throws RuntimeException {
        //查询当前检查项是否与检查组有关
        long count = checkitemDao.findCountByCheckitemId( id );
        if (count > 0) {
            //有关  不能删除
            throw new RuntimeException( "当前项被引用,无法删除" );
        } else {
            //执行删除操作
            checkitemDao.deleteByid( id );
        }


    }


    //查询编辑的检查项
    @Override
    public CheckItem findById(Integer id) {

        CheckItem checkItem = checkitemDao.findById( id );
        return checkItem;
    }

    //编辑检查项
    @Override
    public void edit(CheckItem checkItem) {
        checkitemDao.edit( checkItem );
    }

    //查询所有检查项
    @Override
    public List<CheckItem> findAll() {
        List<CheckItem> checkItemList = checkitemDao.fandAll();
        return checkItemList;
    }


}
