package com.petecat.interchan.sso.sysrole.service;

import java.util.List;

import com.petecat.interchan.core.service.BaseService;
import com.petecat.interchan.protocol.data.PageVO;
import com.petecat.interchan.protocol.sso.sysrole.SysRoleAddDTO;
import com.petecat.interchan.protocol.sso.sysrole.SysRoleModDTO;
import com.petecat.interchan.protocol.sso.sysrole.SysRolePageQueryDTO;
import com.petecat.interchan.protocol.sso.sysrole.SysRoleQueryDTO;
import com.petecat.interchan.protocol.sso.sysrole.SysRoleVO;
import com.petecat.interchan.protocol.sso.sysrole.request.SysRolePageQueryQVO;
import com.petecat.interchan.sso.sysrole.entity.SysRole;

/**
 * 
 * @ClassName:  ISysRoleService   
 * @Description:系统角色服务
 * @author: 张小虎
 * @date:   2017年7月19日 上午10:06:08
 */
public interface ISysRoleService extends BaseService<SysRole, String>{

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
