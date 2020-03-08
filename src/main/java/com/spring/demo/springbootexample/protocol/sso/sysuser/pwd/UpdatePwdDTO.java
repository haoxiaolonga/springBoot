package com.spring.demo.springbootexample.protocol.sso.sysuser.pwd;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

/**
 * 
 * @ClassName:  UpdatePwd   
 * @Description:修改密码
 * @author: mhuang
 * @date:   2017年7月23日 上午11:08:26
 */
@Data
public class UpdatePwdDTO {

	
	/**
	 * 密码
	 */
	@Getter(onMethod_={@ApiModelProperty(value = "密码", required = true)})
	private String password;
	
	/**
	 * 用户id
	 */
	@Getter(onMethod_={@ApiModelProperty(value = "用户id", hidden = true)})
	private String userid;
}
