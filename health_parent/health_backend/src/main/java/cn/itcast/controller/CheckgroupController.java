package cn.itcast.controller;


import cn.itcast.constant.MessageConstant;
import cn.itcast.domain.CheckGroup;
import cn.itcast.entity.PageResult;
import cn.itcast.entity.QueryPageBean;
import cn.itcast.entity.Result;
import cn.itcast.service.CheckgroupService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/checkgroup")
public class CheckgroupController {
    @Reference
    private CheckgroupService checkgroupService;


    //添加检查组方法
    @RequestMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds) {

        try {
            checkgroupService.add( checkGroup, checkitemIds );
        } catch (Exception e) {
            return new Result( false, MessageConstant.ADD_CHECKGROUP_FAIL );
        }
        return new Result( true, MessageConstant.ADD_CHECKGROUP_SUCCESS );
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = checkgroupService.pageQuery(
                queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(),
                queryPageBean.getQueryString() );
        return pageResult;


    }

    //根据id查询检查组
    @RequestMapping("/findById")
    public Result findById(Integer id) {
        //调用查询方法
        CheckGroup checkGroup = checkgroupService.findById( id );
        //判断是否有值
        if (checkGroup != null) {
            Result result = new Result( true, MessageConstant.QUERY_CHECKGROUP_SUCCESS );
            result.setData( checkGroup );
            return result;
        }
        return new Result( false, MessageConstant.QUERY_CHECKGROUP_FAIL );
    }

    //根据检查组id查询所有检查项id
    @RequestMapping("/findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(Integer id) {
        //调用查询方法
        try {
            List<Integer> checkgroupIds = checkgroupService.findCheckItemIdsByCheckGroupId( id );
            return new Result( true, MessageConstant.QUERY_CHECKITEM_SUCCESS, checkgroupIds );
        } catch (Exception e) {
            e.printStackTrace();
            return new Result( false, MessageConstant.QUERY_CHECKITEM_FAIL );
        }

    }

    //编辑检查组
    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds) {
        try {
            checkgroupService.edit( checkGroup, checkitemIds );
        } catch (Exception e) {
            e.printStackTrace();
            return new Result( false, MessageConstant.EDIT_CHECKGROUP_FAIL );//新增失败
        }
        return new Result( true, MessageConstant.EDIT_CHECKGROUP_SUCCESS );//新增成功
    }

    //查询所有检查组
    @RequestMapping("/findAll")
    public Result findAll() {
        List<CheckGroup> checkGroupList = checkgroupService.findAll();
        if (checkGroupList != null && checkGroupList.size() > 0) {

            Result result = new Result( true, MessageConstant.QUERY_CHECKGROUP_SUCCESS );//查询成功
            result.setData( checkGroupList );
            return result;
        }
        return new Result( false, MessageConstant.QUERY_CHECKGROUP_FAIL );//查询失败


    }

}


