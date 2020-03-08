package com.spring.demo.springbootexample.protocol;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public class PageVO<T> {

    /**
     * 总行数
     */
    @ApiModelProperty(value = "总条数")
    private int totalSize;

    /**
     * 业务数据 <LIST>
     */
    @ApiModelProperty(value = "业务数据")
    private List<T> result = Collections.emptyList();
}