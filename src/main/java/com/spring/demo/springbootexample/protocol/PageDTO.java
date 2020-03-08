package com.spring.demo.springbootexample.protocol;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PageDTO {

    @ApiModelProperty(value="每页行数,默认10行",required=true)
    Integer rows = 10;//行数

    @ApiModelProperty(value="开始页数",required=true)
    Integer start = 1;//条数
}
