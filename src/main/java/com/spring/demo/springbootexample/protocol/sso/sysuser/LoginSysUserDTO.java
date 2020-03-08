package com.spring.demo.springbootexample.protocol.sso.sysuser;

import lombok.Data;

@Data
public class LoginSysUserDTO {

	/**
     * 用户名称
     */
    private String username;
    

	/**
     * 用户ID
     */
    private String userid;
    

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
     * 地址
     */
    private String address;
    

	  /**
   * 用户登陆时是否要求修改密码
   */
  private String chgpwdflag;
	
	  /**
   * 用户账号是否有效[1=有效(default)})/0=无效]
   */
  private String available;
	
}
