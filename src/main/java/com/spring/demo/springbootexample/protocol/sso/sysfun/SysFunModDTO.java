package com.spring.demo.springbootexample.protocol.sso.sysfun;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysFunModDTO  implements Serializable {
    /**
     * 功能代号
     */
    private String funid;

    /**
     * 排序
     */
    private Integer orderval;

    /**
     * 功能名称
     */
    private String fundesc;

    /**
     * 对应页面路径
     */
    private String funpath;

    /**
     * 父功能代号
     */
    private String parentid;

    /**
     * 是否是底层权限[0=否/1=是(default)})]
     */
    private String botflag = "0";

    /**
     * 是否在使用[0=否/1=是(default)})]
     */
    private String useflag = "0";

    /**
     * 是否展示【0：否/1：是】
     */
    private String displayfun = "0";
    
    /**   
     * @Fields powerPaths : 可访问权限路径
     */   
    private String powerPaths;
    /**
     * sy_chanmgfunm
     */
    private static final long serialVersionUID = 1L;
}