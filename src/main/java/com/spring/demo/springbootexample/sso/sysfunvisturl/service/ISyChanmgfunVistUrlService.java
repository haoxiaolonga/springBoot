package com.spring.demo.springbootexample.sso.sysfunvisturl.service;

import java.util.List;

import org.springframework.scheduling.annotation.Async;

import com.petecat.interchan.core.service.BaseService;
import com.petecat.interchan.protocol.sso.sysfunvisturl.SyChanmgfunExcludeUrlDTO;
import com.petecat.interchan.protocol.sso.sysfunvisturl.SyChanmgfunVistUrlmAddDTO;
import com.petecat.interchan.protocol.sso.sysfunvisturl.SyChanmgfunVistUrlmQryDTO;
import com.petecat.interchan.sso.sysfunvisturl.entity.SyChanmgfunVistUrlm;
import com.petecat.interchan.sso.sysrole.entity.SysRole;

/**
 * 
 * @ClassName:  ISyChanmgfunVistUrlService   
 * @Description:权限可访问服务
 * @author: 张小虎
 * @date:   2017年7月19日 上午10:06:08
 */
public interface ISyChanmgfunVistUrlService extends BaseService<SyChanmgfunVistUrlm, String>{

	/**   
	 * @Title: deleteByAuth   
	 * @Description: 删除通过历史
	 * @param funid
	 * @return void     
	 */
	void deleteByAuth(String funid);

	/**   
	 * @Title: insertPowersUrl   
	 * @Description: 插入权限
	 * @param dtos
	 * @return void     
	 */
	void insertPowersUrl(List<SyChanmgfunVistUrlmAddDTO> dtos, String userId, String seqno);

	/**   
	 * @Title: insertHByAuth   
	 * @Description: 插入历史通过权限
	 * @param funid
	 * @param userId
	 * @param status
	 * @param seqno 
	 * @return void     
	 */
	void insertHByAuth(String funid, String userId, String status, String seqno);

	/**   
	 * @Title: queryFunVist   
	 * @Description:通过权限查询访问地址
	 * @param funid
	 * @return List<SyChanmgfunVistUrlmQryDTO>     
	 */
	List<SyChanmgfunVistUrlmQryDTO> queryFunVist(String funid);

	/**   
	 * @Title: setExcludeUrl   
	 * @Description: 设置排除url
	 * @return void     
	 */
	List<SyChanmgfunExcludeUrlDTO> setExcludeUrl();

	/**   
	 * @Title: setVistUrlPower   
	 * @Description: 设置用户访问权限
	 * @param userid
	 * @return void     
	 */
	void setVistUrlPower(String userid);

	/**   
	 * @Title: setVistUrlPowerNow   
	 * @Description: 设置用户访问权限 
	 * @param userId
	 * @return void     
	 */
	void setVistUrlPowerNow(String userId);

	/**   
	 * @Title: setUserVistPowerByRoleAsync   
	 * @Description: 设置人员角色权限信息
	 * @param roleid
	 * @return void     
	 */
	@Async
	void setUserVistPowerByRoleAsync(String roleid);

	/**   
	 * @Title: setUserVistPowerByRolesAsync   
	 * @Description:设置用户访问权限
	 * @param roles
	 * @return void     
	 */
	void setUserVistPowerByRolesAsync(List<SysRole> roles);

	/** 
	 * 批量删除   
	 * @Title: deleteByFunsIds
	 * @param ids
	 * @param userId
	 * @param delete
	 * @param reqNo
	 * @return void     
	 */
	void deleteByFunsIds(List<String> ids, String userId, String delete, String reqNo);

	/** 
	 * 重新加载可访问路径
	 * @Title: reloadVistUrl
	 * @param userId
	 * @return void     
	 */
	void reloadVistUrl(String userId);

}
