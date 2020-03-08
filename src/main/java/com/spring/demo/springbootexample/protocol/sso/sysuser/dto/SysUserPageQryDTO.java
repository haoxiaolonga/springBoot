package com.spring.demo.springbootexample.protocol.sso.sysuser.dto;

import com.spring.demo.springbootexample.protocol.PageDTO;
import lombok.Data;
/** 
 * 用户分页查询DTO     
 * @ClassName:  SysUserPageQryDTO   
 * @author: admin
 * @date:   2018年3月23日 上午9:59:51   
 */
@Data
public class SysUserPageQryDTO extends PageDTO {

	private String username;
	
	private String mobilephone;
	
	private String orderUserIds;
	
	private String available;
}
