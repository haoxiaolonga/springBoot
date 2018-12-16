/**  
 * All rights Reserved, Designed By http://www.pete-cat.com/
 * TODO(用一句话描述该文件做什么)   
 * @Title:  SysRolePageQueryQVO.java   
 * @Package com.petecat.interchan.sso.sysrole.domain  
 * @author: 成都皮特猫科技     
 * @date:2018年3月26日 下午1:56:42   
 * @version V1.0 
 * @Copyright: 2018 www.pete-cat.com Inc. All rights reserved. 
 * 注意：本内容仅限于成都皮特猫信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */  
package com.petecat.interchan.sso.sysrole.domain;

import lombok.Data;

/** 
 * 
 * @ClassName:  SysRolePageQueryQVO   
 * @author: admin
 * @date:   2018年3月26日 下午1:56:42   
 */
@Data
public class SysRolePageQueryDO {

	/**
     * 角色id
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
    
    private String orderRoleIds;
}
