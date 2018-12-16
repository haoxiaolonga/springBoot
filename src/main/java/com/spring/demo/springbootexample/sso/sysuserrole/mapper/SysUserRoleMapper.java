package com.petecat.interchan.sso.sysuserrole.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.petecat.interchan.core.mapper.BaseMapper;
import com.petecat.interchan.protocol.InsertInto;
import com.petecat.interchan.protocol.sso.sysuserrole.SysUserFunDTO;
import com.petecat.interchan.protocol.sso.sysuserrole.SysUserRoleBatchDTO;
import com.petecat.interchan.protocol.sso.sysuserrole.SysUserRoleCheckDTO;
import com.petecat.interchan.protocol.sso.sysuserrole.SysUserRoleDTO;
import com.petecat.interchan.sso.sysuserrole.entity.SysUserRole;

/**
 * 
 * @ClassName:  SysRoleFunMapper   
 * @Description:系统角色功能权限mapper  
 * @author: 张小虎
 * @date:   2017年7月19日 上午10:11:32
 */
@Mapper
@Repository
public interface SysUserRoleMapper extends BaseMapper<SysUserRole, String> {

	/**   
	 * @Title: deleteByUserId   
	 * @Description: 删除用户角色  
	 * @param userid
	 * @return void     
	 */
	void deleteByUserId(String userid);

	/**   
	 * @Title: addUserRole   
	 * @Description: 新增用户角色
	 * @param batchDTO
	 * @return void     
	 */
	void addUserRole(SysUserRoleBatchDTO batchDTO);

	/**   
	 * @Title: queryUserRole   
	 * @Description: 查询用户角色   
	 * @param userid
	 * @return List<SysUserRoleDTO>     
	 */
	List<SysUserRoleDTO> queryUserRole(String userid);

	/**   
	 * @Title: queryUserFun   
	 * @Description:查询用户权限   
	 * @param userid
	 * @return List<SysUserFunDTO>     
	 */
	List<SysUserFunDTO> queryUserFun(String userid);

	/**   
	 * @Title: queryUserRoleCheck   
	 * @Description: 查询用户角色选中信息
	 * @param userid
	 * @return List<SysUserRoleCheckDTO>     
	 */
	List<SysUserRoleCheckDTO> queryUserRoleCheck(String userid);

	/**   
	 * @Title: insertIntoByRoleId   
	 * @Description: 参数历史，通过角色ID
	 * @param into
	 * @return void     
	 */
	void insertIntoByRoleId(InsertInto<String> into);

	/**   
	 * @Title: deleteByRoleId   
	 * @Description: 删除人员通过角色ID
	 * @param roleid
	 * @return void     
	 */
	void deleteByRoleId(String roleid);

	
}