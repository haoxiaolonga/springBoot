package com.spring.demo.springbootexample.sso.sysrolefun.service;

import java.util.List;

import com.petecat.interchan.core.service.BaseService;
import com.petecat.interchan.protocol.sso.sysfunrole.SysRoleFunAddDTO;
import com.petecat.interchan.protocol.sso.sysfunrole.SysRoleFunDTO;
import com.petecat.interchan.protocol.sso.sysfunrole.SysRoleFunTreeDTO;
import com.petecat.interchan.sso.sysrolefun.entity.SysRoleFun;


/**   
 * @ClassName:  ISysRoleFunService   
 * @Description:系统功能权限服务  
 * @author: admin
 * @date:   2017年7月21日 下午8:13:48   
 */  
public interface ISysRoleFunService extends BaseService<SysRoleFun, String>{

	/**   
	 * @Title: saveRoleFun   
	 * @Description: 新增角色权限  
	 * @param sysRoleFunAddDTO
	 * @param userId
	 * @return void     
	 */
	void saveRoleFun(SysRoleFunAddDTO sysRoleFunAddDTO, String userId);

	/**   
	 * @Title: queryRoleFunTree   
	 * @Description: 查询角色树
	 * @param roleid
	 * @return List<SysRoleFunTreeDTO>     
	 */
	List<SysRoleFunTreeDTO> queryRoleFunTree(String roleid);

	/**   
	 * @Title: queryRoleFun   
	 * @Description: 查询角色功能权限
	 * @param roleid
	 * @return List<SysRoleFunDTO>     
	 */
	List<SysRoleFunDTO> queryRoleFun(String roleid);

	/**   
	 * @Title: deleteRoleFunByRole   
	 * @Description: 删除角色权限
	 * @param roleid
	 * @param userId
	 * @return void     
	 */
	void deleteRoleFunByRole(String roleid, String userId);

	/**   
	 * @Title: deleteRoleFunByFuns   
	 * @Description: 删除角色权限，通过权限ID 
	 * @param ids
	 * @return void     
	 */
	void deleteRoleFunByFuns(List<String> ids, String userId);

}
