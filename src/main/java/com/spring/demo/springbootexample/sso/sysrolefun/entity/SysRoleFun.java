package com.petecat.interchan.sso.sysrolefun.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SysRoleFun implements Serializable {
	  /**
     * 权限ID
     */
    private String funid;

    /**
     * 用户ID
     */
    private String roleid;
    
    /**
     * 操作时间
     */
    private Date operateTime;

    /**
     * 操作者
     */
    private String operateUser;

    /**
     * sy_chanmgrlfunm
     */
    private static final long serialVersionUID = 1L;
}