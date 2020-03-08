package com.spring.demo.springbootexample.protocol.sso.sysfunvisturl;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @author 
 */
@Data
public class SyChanmgfunExcludeUrlVO implements Serializable {
    /**
     * 主键
     */
    private String type;

    /**
     * 可访问地址
     */
    private String url;

    /**
     * 操作时间
     */
    private Date operateTime;

    /**
     * 操作者
     */
    private String operateUser;

    private static final long serialVersionUID = 1L;

}