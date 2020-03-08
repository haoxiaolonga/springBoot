package com.spring.demo.springbootexample.protocol.sso.sysfunrole;

import java.io.Serializable;

import lombok.Data;

@Data
public class SysRoleFunTreeDTO  implements Serializable {
    /**
     * 功能代号
     */
    private String funid;
    
    /**
     * 功能描述
     */
    private String fundesc;
    
    /**
     * 父节点id
     */
    private String parentid;
    
    /**
     * 父节点名称
     */
    private String parentName;
    
    /**
     * 是否使用
     */
    private String useflag;
    
    /**
     * 是否底层权限
     */
    private String botflag;
    
    
    /**
     * 是否展示菜单权限
     */
    private String displayfun;
    
    
    /**   
     * @Fields checked : 是否选择
     */   
    private boolean checked;
    
    private int orderval;
    
    private static final long serialVersionUID = 1L;
}