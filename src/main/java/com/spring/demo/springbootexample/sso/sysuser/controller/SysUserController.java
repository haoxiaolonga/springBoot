package com.spring.demo.springbootexample.sso.sysuser.controller;

import com.spring.demo.springbootexample.base.BaseController;
import com.spring.demo.springbootexample.common.DataUtils;
import com.spring.demo.springbootexample.common.Global;
import com.spring.demo.springbootexample.common.GlobalHeader;
import com.spring.demo.springbootexample.protocol.PageVO;
import com.spring.demo.springbootexample.protocol.Result;
import com.spring.demo.springbootexample.protocol.sso.UserDTO;
import com.spring.demo.springbootexample.protocol.sso.sysuser.LoginSysUserDTO;
import com.spring.demo.springbootexample.protocol.sso.sysuser.LoginSysUserVO;
import com.spring.demo.springbootexample.protocol.sso.sysuser.SysUserAddDTO;
import com.spring.demo.springbootexample.protocol.sso.sysuser.SysUserDTO;
import com.spring.demo.springbootexample.protocol.sso.sysuser.SysUserUpdateDTO;
import com.spring.demo.springbootexample.protocol.sso.sysuser.SysUserVO;
import com.spring.demo.springbootexample.protocol.sso.sysuser.dto.SysUserPageDTO;
import com.spring.demo.springbootexample.protocol.sso.sysuser.dto.SysUserPageQryDTO;
import com.spring.demo.springbootexample.protocol.sso.sysuser.pwd.ResetPwdDTO;
import com.spring.demo.springbootexample.protocol.sso.sysuser.pwd.UpdateCenterPwdDTO;
import com.spring.demo.springbootexample.protocol.sso.sysuser.pwd.UpdatePwdDTO;
import com.spring.demo.springbootexample.protocol.sso.sysuser.request.SysUserPageQryQVO;
import com.spring.demo.springbootexample.protocol.sso.sysuser.response.SysUserPageQryRVO;
import com.spring.demo.springbootexample.protocol.sso.sysuser.response.SysUserRVO;
import com.spring.demo.springbootexample.sso.sysuser.entity.SysUser;
import com.spring.demo.springbootexample.sso.sysuser.service.ISysUserService;
import com.spring.demo.springbootexample.token.JwtToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Arrays;
import java.util.List;

@RestController("sy/sysuser")
@RequestMapping("sy/sysuser")
@Api(value = "系统用户管理",tags = {"权限"})
public class SysUserController extends BaseController {

	@Autowired
	private ISysUserService sysUserService;

	@Autowired
	private JwtToken token; 
	
	@SuppressWarnings("unchecked")
	@GetMapping("/queryUserByPage")
	@ApiOperation(value = "系统用户分页查询",notes = "分页查询")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "username", value = "用户名", required = false,dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "mobilephone", value = "移动电话", required = false,dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "rows", value = "条数", required = true,dataType = "Integer"),
        @ApiImplicitParam(paramType="query",name = "start", value = "页数", required = true,dataType = "Integer")
        })
	public Result<PageVO<SysUserVO>> queryUserByPage(@RequestParam(required = false) String username,
													 @RequestParam(required = false) String mobilephone, @RequestParam("start") Integer start,
													 @RequestParam("rows") Integer rows){
		SysUserDTO sysUserDTO = new SysUserDTO();
		//组装对象
		packPageSysUserDTO(username, mobilephone, start, rows, sysUserDTO);
		
		return  (Result<PageVO<SysUserVO>>) Result.ok(sysUserService.queryUserByPage(sysUserDTO));
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("/pageForOrder")
	@ApiOperation(value = "系统用户分页查询",notes = "分页查询")
	public Result<PageVO<SysUserPageQryRVO>> pageForOrder(@ModelAttribute SysUserPageQryQVO qryVo){
		SysUserPageQryDTO sysUserDTO  = DataUtils.copyTo(qryVo, SysUserPageQryDTO.class);
		SysUser sysUser = DataUtils.copyTo(qryVo, SysUser.class);
		int count = this.sysUserService.count(sysUser);
		PageVO<SysUserPageQryRVO> pageVo = new PageVO<>();
		pageVo.setTotalSize(count);
		if(count !=0) {
			List<SysUserPageDTO> pageDtos = sysUserService.pageForOrder(sysUserDTO);
			pageVo.setResult(DataUtils.copyTo(pageDtos, SysUserPageQryRVO.class));
		}
		return  (Result<PageVO<SysUserPageQryRVO>>) Result.ok(pageVo);
	}
	
	
	@ApiOperation(value = "新增系统用户",notes = "新增")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "sysUserAddDto", value = "修改的对象", required = true, paramType = "body", dataType = "SysUserAddDTO"),
	})
	@PostMapping("/saveUser")
	public Result saveUser(@RequestBody SysUserAddDTO sysUserAddDto,
			@ApiIgnore @RequestHeader(name = Global.GLOBAL_HEADER,required = false) String header){
		GlobalHeader globalHeader = this.getUserInfo(header, true);
		sysUserAddDto.setOperateUser( globalHeader.getUserId());
		sysUserService.saveUser(sysUserAddDto);
		return Result.ok();
	}
	
	@ApiOperation(value = "修改系统用户",notes = "修改")
	@PutMapping("/updateUser")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "sysUserUpdateDTO", value = "修改的对象", required = true, paramType = "body", dataType = "SysUserUpdateDTO"),
	})
	public Result updateUser(@RequestBody SysUserUpdateDTO sysUserUpdateDTO,
			@ApiIgnore @RequestHeader(name = Global.GLOBAL_HEADER,required = false) String header){
		GlobalHeader globalHeader = this.getUserInfo(header, true);
		sysUserUpdateDTO.setOperateUser(globalHeader.getUserId());
		sysUserService.updateUser(sysUserUpdateDTO);
		return Result.ok();
	}
	
	@ApiOperation(value = "修改密码",notes = "修改")
	@PutMapping("/updatePassword")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "updatePwd", value = "修改的对象", required = true, paramType = "body", dataType = "UpdatePwd"),
	})
	public Result updatePassword(@RequestBody UpdatePwdDTO updatePwd
			,@ApiIgnore @RequestHeader(name = Global.GLOBAL_HEADER,required = false) String header){
		GlobalHeader globalHeader = this.getUserInfo(header, true);
		updatePwd.setUserid(globalHeader.getUserId());
		sysUserService.updatePwd(updatePwd);
		return Result.ok();
	}
	
	@ApiOperation(value = "系统登录密码修改",notes="修改")
	@PutMapping("/updateLoginPwd")
	public Result updateLoginPwd(@RequestBody UpdateCenterPwdDTO updatePwd,
			@ApiIgnore @RequestHeader(name = Global.GLOBAL_HEADER,required = false) String header){
		GlobalHeader globalHeader = this.getUserInfo(header, true);
		updatePwd.setUserid(globalHeader.getUserId());
		sysUserService.updateLoginPwd(updatePwd);
		return Result.ok();
	}
	
	@ApiOperation(value = "重置密码",notes = "重置")
	@PutMapping("/resetPassword")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "resetPwd", value = "修改的对象", required = true, paramType = "body", dataType = "ResetPwdDTO"),
	})
	public Result resetPassword(@RequestBody ResetPwdDTO resetPwd,
			@ApiIgnore @RequestHeader(name = Global.GLOBAL_HEADER,required = false) String header){
		GlobalHeader globalHeader = this.getUserInfo(header, true);
		resetPwd.setOperateUser(globalHeader.getUserId());
		sysUserService.resetPwd(resetPwd);
		return Result.ok();
	}
	
	@ApiOperation(value = "用户锁定/解锁",notes = "锁定/解锁")
	@PutMapping("/lockUser")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "lockUserDTO", value = "锁定的对象", required = true, paramType = "body", dataType = "LockUserDTO"),
	})
	public Result lockUser(@RequestBody UserDTO lockUserDTO,
			@ApiIgnore @RequestHeader(name = Global.GLOBAL_HEADER,required = false) String header){
		GlobalHeader globalHeader = this.getUserInfo(header, true);
		lockUserDTO.setOperateUser(globalHeader.getUserId());
		sysUserService.lockUser(lockUserDTO);
		return Result.ok();
	}
	/**
	 * 
	 * @Title: packPageSysUserDTO   
	 * @Description: 组装user对象   
	 * @return void
	 */
	private void packPageSysUserDTO(String username,String mobilePhone,Integer start,Integer rows,SysUserDTO sysUserDTO){
		sysUserDTO.setMobilephone(mobilePhone);
		sysUserDTO.setStart(start);//从零开始
		sysUserDTO.setUsername(username);
		sysUserDTO.setRows(rows);
	}
	
	@ApiOperation(value = "系统用户使用密码登录",notes = "登录")
	@GetMapping("/loginUsePwd")
	public Result loginUsePwd(@RequestParam String mobilephone,@RequestParam String password){
		LoginSysUserDTO userDto = this.sysUserService.loginUsePwd(mobilephone,password);
		LoginSysUserVO userVo = DataUtils.copyTo(userDto, LoginSysUserVO.class);
//		String tokenStr = token.create(userDto.getUserid(), "sysuser");
//		userVo.setToken(tokenStr);
		return Result.ok(userVo);
	}
	

	@SuppressWarnings("unchecked")
	@GetMapping("/findByUserIds")
	@ApiOperation(value = "查询用户信息",notes = "查询用户信息")
	public Result<List<SysUserRVO>> findByUserIds(
			@RequestParam String userIds,
			@ApiIgnore @RequestHeader(name = Global.GLOBAL_HEADER,required = false) String header){
		GlobalHeader globalHeader = this.getUserInfo(header, true);
		List<SysUser> users = sysUserService.findByUserIds(Arrays.asList(userIds.split(",")));
		return  (Result<List<SysUserRVO>>) Result.ok(DataUtils.copyTo(users, SysUserRVO.class));
	}
}
