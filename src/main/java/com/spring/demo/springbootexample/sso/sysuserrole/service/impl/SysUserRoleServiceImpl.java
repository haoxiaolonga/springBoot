package com.spring.demo.springbootexample.sso.sysuserrole.service.impl;

import com.spring.demo.springbootexample.base.BaseServiceImpl;
import com.spring.demo.springbootexample.common.DataUtils;
import com.spring.demo.springbootexample.common.IRedisExtCommands;
import com.spring.demo.springbootexample.common.IdWorker;
import com.spring.demo.springbootexample.protocol.InsertInto;
import com.spring.demo.springbootexample.protocol.sso.sysuserrole.SysUserFunDTO;
import com.spring.demo.springbootexample.protocol.sso.sysuserrole.SysUserRoleAddDTO;
import com.spring.demo.springbootexample.protocol.sso.sysuserrole.SysUserRoleBatchDTO;
import com.spring.demo.springbootexample.protocol.sso.sysuserrole.SysUserRoleCheckDTO;
import com.spring.demo.springbootexample.protocol.sso.sysuserrole.SysUserRoleDTO;
import com.spring.demo.springbootexample.sso.sysuserrole.entity.SysUserRole;
import com.spring.demo.springbootexample.sso.sysuserrole.mapper.SysUserRoleMapper;
import com.spring.demo.springbootexample.sso.sysuserrole.service.ISysUserRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 
 * @ClassName:  sysUserRoleService   
 * @Description:系统用户角色
 * @author: 张小虎
 * @date:   2017年7月19日 上午10:10:04
 */
@Service("sysUserRoleService")
@Transactional(readOnly=true)
public class SysUserRoleServiceImpl extends BaseServiceImpl<SysUserRole, String> implements ISysUserRoleService{

	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;
	

	@Autowired
	private IRedisExtCommands redisExtCommands;
	

	public static final String SYSTEM_USER_ROLE_CACHE_KEY = "SYSTEM_USER_ROLE";
	
	@Autowired
	public void setMapper(SysUserRoleMapper sysUserRoleMapper){
		this.setBaseMapper(sysUserRoleMapper);
	}

	/**   
	 * <p>Title: saveUserRole</p>   
	 * <p>Description: </p>   
	 * @param sysUserRoleAddDTO
	 * @see com.spring.demo.springbootexample.sso.sysuserrole.service.ISysUserRoleService#saveUserRole(com.spring.demo.springbootexample.protocol.sso.sysuserrole.SysUserRoleAddDTO, String)
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public void saveUserRole(SysUserRoleAddDTO sysUserRoleAddDTO, String userid) {
		InsertInto<String> into = new InsertInto<>();
		into.setReqNo(new IdWorker().nextId()+"");
		into.setId(sysUserRoleAddDTO.getUserid());
		into.setStatus(InsertInto.DELETE);
		into.setUserId(userid);
		into.setOpDate(new Date());
		this.sysUserRoleMapper.insertInto(into);
		
		SysUserRoleBatchDTO batchDTO = DataUtils.copyTo(sysUserRoleAddDTO, SysUserRoleBatchDTO.class);
		batchDTO.setOperateUser(userid);
		batchDTO.setOperateTime(new Date());
		this.sysUserRoleMapper.deleteByUserId(sysUserRoleAddDTO.getUserid());
		if(StringUtils.isNotBlank(sysUserRoleAddDTO.getRoleid())){
			batchDTO.setRoleids(Arrays.asList(StringUtils.split(sysUserRoleAddDTO.getRoleid(), ",")));
			this.sysUserRoleMapper.addUserRole(batchDTO);
			into.setReqNo(new IdWorker().nextId()+"");
			into.setStatus(InsertInto.UPDATE);
			this.sysUserRoleMapper.insertInto(into);
		}
		
	}

	/**   
	 * <p>Title: queryUserRole</p>   
	 * <p>Description: </p>   
	 * @param userid
	 * @return   
	 * @see com.spring.demo.springbootexample.sso.sysuserrole.service.ISysUserRoleService#queryUserRole(String)
	 */  
	@Override
	public List<SysUserRoleDTO> queryUserRole(String userid) {
		return this.sysUserRoleMapper.queryUserRole(userid);	
	}

	/**   
	 * <p>Title: queryUserFun</p>   
	 * <p>Description: </p>   
	 * @param userid
	 * @return   
	 * @see com.spring.demo.springbootexample.sso.sysuserrole.service.ISysUserRoleService#queryUserFun(String)
	 */  
	@Override
	public List<SysUserFunDTO> queryUserFun(String userid) {
		return this.sysUserRoleMapper.queryUserFun(userid);
	}

	/**   
	 * <p>Title: queryUserRoleCheck</p>   
	 * <p>Description: </p>   
	 * @param userid
	 * @return   
	 * @see com.spring.demo.springbootexample.sso.sysuserrole.service.ISysUserRoleService#queryUserRoleCheck(String)
	 */  
	@Override
	public List<SysUserRoleCheckDTO> queryUserRoleCheck(String userid) {
		return this.sysUserRoleMapper.queryUserRoleCheck(userid);
	}

	/**   
	 * <p>Title: sysUserRoleService</p>   
	 * <p>Description: </p>   
	 * @param roleid
	 * @param userId   
	 * @see com.spring.demo.springbootexample.sso.sysuserrole.service.ISysUserRoleService#sysUserRoleService(String, String)
	 */  
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public void sysUserRoleService(String roleid, String userId) {
		InsertInto<String> into = new InsertInto<>();
		into.setReqNo(new IdWorker().nextId()+"");
		into.setId(roleid);
		into.setStatus(InsertInto.DELETE);
		into.setUserId(userId);
		into.setOpDate(new Date());
		this.sysUserRoleMapper.insertIntoByRoleId(into);
		this.sysUserRoleMapper.deleteByRoleId(roleid);
	}

	/**   
	 * <p>Title: setUserRoleToCache</p>   
	 * <p>Description: </p>   
	 * @param userId   
	 * @see com.spring.demo.springbootexample.sso.sysuserrole.service.ISysUserRoleService#setUserRoleToCache(String)
	 */  
	@Override
	public void setUserRoleToCache(String userId) {
		 List<SysUserRoleDTO>  userRoles = this.queryUserRole(userId);
		 redisExtCommands.hset(SYSTEM_USER_ROLE_CACHE_KEY, userId, userRoles);
	}

	
	
	
}
