package com.spring.demo.springbootexample.sso.sysrole.entity;

import java.io.Serializable;
import java.util.Date;

import com.petecat.interchan.core.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SysRole extends BaseEntity implements Serializable {
    /**
     * 角色代号
     */
    private String roleid;

    /**
     * 角色名称
     */
    private String roledesc;

    /**
     * 审核标记[0 有效 1 无效]
     */
    private String markflag;

    /**
     * 操作者
     */
    private String operateUser;

    /**
     * 操作时间
     */
    private Date operateTime;

    /**
     * sy_chanmgrolem
     */
    private static final long serialVersionUID = 1L;
}