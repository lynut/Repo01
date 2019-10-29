package cn.itcast.controller;

import cn.itcast.domain.User;

import cn.itcast.service.UserService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Reference
    private UserService userService;

    @RequestMapping("/findAll")
    @ResponseBody
    public List<User> findAll() {

        List<User> all = userService.findAll();
        System.out.println( all );
        return all;
    }

    @RequestMapping("/add")
    @ResponseBody
    public String add() {
        User user = new User();
        user.setName( "大姐" );
        user.setMoney( "6000" );
        userService.add( user );
        return "添加成功";
    }

    @RequestMapping("/update")
    @ResponseBody
    public String update() {
        User user = new User();
        user.setId( 7 );
        user.setName( "沙雕" );
        user.setMoney( "5000" );
        userService.update( user );
        return "修改成功";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete() {
        User user = new User();
        user.setId( 7 );
        userService.delete( user );
        return "删除成功";
    }

}
