/**  
 * All rights Reserved, Designed By http://www.pete-cat.com/
 * TODO(用一句话描述该文件做什么)})
 * @Title:  SysUserPageDTO.java   
 * @Package com.spring.demo.springbootexample.protocol.sso.sysuser.dto
 * @author: 成都皮特猫科技     
 * @date:2018年3月23日 上午10:06:29   
 * @version V1.0 
 * @Copyright: 2018 www.pete-cat.com Inc. All rights reserved. 
 * 注意：本内容仅限于成都皮特猫信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */  
package com.spring.demo.springbootexample.protocol.sso.sysuser.dto;

import lombok.Data;

/** 
 * 分页查询DTO    
 * @ClassName:  SysUserPageDTO   
 * @author: admin
 * @date:   2018年3月23日 上午10:06:29   
 */
@Data
public class SysUserPageDTO {
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
     * 用户账号是否有效[1=有效(default)})/0=无效]
     */
    private String available;
    /**
     * 地址
     */
    private String address;
}
