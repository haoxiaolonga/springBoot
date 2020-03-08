package com.spring.demo.springbootexample.protocol.sso.sysuser;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

@Data
public class LoginSysUserVO {

	/**
     * 用户名称
     */
	@Getter(onMethod_={@ApiModelProperty(value = "用户名")})
    private String username;


	/**
     * 用户ID
     */
	@Getter(onMethod_={@ApiModelProperty(value = "用户ID")})
    private String userid;
    
    
    /**
     * 密码
     */
	@Getter(onMethod_={@ApiModelProperty(value = "密码")})
    private String password; 
    
    /**
     * Email
     */
	@Getter(onMethod_={@ApiModelProperty(value = "邮箱")})
    private String email;
    
    /**
     * 电话
     */
	@Getter(onMethod_={@ApiModelProperty(value = "电话")})
    private String tel;
    
    /**
     * 移动电话
     */
	@Getter(onMethod_={@ApiModelProperty(value = "移动电话")})
    private String mobilephone;

    /**
     * 地址
     */
	@Getter(onMethod_={@ApiModelProperty(value = "地址")})
    private String address;
	
	
	  /**
     * 令牌
     */
	@Getter(onMethod_={@ApiModelProperty(value = "令牌")})
    private String token;
	

		  /**
	 * 用户登陆时是否要求修改密码
	 */
	@Getter(onMethod_={@ApiModelProperty(value = "用户登陆时是否要求修改密码")})
	private String chgpwdflag;
	
}
