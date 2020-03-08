package com.spring.demo.springbootexample.protocol.sso.sysuser;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

@Data
public class SysUserUpdateDTO {

	/**
	 * 用户名
	 */
	@Getter(onMethod_={@ApiModelProperty(value = "用户名")})
	private String username;
	
	/**
	 * 密码
	 */
	@Getter(onMethod_={@ApiModelProperty(value = "密码")})
	private String password;
	
	/**
	 * 邮件
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
	 * 用户id
	 */
	@Getter(onMethod_={@ApiModelProperty(value = "用户id")})
	private String userid;
	
	/**
     * 操作者
     */
	@Getter(onMethod_={@ApiModelProperty(hidden = true)})
    private String operateUser;
	
}
