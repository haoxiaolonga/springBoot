package com.spring.demo.springbootexample.protocol.sso.sysuserrole;

import java.io.Serializable;

import lombok.Data;

@Data
public class SysUserRoleCheckVO  implements Serializable {
    /**
     * 角色代号
     */
    private String roleid;

    /**
     * 角色名称
     */
    private String roledesc;
    /**
     * 审核标示
     */
    private String markflag;
    
    private boolean checked;

    /**
     * sy_chanmgrolem
     */
    private static final long serialVersionUID = 1L;
}