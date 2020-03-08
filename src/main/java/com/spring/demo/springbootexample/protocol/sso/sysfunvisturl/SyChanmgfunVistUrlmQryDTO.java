package com.spring.demo.springbootexample.protocol.sso.sysfunvisturl;

import java.io.Serializable;

import lombok.Data;

/**
 * @author 
 */
@Data
public class SyChanmgfunVistUrlmQryDTO implements Serializable {
    /**
     * 主键
     */
    private String id;

    /**
     * 可访问地址
     */
    private String url;

    /**
     * 功能代码
     */
    private String funid;

    private static final long serialVersionUID = 1L;

}