package com.petecat.interchan.sso.sysrolefun.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.petecat.interchan.core.mapper.BaseMapper;
import com.petecat.interchan.protocol.InsertInto;
import com.petecat.interchan.protocol.sso.sysfunrole.SysRoleFunBatchDTO;
import com.petecat.interchan.protocol.sso.sysfunrole.SysRoleFunDTO;
import com.petecat.interchan.protocol.sso.sysfunrole.SysRoleFunTreeDTO;
import com.petecat.interchan.sso.sysrolefun.entity.SysRoleFun;

/**
 * 
 * @ClassName:  SysRoleFunMapper   
 * @Description:系统角色功能权限mapper  
 * @author: 张小虎
 * @date:   2017年7月19日 上午10:11:32
 */
@Mapper
@Repository
public interface SysRoleFunMapper extends BaseMapper<SysRoleFun, String> {

	/**   
	 * @Title: deleteByRoleId   
	 * @Description: 删除角色功能权限   
	 * @param roleId
	 * @return void     
	 */
	public void deleteByRoleId(String roleId);
	
	/**   
	 * @Title: queryRoleFun   
	 * @Description: 查询角色功能 
	 * @param roleId
	 * @return List<SysRoleFunDTO>     
	 */
	List<SysRoleFunDTO>queryRoleFun(String roleId);

	/**   
	 * @Title: queryRoleFunTree   
	 * @Description: 查询角色功能树
	 * @param roleId
	 * @return List<SysRoleFunTreeDTO>     
	 */
	List<SysRoleFunTreeDTO>queryRoleFunTree(String roleId);

	/**   
	 * @Title: addRoleFun   
	 * @Description: 批量新增 
	 * @param batchDTO
	 * @return void     
	 */
	public void addRoleFun(SysRoleFunBatchDTO batchDTO);

	/**   
	 * @Title: insertIntoByFuns   
	 * @Description:插入历史，通过权限
	 * @param into
	 * @return void     
	 */
	public void insertIntoByFuns(InsertInto<List<String>> into);

	/**   
	 * @Title: deleteByFuns   
	 * @Description:删除通过权限id   
	 * @param ids
	 * @return void     
	 */
	public void deleteByFuns(List<String> ids);
	
}