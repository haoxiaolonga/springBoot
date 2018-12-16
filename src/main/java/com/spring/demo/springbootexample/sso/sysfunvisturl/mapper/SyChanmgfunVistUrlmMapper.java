package com.spring.demo.springbootexample.sso.sysfunvisturl.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.petecat.interchan.core.mapper.BaseMapper;
import com.petecat.interchan.protocol.InsertInto;
import com.petecat.interchan.sso.sysfunvisturl.entity.SyChanmgfunExcludeUrl;
import com.petecat.interchan.sso.sysfunvisturl.entity.SyChanmgfunVistUrlm;
import com.petecat.interchan.sso.sysuser.entity.SysUser;
@Mapper
@Repository("syChanmgfunVistUrlmMapper")
public interface SyChanmgfunVistUrlmMapper extends BaseMapper<SyChanmgfunVistUrlm,String>{

	/**   
	 * @Title: insertIntoByAuth   
	 * @Description: 插入历史通过权限
	 * @param into
	 * @return void     
	 */
	void insertIntoByAuth(InsertInto<String> into);

	/**   
	 * @Title: deleteByAuth   
	 * @Description:通过权限删除数据   
	 * @param funid
	 * @return void     
	 */
	void deleteByAuth(String funid);

	/**   
	 * @Title: queryFunVist   
	 * @Description: 查询访问路径
	 * @param funid
	 * @return List<SyChanmgfunVistUrlm>     
	 */
	List<SyChanmgfunVistUrlm> queryFunVist(String funid);

	/**   
	 * @Title: getExcludeUrl   
	 * @Description: 查询排除路径
	 * @return List<SyChanmgfunExcludeUrl>     
	 */
	List<SyChanmgfunExcludeUrl> getExcludeUrl();

	/**   
	 * @Title: checkUrlPower   
	 * @Description: 检查访问地址权限
	 * @param userId
	 * @param checkUrl
	 * @return boolean     
	 */
	boolean checkUrlPower(@Param("userId") String userId, @Param("checkUrl") String checkUrl);
    
	List<SyChanmgfunVistUrlm>  getUserUrlPower(String userId);

	/**   
	 * @Title: queryUserByRole   
	 * @Description: 查询具有角色的用户
	 * @param roleIds
	 * @return List<SysUser>     
	 */
	List<SysUser> queryUserByRole(List<String> roleIds);

	/** 
	 * 批量新增 
	 * @Title: insertIntoByAuths
	 * @param into
	 * @return void     
	 */
	void insertIntoByAuths(InsertInto<List<String>> into);

	/** 
	 * 批量删除  
	 * @Title: deleteByAuths
	 * @param ids
	 * @return void     
	 */
	void deleteByAuths(List<String> ids);
}