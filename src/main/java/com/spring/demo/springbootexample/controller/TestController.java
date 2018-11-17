package com.spring.demo.springbootexample.controller;

import com.spring.demo.springbootexample.mybatis.mapper.TestMapper;
import com.spring.demo.springbootexample.mybatis.po.Test;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.jws.WebResult;

/**
 * class_name: TestController
 * package: com.spring.demo.springbootexample.controller
 * describe: 测试类控制器
 * creat_user: haoxiaol
 * creat_date: 2018/11/16
 * creat_time: 14:57
 **/
@Controller
@Api(tags = "测试控制器", description = "TestController")
public class TestController {

    private final Logger logger = LoggerFactory.getLogger(TestController.class);

    //替换成自己生成的mapper
    @Autowired
    TestMapper userMapper;

    @GetMapping("/test")
    @ApiOperation(value = "返回test表中的记录", notes = "创建图书")
    @ResponseBody
    public Test test() {
        //查询该表的所有数据
        return userMapper.selectByPrimaryKey("1");
    }

    @GetMapping("/logger")
    @ResponseBody
    public String logger(@RequestParam @ApiParam("测试日志的ID") String id) {
        logger.trace("日志输出 {}", "trace");
        logger.debug("日志输出 {}", "debug");
        logger.info("日志输出 {}", "info");
        logger.warn("日志输出 {}", "warn");
        logger.error("日志输出 {}", "error");
        return "成功打印";
    }


}
