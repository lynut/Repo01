package cn.itcast.controller;


import cn.itcast.constant.MessageConstant;
import cn.itcast.domain.CheckItem;
import cn.itcast.entity.PageResult;
import cn.itcast.entity.QueryPageBean;
import cn.itcast.entity.Result;
import cn.itcast.service.CheckitemService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Controller
@RequestMapping("/checkitem")
public class CheckitemController {
    //注入service
    @Reference
    private CheckitemService checkitemService;

    //添加
    @RequestMapping("/add")
    @ResponseBody
    public Result add(@RequestBody CheckItem checkItem) {
        System.out.println( checkitemService );

        try {
            checkitemService.add( checkItem );
        } catch (Exception e) {
            //添加失败返回新增失败
            return new Result( false, MessageConstant.ADD_CHECKITEM_FAIL );
        }
        //添加成功则返回true
        return new Result( true, MessageConstant.ADD_CHECKITEM_SUCCESS );
    }

    //检查项分页
    @RequestMapping("/findPage")
    @ResponseBody
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        //获取前台传来的数据调用service方法
        PageResult pageResult = checkitemService.findPage(
                queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(),
                queryPageBean.getQueryString()
        );
        return pageResult;
    }

    //检查项删除
    @RequestMapping("/delete")
    @ResponseBody
    public Result delete(Integer id) {

        try {
            //判断是否删除成功
            checkitemService.deleteById( id );
        } catch (RuntimeException e) {
            //先判断是否是关联项阻止删除    //再判断内部是否错误并提示信息
            return new Result( false, e.getMessage() );
        } catch (Exception e) {

            return new Result( false, MessageConstant.DELETE_CHECKITEM_FAIL );
        }
        return new Result( true, MessageConstant.DELETE_CHECKITEM_SUCCESS );

    }

    @RequestMapping("/findById")
    @ResponseBody
    public Result findByid(Integer id) {
        try {
            //判断查找数据是否成功
            CheckItem checkItem = checkitemService.findById( id );
            //成功 回写数据
            return new Result( true, MessageConstant.QUERY_CHECKITEM_SUCCESS, checkItem );
        } catch (Exception e) {
            return new Result( false, MessageConstant.QUERY_CHECKITEM_FAIL );
        }
    }

    @RequestMapping("/edit")
    @ResponseBody
    public Result edit(@RequestBody CheckItem checkItem) {

        try {
            //判断查找数据是否成功
            checkitemService.edit( checkItem );
        } catch (Exception e) {
            return new Result( false, MessageConstant.EDIT_CHECKITEM_FAIL );
        }
        return new Result( true, MessageConstant.EDIT_CHECKITEM_SUCCESS );

    }

    @RequestMapping("/findAll")
    @ResponseBody
    public Result findAll() {


        //判断查找数据是否成功
        List<CheckItem> checkItemList = checkitemService.findAll();

        if (checkItemList != null && checkItemList.size() > 0) {
            //成功则返回数据
            Result result = new Result( true, MessageConstant.QUERY_CHECKITEM_SUCCESS );
            result.setData( checkItemList );
            return result;
        } else {
            return new Result( false, MessageConstant.QUERY_CHECKITEM_FAIL );
        }

    }

}
