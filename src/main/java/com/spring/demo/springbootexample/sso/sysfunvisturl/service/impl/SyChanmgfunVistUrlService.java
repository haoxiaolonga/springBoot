package com.petecat.interchan.sso.sysfunvisturl.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.mhuang.common.util.DataUtils;
import com.petecat.interchan.auth.common.constant.AuthConstant;
import com.petecat.interchan.core.service.impl.BaseServiceImpl;
import com.petecat.interchan.protocol.InsertInto;
import com.petecat.interchan.protocol.sso.sysfunvisturl.SyChanmgfunExcludeUrlDTO;
import com.petecat.interchan.protocol.sso.sysfunvisturl.SyChanmgfunVistUrlmAddDTO;
import com.petecat.interchan.protocol.sso.sysfunvisturl.SyChanmgfunVistUrlmQryDTO;
import com.petecat.interchan.redis.IdWorker;
import com.petecat.interchan.redis.commands.IRedisExtCommands;
import com.petecat.interchan.sso.sysfunvisturl.entity.SyChanmgfunExcludeUrl;
import com.petecat.interchan.sso.sysfunvisturl.entity.SyChanmgfunVistUrlm;
import com.petecat.interchan.sso.sysfunvisturl.mapper.SyChanmgfunVistUrlmMapper;
import com.petecat.interchan.sso.sysfunvisturl.service.ISyChanmgfunVistUrlService;
import com.petecat.interchan.sso.sysrole.entity.SysRole;
import com.petecat.interchan.sso.sysuser.entity.SysUser;

/**
 * 
 * @ClassName:  SyChanmgfunVistUrlService   
 * @Description:权限可访问路径服务
 * @author: 张小虎
 * @date:   2017年7月19日 上午10:10:04
 */
@Service("syChanmgfunVistUrlService")
@Transactional(readOnly=true)
public class SyChanmgfunVistUrlService 
extends BaseServiceImpl<SyChanmgfunVistUrlm, String> implements ISyChanmgfunVistUrlService,InitializingBean{

	@Autowired
    private	IRedisExtCommands redisExtCommands;
	
	@Autowired
	private SyChanmgfunVistUrlmMapper syChanmgfunVistUrlmMapper;
	
	@Autowired
	private void setMapper(SyChanmgfunVistUrlmMapper syChanmgfunVistUrlmMapper){
		super.setBaseMapper(syChanmgfunVistUrlmMapper);
	}
	
	/**   
	 * @Title: insertHByAuth   
	 * @Description: 插入历史通过权限
	 * @param funid
	 * @return void     
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void insertHByAuth(String funid,String userId,String status,String seqno) {
		InsertInto<String> into = new InsertInto<>();
		into.setId(funid);
		into.setOpDate(new Date());
		into.setUserId(userId);
		into.setReqNo(new IdWorker().nextId()+"");
		into.setStatus(status);
	    this.syChanmgfunVistUrlmMapper.insertIntoByAuth(into);
	}

	/**   
	 * @Title: deleteByAuth   
	 * @Description:删除通过权限
	 * @param funid
	 * @return void     
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteByAuth(String funid) {
		 this.syChanmgfunVistUrlmMapper.deleteByAuth(funid);
	}

	/**   
	 * <p>Title: insertPowers</p>   
	 * <p>Description: </p>   
	 * @param dtos   
	 * @see com.petecat.interchan.sso.sysfun.service.ISyChanmgfunVistUrlService#insertPowers(java.util.List)   
	 */  
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public void insertPowersUrl(List<SyChanmgfunVistUrlmAddDTO> dtos,String userId,String seqno) {
		dtos.forEach((value)->{
			SyChanmgfunVistUrlm url = new SyChanmgfunVistUrlm();
		    BeanUtils.copyProperties(value, url);
		    url.setOperateTime(new Date());
		    url.setOperateUser(userId);
		    url.setId(new IdWorker().nextId()+"");
		    this.syChanmgfunVistUrlmMapper.save(url);
		});
        if(!CollectionUtils.isEmpty(dtos)) {
		    InsertInto<String> into = new InsertInto<>();
			into.setReqNo(seqno);
			into.setId(dtos.get(0).getFunid());
			into.setStatus(InsertInto.ADD);
			into.setUserId(userId);
			into.setOpDate(new Date());
			this.syChanmgfunVistUrlmMapper.insertIntoByAuth(into);
        }
	}

	/**   
	 * <p>Title: queryFunVist</p>   
	 * <p>Description: </p>   
	 * @param funid
	 * @return   
	 * @see com.petecat.interchan.sso.sysfun.service.ISyChanmgfunVistUrlService#queryFunVist(java.lang.String)   
	 */  
	@Override
	public List<SyChanmgfunVistUrlmQryDTO> queryFunVist(String funid) {
		List<SyChanmgfunVistUrlm> urlm = this.syChanmgfunVistUrlmMapper.queryFunVist(funid);
		return DataUtils.copyTo(urlm, SyChanmgfunVistUrlmQryDTO.class);
	}

	/**   
	 * @Title: setExcludeUrl   
	 * @Description: 设置排除地址
	 * @return void     
	 */
	@Override
	public List<SyChanmgfunExcludeUrlDTO> setExcludeUrl() {
		List<SyChanmgfunExcludeUrlDTO> vos = null;
		//查询数据库，并放入缓存中
		 List<SyChanmgfunExcludeUrl> urls = this.syChanmgfunVistUrlmMapper.getExcludeUrl();
		 if(!CollectionUtils.isEmpty(urls)) {
			 Map datas = 
					 urls.parallelStream().collect(Collectors.groupingBy(SyChanmgfunExcludeUrl::getType));
			 this.redisExtCommands.hmset(AuthConstant.AUTH_DICT_KEY, datas);
		 }
		 return vos;
	}

	/**   
	 * <p>Title: afterPropertiesSet</p>   
	 * <p>Description: </p>   
	 * @throws Exception   
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()   
	 */  
	@Override
	public void afterPropertiesSet() throws Exception {
		setExcludeUrl();
	}

	/**   
	 * <p>Title: setVistUrlPower</p>   
	 * <p>Description: </p>   
	 * @param userid   
	 * @see com.petecat.interchan.sso.sysfun.service.ISyChanmgfunVistUrlService#setVistUrlPower(java.lang.String)   
	 */  
	@Override
	public void setVistUrlPower(String userid) {
		//检查路径与权限问题
		String cacheKey = AuthConstant.USER_VIST_URL_CACHEKEY;
		List<SyChanmgfunVistUrlm> vistUrls = this.redisExtCommands.hgetList(cacheKey, userid, SyChanmgfunVistUrlm.class);
		if(CollectionUtils.isEmpty(vistUrls)) {
			//不存在的时候设置权限
			setVistUrlPowerNow(userid);
		}
	   
	}
	
	@Override
	public void setVistUrlPowerNow(String userId){
		String cacheKey = AuthConstant.USER_VIST_URL_CACHEKEY;
		List<SyChanmgfunVistUrlm> urlms = this.syChanmgfunVistUrlmMapper.getUserUrlPower(userId);
		Map params = urlms.parallelStream().collect(Collectors.toMap((k)->{
			return userId.concat("-").concat(k.getUrl());
		},v->v,(oldValue,newValue)->oldValue));
		this.redisExtCommands.hmset(cacheKey, params);
	
	}
	/**   
	 * @Title: setUserVistPowerByRoleAsync   
	 * @Description:设置人员角色权限信息
	 * @param roleid
	 * @return void     
	 */
	@Async
	public void setUserVistPowerByRoleAsync(String roleid) {
		//查询角色对应的用户信息
		List<String> roleIds = new ArrayList<>();
		roleIds.add(roleid);
		List<SysUser> users = this.syChanmgfunVistUrlmMapper.queryUserByRole(roleIds);
		users.forEach(user->{
			setVistUrlPowerNow(user.getUserid());
		});
	}

	/**   
	 * <p>Title: setUserVistPowerByRolesAsync</p>   
	 * <p>Description: </p>   
	 * @param roles   
	 * @see com.petecat.interchan.sso.sysfun.service.ISyChanmgfunVistUrlService#setUserVistPowerByRolesAsync(java.util.List)   
	 */  
	@Async
	@Override
	public void setUserVistPowerByRolesAsync(List<SysRole> roles) {
		if(!CollectionUtils.isEmpty(roles)){
			//查询角色对应的用户信息
			List<String> roleIds = 
					roles.parallelStream().map(value->value.getRoleid()).collect(Collectors.toList());
			List<SysUser> users = this.syChanmgfunVistUrlmMapper.queryUserByRole(roleIds);
			users.forEach(user->{
				setVistUrlPowerNow(user.getUserid());
			});
		}
	}

	/**   
	 * <p>Title: deleteByFunsIds</p>   
	 * <p>Description: </p>   
	 * @param ids
	 * @param userId
	 * @param delete
	 * @param reqNo   
	 * @see com.petecat.interchan.sso.sysfunvisturl.service.ISyChanmgfunVistUrlService#deleteByFunsIds(java.util.List, java.lang.String, java.lang.String, java.lang.String)   
	 */ 
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public void deleteByFunsIds(List<String> ids, String userId, String status, String reqNo) {
		InsertInto<List<String>> into = new InsertInto<>();
		into.setId(ids);
		into.setOpDate(new Date());
		into.setUserId(userId);
		into.setReqNo(reqNo);
		into.setStatus(status);
	    this.syChanmgfunVistUrlmMapper.insertIntoByAuths(into);
	    this.syChanmgfunVistUrlmMapper.deleteByAuths(ids);
	}

	/**   
	 * <p>Title: reloadVistUrl</p>   
	 * <p>Description: </p>   
	 * @param userId   
	 * @see com.petecat.interchan.sso.sysfunvisturl.service.ISyChanmgfunVistUrlService#reloadVistUrl(java.lang.String)   
	 */  
	@Override
	public void reloadVistUrl(String userId) {
		if(StringUtils.isNotBlank(userId)) {
		   this.setVistUrlPowerNow(userId);	
		}else {
			this.setExcludeUrl();
		}
		
	}
	
}
