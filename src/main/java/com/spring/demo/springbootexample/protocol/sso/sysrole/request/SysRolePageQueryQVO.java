package com.spring.demo.springbootexample.protocol.sso.sysrole.request;

import java.io.Serializable;

import com.spring.demo.springbootexample.protocol.PageDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SysRolePageQueryQVO extends PageDTO  implements Serializable {

	/**
     * 角色id
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
    

    private String orderRoleIds;
    
    /**
     * sy_chanmgrolem
     */
    private static final long serialVersionUID = 1L;
}