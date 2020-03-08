package com.spring.demo.springbootexample.sso.sysrolefun.mapper;

import com.spring.demo.springbootexample.base.BaseMapper;
import com.spring.demo.springbootexample.protocol.InsertInto;
import com.spring.demo.springbootexample.protocol.sso.sysfunrole.SysRoleFunBatchDTO;
import com.spring.demo.springbootexample.protocol.sso.sysfunrole.SysRoleFunDTO;
import com.spring.demo.springbootexample.protocol.sso.sysfunrole.SysRoleFunTreeDTO;
import com.spring.demo.springbootexample.sso.sysrolefun.entity.SysRoleFun;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

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