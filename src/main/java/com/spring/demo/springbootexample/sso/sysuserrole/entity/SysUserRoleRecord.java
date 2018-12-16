package com.petecat.interchan.sso.sysuserrole.entity;

import java.io.Serializable;
import java.util.Date;

import com.petecat.interchan.core.entity.BaseEntity;

import lombok.Data;

@Data
public class SysUserRoleRecord extends BaseEntity implements Serializable {
	
	/**
     * 角色ID
     */
    private String roleid;

    /**
     * 用户ID
     */
    private String userid;
    /**
     * 操作时间
     */
    private Date operateTime;

    /**
     * 操作者
     */
    private String operateUser;
    

    /**
     * 序列值
     */
    private int seqno;
    
    /**
     * 操作类型
     */
    private String operateStatus;

    /**
     * sy_chanmgurrlm
     */
    private static final long serialVersionUID = 1L;
}