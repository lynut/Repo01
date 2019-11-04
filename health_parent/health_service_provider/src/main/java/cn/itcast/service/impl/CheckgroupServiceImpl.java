package cn.itcast.service.impl;


import cn.itcast.dao.CheckgroupDao;
import cn.itcast.domain.CheckGroup;
import cn.itcast.entity.PageResult;
import cn.itcast.service.CheckgroupService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = CheckgroupService.class)
@Transactional
public class CheckgroupServiceImpl implements CheckgroupService {

    @Autowired
    private CheckgroupDao checkgroupDao;


    //添加检查组
    @Override
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        //添加检查组
        checkgroupDao.add( checkGroup );
        //配置检查组与检查项的关系
        this.setCheckGroupAndCheckItem( checkGroup.getId(), checkitemIds );
    }


    //检查组分页
    @Override
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage( currentPage, pageSize );

        Page<CheckGroup> checkGroupList = checkgroupDao.findByCondition( queryString );


        return new PageResult( checkGroupList.getTotal(), checkGroupList.getResult() );
    }


    //根据id查询
    @Override
    public CheckGroup findById(Integer id) {
        CheckGroup checkGroup = checkgroupDao.findById( id );
        return checkGroup;
    }

    //根据检查组id查询所有检查项id
    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {
        List<Integer> checkgroupids = checkgroupDao.findCheckItemIdsByCheckGroupId( id );
        return checkgroupids;


    }

    //编辑表
    @Override
    public void edit(CheckGroup checkGroup, Integer[] checkitemIds) {
        //更新检查组基本信息
        checkgroupDao.edit( checkGroup );
        //修改之前因根据检查项id清除中间表中与价差组关系
        checkgroupDao.deleteByCheckGroupId( checkGroup.getId() );
        //在中间表中配置新的两表关系
        setCheckGroupAndCheckItem( checkGroup.getId(), checkitemIds );


    }

    //查询所有检查组
    @Override
    public List<CheckGroup> findAll() {

        List<CheckGroup> checkGroupList = checkgroupDao.findAll();

        return checkGroupList;
    }

    //建立检查组和检查项多对多关系
    public void setCheckGroupAndCheckItem(Integer checkGroupId, Integer[] checkitemIds) {
        if (checkitemIds != null && checkitemIds.length > 0) {
            for (Integer checkitemId : checkitemIds) {
                Map<String, Integer> map = new HashMap<>();
                map.put( "checkgroup_Id", checkGroupId );
                map.put( "checkitem_Id", checkitemId );
                checkgroupDao.setCheckGroupAndCheckItem( map );
            }
        }
    }

}
