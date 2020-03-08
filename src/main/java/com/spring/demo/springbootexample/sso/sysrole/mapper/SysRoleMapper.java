package com.spring.demo.springbootexample.sso.sysrole.mapper;

import com.spring.demo.springbootexample.base.BaseMapper;
import com.spring.demo.springbootexample.protocol.Page;
import com.spring.demo.springbootexample.sso.sysrole.domain.SysRolePageQueryDO;
import com.spring.demo.springbootexample.sso.sysrole.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
 * @ClassName:  SysRoleMapper   
 * @Description:系统角色mapper  
 * @author: 张小虎
 * @date:   2017年7月19日 上午10:11:32
 */
@Mapper
@Repository
public interface SysRoleMapper extends BaseMapper<SysRole, String> {

	/**   
	 * @Title: page   
	 * @Description: 分页查询角色信息 
	 * @param page
	 * @return List<SyChanmgrolem>     
	 */
	List<SysRole> page(Page<SysRole> page);

	/**   
	 * @Title: pageCount   
	 * @Description:分页查询数量
	 * @param page
	 * @return int     
	 */
	int pageCount(Page<SysRole> page);

	/** 
	 * 分页查角色
	 * @Title: pageOrderRole
	 * @param page
	 * @return
	 * @return List<SysRole>     
	 */
	List<SysRole> pageOrderRole(Page<SysRolePageQueryDO> page);

	/** 
	 * 角色ID列表   
	 * @Title: findByRoleIds
	 * @param roleIds
	 * @return List<SysRole>     
	 */
	List<SysRole> findByRoleIds(List<String> roleIds);
	
}