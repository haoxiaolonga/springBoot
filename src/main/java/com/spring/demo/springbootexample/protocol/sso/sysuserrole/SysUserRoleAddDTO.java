package com.spring.demo.springbootexample.protocol.sso.sysuserrole;

import java.io.Serializable;

import lombok.Data;

@Data
public class SysUserRoleAddDTO  implements Serializable {
    /**
     * 用户代码
     */
    private String userid;

    /**
     *  角色代码
     */
    private String roleid;

    
    private static final long serialVersionUID = 1L;
}