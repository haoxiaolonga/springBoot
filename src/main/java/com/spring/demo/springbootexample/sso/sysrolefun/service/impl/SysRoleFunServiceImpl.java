package com.spring.demo.springbootexample.sso.sysrolefun.service.impl;

import com.spring.demo.springbootexample.base.BaseServiceImpl;
import com.spring.demo.springbootexample.common.DataUtils;
import com.spring.demo.springbootexample.common.IdWorker;
import com.spring.demo.springbootexample.protocol.InsertInto;
import com.spring.demo.springbootexample.protocol.sso.sysfunrole.SysRoleFunAddDTO;
import com.spring.demo.springbootexample.protocol.sso.sysfunrole.SysRoleFunBatchDTO;
import com.spring.demo.springbootexample.protocol.sso.sysfunrole.SysRoleFunDTO;
import com.spring.demo.springbootexample.protocol.sso.sysfunrole.SysRoleFunTreeDTO;
import com.spring.demo.springbootexample.sso.sysrolefun.entity.SysRoleFun;
import com.spring.demo.springbootexample.sso.sysrolefun.mapper.SysRoleFunMapper;
import com.spring.demo.springbootexample.sso.sysrolefun.service.ISysRoleFunService;
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
 * @ClassName:  sysRoleFunService   
 * @Description:系统角色功能权限服务
 * @author: 张小虎
 * @date:   2017年7月19日 上午10:10:04
 */
@Service("sysRoleFunService")
@Transactional(readOnly=true)
public class SysRoleFunServiceImpl extends BaseServiceImpl<SysRoleFun, String> implements ISysRoleFunService{

	@Autowired
	private SysRoleFunMapper sysRoleFunMapper;
	
	@Autowired
	public void setMapper(SysRoleFunMapper sysRoleFunMapper){
		this.setBaseMapper(sysRoleFunMapper);
	}

	/**   
	 * <p>Title: saveRoleFun</p>   
	 * <p>Description: </p>   
	 * @param sysRoleFunAddDTO
	 * @param userId   
	 * @see com.spring.demo.springbootexample.sso.sysrolefun.service.ISysRoleFunService#saveRoleFun(com.spring.demo.springbootexample.protocol.sso.sysfunrole.SysRoleFunAddDTO, String)
	 */  
    @Transactional(propagation=Propagation.REQUIRED)
	@Override
	public void saveRoleFun(SysRoleFunAddDTO sysRoleFunAddDTO, String userId) {

		InsertInto<String> into = new InsertInto<>();
		into.setReqNo(new IdWorker().nextId()+"");
		into.setId(sysRoleFunAddDTO.getRoleid());
		into.setStatus(InsertInto.DELETE);
		into.setUserId(userId);
		into.setOpDate(new Date());
		this.sysRoleFunMapper.insertInto(into);
		
		SysRoleFunBatchDTO batchDTO = DataUtils.copyTo(sysRoleFunAddDTO, SysRoleFunBatchDTO.class);
		batchDTO.setOperateUser(userId);
		batchDTO.setOperateTime(new Date());
		this.sysRoleFunMapper.deleteByRoleId(sysRoleFunAddDTO.getRoleid());
		if(StringUtils.isNotBlank(sysRoleFunAddDTO.getFunid())){
			batchDTO.setFunids(Arrays.asList(StringUtils.split(sysRoleFunAddDTO.getFunid(), ",")));
			this.sysRoleFunMapper.addRoleFun(batchDTO);
			into.setReqNo(new IdWorker().nextId()+"");
			into.setStatus(InsertInto.UPDATE);
			this.sysRoleFunMapper.insertInto(into);
		}
		
		
		
	}

	/**   
	 * <p>Title: queryRoleFunTree</p>   
	 * <p>Description: </p>   
	 * @param roleid
	 * @return   
	 * @see com.spring.demo.springbootexample.sso.sysrolefun.service.ISysRoleFunService#queryRoleFunTree(String)
	 */  
	@Override
	public List<SysRoleFunTreeDTO> queryRoleFunTree(String roleid) {
		return this.sysRoleFunMapper.queryRoleFunTree(roleid);
	}

	/**   
	 * <p>Title: queryRoleFun</p>   
	 * <p>Description: </p>   
	 * @param roleid
	 * @return   
	 * @see com.spring.demo.springbootexample.sso.sysrolefun.service.ISysRoleFunService#queryRoleFun(String)
	 */  
	@Override
	public List<SysRoleFunDTO> queryRoleFun(String roleid) {
		return this.sysRoleFunMapper.queryRoleFun(roleid);
	}

	/**   
	 * <p>Title: deleteRoleFunByRole</p>   
	 * <p>Description: </p>   
	 * @param roleid
	 * @param userId   
	 * @see com.spring.demo.springbootexample.sso.sysrolefun.service.ISysRoleFunService#deleteRoleFunByRole(String, String)
	 */  
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public void deleteRoleFunByRole(String roleid, String userId) {
		InsertInto<String> into = new InsertInto<>();
		into.setReqNo(new IdWorker().nextId()+"");
		into.setId(roleid);
		into.setStatus(InsertInto.DELETE);
		into.setUserId(userId);
		into.setOpDate(new Date());
		this.sysRoleFunMapper.insertInto(into);
		this.sysRoleFunMapper.deleteByRoleId(roleid);
	}

	/**   
	 * <p>Title: deleteRoleFunByFuns</p>   
	 * <p>Description: </p>   
	 * @param ids   
	 * @see com.spring.demo.springbootexample.sso.sysrolefun.service.ISysRoleFunService#deleteRoleFunByFuns(List)
	 */  
	@Override
	public void deleteRoleFunByFuns(List<String> ids,String userId) {
		this.sysRoleFunMapper.deleteByFuns(ids);
		InsertInto<List<String>> into = new InsertInto<>();
		into.setReqNo(new IdWorker().nextId()+"");
		into.setId(ids);
		into.setStatus(InsertInto.UPDATE);
		into.setUserId(userId);
		into.setOpDate(new Date());
		this.sysRoleFunMapper.insertIntoByFuns(into);
	}
	
	
	
}
