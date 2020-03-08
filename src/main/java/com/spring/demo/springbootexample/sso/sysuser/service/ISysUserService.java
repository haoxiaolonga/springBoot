package com.spring.demo.springbootexample.sso.sysuser.service;

import com.spring.demo.springbootexample.base.BaseService;
import com.spring.demo.springbootexample.protocol.PageVO;
import com.spring.demo.springbootexample.protocol.sso.UserDTO;
import com.spring.demo.springbootexample.protocol.sso.sysuser.LoginSysUserDTO;
import com.spring.demo.springbootexample.protocol.sso.sysuser.SysUserAddDTO;
import com.spring.demo.springbootexample.protocol.sso.sysuser.SysUserDTO;
import com.spring.demo.springbootexample.protocol.sso.sysuser.SysUserUpdateDTO;
import com.spring.demo.springbootexample.protocol.sso.sysuser.SysUserVO;
import com.spring.demo.springbootexample.protocol.sso.sysuser.dto.SysUserPageDTO;
import com.spring.demo.springbootexample.protocol.sso.sysuser.dto.SysUserPageQryDTO;
import com.spring.demo.springbootexample.protocol.sso.sysuser.pwd.ResetPwdDTO;
import com.spring.demo.springbootexample.protocol.sso.sysuser.pwd.UpdateCenterPwdDTO;
import com.spring.demo.springbootexample.protocol.sso.sysuser.pwd.UpdatePwdDTO;
import com.spring.demo.springbootexample.sso.sysuser.entity.SysUser;

import java.util.List;

/**
 * 
 * @ClassName:  ISysUserService   
 * @Description:系统用户接口   
 * @author: mhuang
 * @date:   2017年7月18日 下午7:13:17
 */
public interface ISysUserService extends BaseService<SysUser, String> {

	/**
	 * 
	 * @Title: queryUserByPage   
	 * @Description: 分页查询用户  
	 * @param sysUserDTO
	 * 			分页条件DTO
	 * @return
	 * @return PageVO<SysUserVO>
	 */
	public PageVO<SysUserVO> queryUserByPage(SysUserDTO sysUserDTO);

	/**
	 * 
	 * @Title: saveUser   
	 * @Description: 新增系统用户   
	 * @return
	 * @return int
	 * @throws Exception 
	 */
	public void saveUser(SysUserAddDTO sysUserAddDTO);
	
	/**
	 * 
	 * @Title: updatePwd   
	 * @Description:修改系统用户 密码 
	 * @param updatePwdDTO
	 * @return void
	 */
	public void updatePwd(UpdatePwdDTO updatePwdDTO);
	
	/**
	 * 
	 * @Title: updatePwd   
	 * @Description:修改系统用户 密码 
	 * @param updatePwdDTO
	 * @return void
	 */
	public void updateLoginPwd(UpdateCenterPwdDTO updatePwdDTO);
	
	/**
	 * 
	 * @Title: resetPwd   
	 * @Description:重置系统用户 密码 
	 * @return void
	 */
	public void resetPwd(ResetPwdDTO resetPwd);
	/**
	 * 
	 * @Title: lockUser   
	 * @Description: 锁定用户
	 * @param lockUserDTO
	 * @return void
	 */
	public void lockUser(UserDTO lockUserDTO);
	/**
	 * 
	 * @Title: updateUser   
	 * @Description:修改系统用户 
	 * @param sysUserUpdateDTO
	 * @return void
	 */
	public void updateUser(SysUserUpdateDTO sysUserUpdateDTO);

	/**   
	 * @Title: loginUsePwd   
	 * @Description:使用密码登录
	 * @param mobilephone 手机号
	 * @param password 密码
	 * @return LoginSysUserDTO	     
	 */
	public LoginSysUserDTO loginUsePwd(String mobilephone, String password);

	/** 
	 * 排序分页查询    
	 * @Title: pageForOrder
	 * @param sysUserDTO
	 * @return List<SysUserPageDTO>     
	 */
	public List<SysUserPageDTO> pageForOrder(SysUserPageQryDTO sysUserDTO);

	/** 
	 * 通过用户ID查询用户    
	 * @Title: findByUserIds
	 * @param userIds
	 * @return List<SysUser>     
	 */
	public List<SysUser> findByUserIds(List<String> userIds);
}
