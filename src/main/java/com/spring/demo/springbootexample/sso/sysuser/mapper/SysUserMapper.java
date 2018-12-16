package com.spring.demo.springbootexample.sso.sysuser.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.petecat.interchan.core.mapper.BaseMapper;
import com.petecat.interchan.protocol.data.Page;
import com.petecat.interchan.sso.sysuser.domain.SysUserDO;
import com.petecat.interchan.sso.sysuser.entity.SysUser;

/**
 * 
 * @ClassName:  SysUserMapper   
 * @Description:系统用户
 * @author: mhuang
 * @date:   2017年7月13日 下午3:42:41
 */
@Mapper
@Repository("sysUserMapper")
public interface SysUserMapper extends BaseMapper<SysUser, String>{

	List<SysUser> page(Page<SysUser> page);

	/**
	 * 
	 * @Title: getByUserName   
	 * @Description: 根据用户名查询用户对象 
	 * @param username
	 * @return
	 * @return SysUser
	 */
	SysUser getByUserName(String username);

	/**   
	 * @Title: getSysUserByMobilephone   
	 * @Description:通过手机号获取系统用户   
	 * @param mobilephone
	 * @return SysUser     
	 */
	SysUser getSysUserByMobilephone(String mobilephone);
	
	/**
	 * 
	 * @Title: lock   
	 * @Description: 锁定/解锁用户  
	 * @param sysUser
	 * @return
	 * @return int
	 */
	int lock(SysUser sysUser);

	/** 
	 * 排序分页查询 
	 * @Title: pageForOrder
	 * @param pageUser
	 * @return List<SysUser>     
	 */
	List<SysUser> pageForOrder(Page<SysUserDO> pageUser);

	/** 
	 * 用户ID列表   
	 * @Title: findByUserIds
	 * @param userIds
	 * @return List<SysUser>     
	 */
	List<SysUser> findByUserIds(List<String> userIds);
}
