package com.spring.demo.springbootexample.sso.sysuser.domain;

import java.io.Serializable;
import java.util.Date;

import com.petecat.interchan.core.entity.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 
 * @ClassName:  SysUser   
 * @Description:系统用户表  
 * @author: mhuang
 * @date:   2017年7月18日 上午10:52:50
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class SysUserDO extends BaseEntity implements Serializable{

	/**
     * sy_chanmguserm
     */
    private static final long serialVersionUID = 1L;
    
	/**
     * 用户唯一编号（建议从100000000开始）
     */
    private String userid;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户登陆时是否要求修改密码[0=否/1=是(default)／9=用户锁定]
     */
    private String chgpwdflag;

    /**
     * 密码有效天数
     */
    private Integer validday;

    /**
     * 密码到期前警告天数
     */
    private Integer warningday;

    /**
     * Email
     */
    private String email;

    /**
     * 电话
     */
    private String tel;

    /**
     * 移动电话
     */
    private String mobilephone;

    /**
     * 地址
     */
    private String address;

    /**
     * 注册日期
     */
    private Date entrantdate;

    /**
     * 用户账号是否有效[1=有效(default)/0=无效]
     */
    private String available;

    /**
     * 操作者
     */
    private String operateUser;

    /**
     * 操作时间
     */
    private Date operateTime;
    
    /**   
     * @Fields orderUserIds : 排序用户
     */   
    private String orderUserIds;

}
