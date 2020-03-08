package com.spring.demo.springbootexample.protocol.sso.sysrole;

import java.io.Serializable;

import lombok.Data;

@Data
public class SysRoleQueryDTO  implements Serializable {
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
    
    /**
     * sy_chanmgrolem
     */
    private static final long serialVersionUID = 1L;
}