package com.spring.demo.springbootexample.controller;

import com.spring.demo.springbootexample.mybatis.mapper.TestMapper;
import com.spring.demo.springbootexample.mybatis.po.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * class_name: TestController
 * package: com.spring.demo.springbootexample.controller
 * describe: 测试类控制器
 * creat_user: haoxiaol
 * creat_date: 2018/11/16
 * creat_time: 14:57
 **/
@Controller
public class TestController {

    //替换成自己生成的mapper
    @Autowired
    TestMapper userMapper;

    @RequestMapping("/test")
    @ResponseBody
    public Test test(){
        //查询该表的所有数据
        return userMapper.selectByPrimaryKey("1");
    }

}
