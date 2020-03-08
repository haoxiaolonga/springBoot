package com.spring.demo.springbootexample.sso.sysuserrole.service;

import com.spring.demo.springbootexample.base.BaseService;
import com.spring.demo.springbootexample.protocol.sso.sysuserrole.SysUserFunDTO;
import com.spring.demo.springbootexample.protocol.sso.sysuserrole.SysUserRoleAddDTO;
import com.spring.demo.springbootexample.protocol.sso.sysuserrole.SysUserRoleCheckDTO;
import com.spring.demo.springbootexample.protocol.sso.sysuserrole.SysUserRoleDTO;
import com.spring.demo.springbootexample.sso.sysuserrole.entity.SysUserRole;

import java.util.List;


/**   
 * @ClassName:  ISysUserRoleService   
 * @Description:系统功能权限服务  
 * @author: admin
 * @date:   2017年7月21日 下午8:13:48   
 */  
public interface ISysUserRoleService extends BaseService<SysUserRole, String> {

	/**   
	 * @Title: saveUserRole   
	 * @Description: 保存用户角色  
	 * @param sysUserRoleAddDTO
	 * @param userId
	 * @return void     
	 */
	void saveUserRole(SysUserRoleAddDTO sysUserRoleAddDTO, String userId);

	/**   
	 * @Title: queryUserRole   
	 * @Description: 查询用户角色  
	 * @param userid
	 * @return List<SysUserRoleDTO>     
	 */
	List<SysUserRoleDTO> queryUserRole(String userid);

	/**   
	 * @Title: queryUserFun   
	 * @Description: 查询用户权限
	 * @param userid
	 * @return List<SysUserFunDTO>     
	 */
	List<SysUserFunDTO> queryUserFun(String userid);

	/**   
	 * @Title: queryUserRoleCheck   
	 * @Description: 查询用户角色选择信息
	 * @param userid
	 * @return List<SysUserRoleCheckDTO>     
	 */
	List<SysUserRoleCheckDTO> queryUserRoleCheck(String userid);

	/**   
	 * @Title: sysUserRoleService   
	 * @Description: 删除人员角色
	 * @param roleid
	 * @param userId
	 * @return void     
	 */
	void sysUserRoleService(String roleid, String userId);

	/** 
	 * 设置用户角色 
	 * @Title: setUserRole
	 * @param userId
	 * @return void     
	 */
	void setUserRoleToCache(String userId);


}
