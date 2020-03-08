package com.spring.demo.springbootexample.protocol.sso.sysrole.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysRoleRVO  implements Serializable {
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