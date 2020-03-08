package com.spring.demo.springbootexample.protocol.sso.sysuser.pwd;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

@Data
public class ResetPwdDTO {

	/**
	 * 密码
	 */
	@Getter(onMethod_={@ApiModelProperty(value = "密码", required = true)})
	private String password;
	
	/**
	 * 用户id
	 */
	@Getter(onMethod_={@ApiModelProperty(value = "用户id", required = true)})
	private String userid;
	
	/**
	 * 操作的用户id
	 */
	@Getter(onMethod_={@ApiModelProperty(value = "操作用户id", hidden = true)})
	private String operateUser;
}
