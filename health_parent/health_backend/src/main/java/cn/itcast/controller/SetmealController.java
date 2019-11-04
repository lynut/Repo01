package cn.itcast.controller;

import cn.itcast.constant.MessageConstant;
import cn.itcast.constant.RedisConstant;
import cn.itcast.domain.Setmeal;
import cn.itcast.entity.PageResult;
import cn.itcast.entity.QueryPageBean;
import cn.itcast.entity.Result;
import cn.itcast.service.SetmealService;
import cn.itcast.utils.QiniuUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Reference
    private SetmealService setmealService;
    @Autowired
    private JedisPool jedisPool;

    //文件上传
    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile) {
        try {
            //获取文件名
            String originalFilename = imgFile.getOriginalFilename();
            //获取文件名后缀
            String extension = FilenameUtils.getExtension( originalFilename );
            //拼接随机uuid防止重名覆盖
            String fileName = UUID.randomUUID().toString() + extension;
            //七牛工具类储存到云端
            QiniuUtils.upload2Qiniu( imgFile.getBytes(), fileName );

            //将上传图片名称存入Redis，基于Redis的Set集合存储
            jedisPool.getResource().sadd( RedisConstant.SETMEAL_PIC_RESOURCES,fileName);

            //上传成功
            Result result = new Result( true, MessageConstant.PIC_UPLOAD_SUCCESS );
            result.setData( fileName );
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            //上传失败
            return new Result( false, MessageConstant.PIC_UPLOAD_FAIL );
        }
    }


    //添加套餐
    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal, Integer[] checkgroupIds) {

        try {
            setmealService.add( setmeal, checkgroupIds );
        } catch (Exception e) {
            e.printStackTrace();
            return new Result( false, MessageConstant.ADD_SETMEAL_FAIL );
        }
        return new Result( true, MessageConstant.ADD_SETMEAL_SUCCESS );
    }

    //套餐分页查询
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {

        PageResult pageResult = setmealService.pagequery(
                queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(),
                queryPageBean.getQueryString()
        );
        return pageResult;

    }
}
