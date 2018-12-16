package com.spring.demo.springbootexample.sso.sysrolefun.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SysRoleFunRecord implements Serializable {
	  /**
     * 权限ID
     */
    private String funid;

    /**
     * 用户ID
     */
    private String roleid;
    
    /**
     * 历史序列值
     */
    private String seqno;
    
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