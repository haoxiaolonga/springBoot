package com.spring.demo.springbootexample.sso.sysrole.service.impl;

import com.spring.demo.springbootexample.base.BaseServiceImpl;
import com.spring.demo.springbootexample.common.BusinessException;
import com.spring.demo.springbootexample.common.DataUtils;
import com.spring.demo.springbootexample.common.IdWorker;
import com.spring.demo.springbootexample.protocol.InsertInto;
import com.spring.demo.springbootexample.protocol.Page;
import com.spring.demo.springbootexample.protocol.PageVO;
import com.spring.demo.springbootexample.protocol.Result;
import com.spring.demo.springbootexample.protocol.sso.sysrole.SysRoleAddDTO;
import com.spring.demo.springbootexample.protocol.sso.sysrole.SysRoleModDTO;
import com.spring.demo.springbootexample.protocol.sso.sysrole.SysRolePageQueryDTO;
import com.spring.demo.springbootexample.protocol.sso.sysrole.SysRoleQueryDTO;
import com.spring.demo.springbootexample.protocol.sso.sysrole.SysRoleVO;
import com.spring.demo.springbootexample.protocol.sso.sysrole.request.SysRolePageQueryQVO;
import com.spring.demo.springbootexample.sso.sysrole.domain.SysRolePageQueryDO;
import com.spring.demo.springbootexample.sso.sysrole.entity.SysRole;
import com.spring.demo.springbootexample.sso.sysrole.mapper.SysRoleMapper;
import com.spring.demo.springbootexample.sso.sysrole.service.ISysRoleService;
import com.spring.demo.springbootexample.sso.sysrolefun.service.ISysRoleFunService;
import com.spring.demo.springbootexample.sso.sysuserrole.service.ISysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 
 * @ClassName:  SysRoleServiceImpl   
 * @Description:系统角色服务
 * @author: 张小虎
 * @date:   2017年7月19日 上午10:10:04
 */
@Service("sysRoleService")
@Transactional(readOnly=true)
public class SysRoleServiceImpl extends BaseServiceImpl<SysRole, String> implements ISysRoleService{

	@Autowired
	private SysRoleMapper sysRoleMapper;
	
	@Autowired
	private ISysRoleFunService sysRoleFunService;
	
	@Autowired
	private ISysUserRoleService sysUserRoleService;
	
	
	@Autowired
	private Environment environment;
	
	public void setMapper(SysRoleMapper sysRoleMapper){
		this.setBaseMapper(sysRoleMapper);
	}
	
	/**   
	 * <p>Title: saveRole</p>   
	 * <p>Description: </p>   
	 * @param sysRoleAddDTO
	 * @param userId   
	 * @see com.spring.demo.springbootexample.sso.sysrole.service.ISysRoleService#saveRole(com.spring.demo.springbootexample.protocol.sso.sysrole.SysRoleAddDTO, String)
	 */ 
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public void saveRole(SysRoleAddDTO sysRoleAddDTO, String userId) {
		//检查代号是否存在
		SysRole role = this.sysRoleMapper.getById(sysRoleAddDTO.getRoleid());
		if(role != null){//存在角色代码
			throw new BusinessException(Result.SYS_RESULT_FAILD, this.environment.getProperty("sysroleid_exists"));
		}
		role = DataUtils.copyTo(sysRoleAddDTO, SysRole.class);
		role.setMarkflag("0");
		role.setOperateTime(new Date());
		role.setOperateUser(userId);
		this.sysRoleMapper.save(role);
		InsertInto<String> into = new InsertInto<>();
		into.setReqNo(new IdWorker().nextId()+"");
		into.setId(role.getRoleid());
		into.setStatus(InsertInto.ADD);
		into.setUserId(userId);
		this.sysRoleMapper.insertInto(into);
	}


	/**   
	 * <p>Title: saveRole</p>   
	 * <p>Description: </p>   
	 * @param userId
	 * @see com.spring.demo.springbootexample.sso.sysrole.service.ISysRoleService#saveRole(com.spring.demo.springbootexample.protocol.sso.sysrole.SysRoleAddDTO, String)
	 */ 
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public void updateRole(SysRoleModDTO sysRoleModDTO, String userId) {
		//检查代号是否存在
		SysRole role = this.sysRoleMapper.getById(sysRoleModDTO.getRoleid());
		if(role == null){//存在不存在角色代码
			throw new BusinessException(Result.SYS_RESULT_FAILD, this.environment.getProperty("sysrole_not_exists"));
		}
		role = DataUtils.copyTo(sysRoleModDTO, SysRole.class);
		role.setMarkflag(sysRoleModDTO.getMarkflag());
		role.setOperateTime(new Date());
		role.setOperateUser(userId);
		this.sysRoleMapper.update(role);
		InsertInto<String> into = new InsertInto<>();
		into.setReqNo(new IdWorker().nextId()+"");
		into.setId(role.getRoleid());
		into.setStatus(InsertInto.UPDATE);
		into.setUserId(userId);
		this.sysRoleMapper.insertInto(into);
	}

	/**   
	 * <p>Title: queryRoleByPage</p>   
	 * <p>Description: </p>   
	 * @param dto
	 * @return   
	 * @see com.spring.demo.springbootexample.sso.sysrole.service.ISysRoleService#queryRoleByPage(com.spring.demo.springbootexample.protocol.sso.sysrole.SysRolePageQueryDTO)
	 */  
	@Override
	public PageVO<SysRoleVO> queryRoleByPage(SysRolePageQueryDTO dto) {
		Page<SysRole> page = new Page<>();
		SysRole rolem = DataUtils.copyTo(dto, SysRole.class);
		page.setRecord(rolem);
		page.setRows(dto.getRows());
		page.setStart((dto.getStart() - 1) * dto.getRows());
		PageVO<SysRoleVO> pageVo = new PageVO<>();
		List<SysRole> sysRoles = sysRoleMapper.page(page);
		pageVo.setResult(DataUtils.copyTo(sysRoles, SysRoleVO.class));
		pageVo.setTotalSize(this.sysRoleMapper.pageCount(page));
		return pageVo;
	}
	
	

	/**   
	 * <p>Title: queryRole</p>   
	 * <p>Description: </p>   
	 * @param roleid
	 * @return  SysRoleQueryDTO
	 */
	@Override
	public SysRoleQueryDTO queryRole(String roleid,boolean nullException) {
		SysRole role  = sysRoleMapper.getById(roleid);
		SysRoleQueryDTO dto = null;
		if(role == null){
			if(nullException){
				throw new BusinessException(Result.SYS_RESULT_FAILD, this.environment.getProperty("sysrole_not_exists"));
			}
		}else{
			dto = DataUtils.copyTo(role, SysRoleQueryDTO.class);
		}
		return dto;
	}
	

	/**   
	 * <p>Title: deleteRole</p>   
	 * <p>Description: </p>   
	 * @param roleid   
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public void deleteRole(String roleid,String userId) {
		InsertInto<String> into = new InsertInto<>();
		into.setReqNo(new IdWorker().nextId()+"");
		into.setId(roleid);
		into.setStatus(InsertInto.DELETE);
		into.setUserId(userId);
		into.setOpDate(new Date());
		this.sysRoleMapper.insertInto(into);
		this.sysRoleMapper.remove(roleid);
		this.sysRoleFunService.deleteRoleFunByRole(roleid,userId);
		this.sysUserRoleService.sysUserRoleService(roleid,userId);
		
	}

	/**   
	 * <p>Title: pageOrderRole</p>   
	 * <p>Description: </p>   
	 * @param dto
	 * @return   
	 * @see com.spring.demo.springbootexample.sso.sysrole.service.ISysRoleService#pageOrderRole(com.spring.demo.springbootexample.protocol.sso.sysrole.request.SysRolePageQueryQVO)
	 */  
	@Override
	public List<SysRole> pageOrderRole(SysRolePageQueryQVO dto) {
		Page<SysRolePageQueryDO> page = DataUtils.copyTo(dto, Page.class);
		page.setRecord(DataUtils.copyTo(dto, SysRolePageQueryDO.class));
		page.setStart(page.getStart()-1);
		List<SysRole> sysRoles = sysRoleMapper.pageOrderRole(page);
		return sysRoles;
	}

	/**   
	 * <p>Title: findByRoleIds</p>   
	 * <p>Description: </p>   
	 * @param roleIds
	 * @return   
	 * @see com.spring.demo.springbootexample.sso.sysrole.service.ISysRoleService#findByRoleIds(List)
	 */  
	@Override
	public List<SysRole> findByRoleIds(List<String> roleIds) {
		List<SysRole> sysRoles = sysRoleMapper.findByRoleIds(roleIds);
		return sysRoles;
	}

}
