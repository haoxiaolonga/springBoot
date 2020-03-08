package com.spring.demo.springbootexample.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlobalHeader{

    private String userId;//用户id

    private String type;//用户类型

    private String companyId;//公司id

    private String token;//用户token

    private String ip;//用户ip
}