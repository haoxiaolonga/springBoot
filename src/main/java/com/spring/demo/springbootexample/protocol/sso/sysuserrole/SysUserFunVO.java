package com.spring.demo.springbootexample.protocol.sso.sysuserrole;

import java.io.Serializable;

import lombok.Data;

@Data
public class SysUserFunVO  implements Serializable {
	 /**
     * 功能代号
     */
    private String funid;

    /**
     * 排序
     */
    private Integer orderval;

    /**
     * 当前层
     */
    private Integer layid;

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
     * 父功能代号
     */
    private String parentName;

    /**
     * sy_chanmgrolem
     */
    private static final long serialVersionUID = 1L;
}