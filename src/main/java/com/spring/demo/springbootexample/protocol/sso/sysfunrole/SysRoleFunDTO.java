package com.spring.demo.springbootexample.protocol.sso.sysfunrole;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class SysRoleFunDTO  implements Serializable {
    /**
     * 功能代号
     */
    private String funid;
    
    /**
     * 功能名称
     */
    private String fundesc;
    
    /**
     * 父代码
     */
    private String parentid;

    /**
     * 父功能名称
     */
    private String parentName;
    
    private int orderval;

    /**
     * 当前层
     */
    private Integer layid;

    /**
     * 对应页面路径
     */
    private String funpath;


    /**
     * 是否是底层权限[0=否/1=是(default)]
     */
    private String botflag;

    /**
     * 是否在使用[0=否/1=是(default)]
     */
    private String useflag;

    /**
     * 是否展示【0：否/1：是】
     */
    private String displayfun;

    /**
     * 操作者
     */
    private String operateUser;

    /**
     * 操作时间
     */
    private Date operateTime;

    /**
     * 附件ID
     */

    private String attachid;
    
    /**
     * sy_chanmgfunm
     */
    private static final long serialVersionUID = 1L;
}