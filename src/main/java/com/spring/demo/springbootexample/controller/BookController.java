package com.spring.demo.springbootexample.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import com.spring.demo.springbootexample.mybatis.po.Test;

import java.awt.print.Book;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@Api(tags = "图书控制器", description = "BookController")
@RequestMapping(value = "/books")
public class BookController {

    Map<String, Test> books = Collections.synchronizedMap(new HashMap<String, Test>());

    @ApiOperation(value="创建图书", notes="创建图书")
    @ApiImplicitParam(name = "test", value = "图书详细实体", required = true, dataType = "Test")
    @PostMapping(value="/add")
    public String postBook(@RequestBody Test test) {
        books.put(test.getId(), test);
        return "success";
    }

    @ApiOperation(value = "获图书细信息", notes = "根据url的id来获取详细信息")
    @GetMapping(value = "/{id}")
    public Test getBook(@RequestParam @ApiParam("主键Id") String id) {
        return books.get(id);
    }
}