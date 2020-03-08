package com.spring.demo.springbootexample.sso.sysrole.service;

import com.spring.demo.springbootexample.base.BaseService;
import com.spring.demo.springbootexample.protocol.PageVO;
import com.spring.demo.springbootexample.protocol.sso.sysrole.SysRoleAddDTO;
import com.spring.demo.springbootexample.protocol.sso.sysrole.SysRoleModDTO;
import com.spring.demo.springbootexample.protocol.sso.sysrole.SysRolePageQueryDTO;
import com.spring.demo.springbootexample.protocol.sso.sysrole.SysRoleQueryDTO;
import com.spring.demo.springbootexample.protocol.sso.sysrole.SysRoleVO;
import com.spring.demo.springbootexample.protocol.sso.sysrole.request.SysRolePageQueryQVO;
import com.spring.demo.springbootexample.sso.sysrole.entity.SysRole;

import java.util.List;


public interface ISysRoleService extends BaseService<SysRole, String> {

	/**   
	 * @Title: saveRole   
	 * @Description: 新增角色信息
	 * @param sysRoleAddDTO 角色参数
	 * @param userId 操作人
	 * @return void     
	 */
	void saveRole(SysRoleAddDTO sysRoleAddDTO, String userId);

	/**   
	 * @Title: updateRole   
	 * @Description:修改角色  
	 * @param sysRoleModDTO
	 * @param userId
	 * @return void     
	 */
	void updateRole(SysRoleModDTO sysRoleModDTO, String userId);

	/**   
	 * @Title: queryRoleByPage   
	 * @Description: 分页查询角色  
	 * @param dto
	 * @return PageVo<SysRoleVo>     
	 */
	PageVO<SysRoleVO> queryRoleByPage(SysRolePageQueryDTO dto);

	/**   
	 * @Title: deleteRole   
	 * @Description: 删除角色
	 * @param roleid 角色id
	 * @param userid 用户ID
	 * @return void     
	 */
	void deleteRole(String roleid, String userid);

	/**   
	 * @Title: queryRole   
	 * @Description:查询角色
	 * @param roleid 角色id
	 * @param nullException 为空是否异常
	 * @return SysRoleQueryDTO    
	 */
	SysRoleQueryDTO queryRole(String roleid, boolean nullException);

	/** 
	 * 分页查询角色
	 * @Title: pageOrderRole
	 * @param dto
	 * @return List<SysRole>     
	 */
	List<SysRole> pageOrderRole(SysRolePageQueryQVO dto);

	/** 
	 * 角色ID列表
	 * @Title: findByRoleIds
	 * @param roleIds
	 * @return List<SysRole>     
	 */
	List<SysRole> findByRoleIds(List<String> roleIds);

}
