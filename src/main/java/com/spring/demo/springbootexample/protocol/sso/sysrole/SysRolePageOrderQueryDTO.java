/**  
 * All rights Reserved, Designed By http://www.pete-cat.com/
 * 角色
 * @Title:  SysRolePageOrderQueryDTO.java   
 * @Package com.spring.demo.springbootexample.protocol.sso.sysrole
 * @author: 成都皮特猫科技     
 * @date:2018年3月26日 下午1:46:58   
 * @version V1.0 
 * @Copyright: 2018 www.pete-cat.com Inc. All rights reserved. 
 * 注意：本内容仅限于成都皮特猫信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */  
package com.spring.demo.springbootexample.protocol.sso.sysrole;

import com.spring.demo.springbootexample.protocol.PageDTO;

import lombok.Data;

/** 
 *      
 * @ClassName:  SysRolePageOrderQueryDTO   
 * @author: admin
 * @date:   2018年3月26日 下午1:46:58   
 */
@Data
public class SysRolePageOrderQueryDTO extends PageDTO{


	/**
     * 角色id
     */
    private String roleid;

    /**
     * 角色名称
     */
    private String roledesc;

    /**
     * 审核标示
     */
    private String markflag;
    

    private String orderRoleIds;
}
