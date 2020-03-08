package com.spring.demo.springbootexample.protocol.sso.sysfun;

import java.io.Serializable;

import lombok.Data;

@Data
public class SysFunTreeQueryDTO  implements Serializable {
    /**
     * 功能代号
     */
    private String funid;
    
    private String nodeid;

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
    private String botflag;

    /**
     * 是否在使用[0=否/1=是(default)})]
     */
    private String useflag;

    /**
     * 是否展示【0：否/1：是】
     */
    private String displayfun;
    

    /**
     * sy_chanmgfunm
     */
    private static final long serialVersionUID = 1L;
}