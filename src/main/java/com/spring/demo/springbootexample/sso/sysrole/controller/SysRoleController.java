/**  
 * All rights Reserved, Designed By http://www.pete-cat.com/
 * @Title:  SysRoleController.java   
 * @Package com.petecat.interchan.sso.sysuserrole   
 * @Description:系统角色
 * @author: 成都皮特猫科技     
 * @date:2017年7月20日 下午6:48:48   
 * @version V1.0 
 * @Copyright: 2017 www.pete-cat.com Inc. All rights reserved. 
 * 注意：本内容仅限于成都皮特猫信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */  
package com.spring.demo.springbootexample.sso.sysrole.controller;

import java.util.Arrays;
import java.util.List;

import com.petecat.interchan.core.local.GlobalHeaderThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mhuang.common.util.DataUtils;
import com.petecat.interchan.core.constans.Global;
import com.petecat.interchan.core.controller.BaseController;
import com.petecat.interchan.protocol.GlobalHeader;
import com.petecat.interchan.protocol.Result;
import com.petecat.interchan.protocol.data.Page;
import com.petecat.interchan.protocol.data.PageVO;
import com.petecat.interchan.protocol.sso.sysrole.SysRoleAddDTO;
import com.petecat.interchan.protocol.sso.sysrole.SysRoleModDTO;
import com.petecat.interchan.protocol.sso.sysrole.SysRolePageQueryDTO;
import com.petecat.interchan.protocol.sso.sysrole.SysRoleQueryDTO;
import com.petecat.interchan.protocol.sso.sysrole.SysRoleVO;
import com.petecat.interchan.protocol.sso.sysrole.request.SysRolePageQueryQVO;
import com.petecat.interchan.protocol.sso.sysrole.response.SysRoleRVO;
import com.petecat.interchan.sso.sysrole.entity.SysRole;
import com.petecat.interchan.sso.sysrole.service.ISysRoleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**   
 * @ClassName:  SysRoleController   
 * @Description:系统角色  
 * @author: admin
 * @date:   2017年7月20日 下午6:48:48   
 */
@RestController
@Api(value="系统角色管理",tags="角色")
@RequestMapping("/sy/sysrole")
public class SysRoleController extends BaseController {

	@Autowired
	private ISysRoleService sysRoleService;
	
	@ApiOperation(value = "新增角色信息",notes = "新增")
	@PostMapping(value = "/saveRole")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "sysRoleAddDTO", value = "角色对象", required = true, paramType = "body", dataType = "SysRoleAddDTO"),
	})
	public Result saveRole(@RequestBody SysRoleAddDTO sysRoleAddDTO,
			@ApiIgnore @RequestHeader(name = Global.GLOBAL_HEADER,required = false) String header){
		GlobalHeader globalHeader = this.getUserInfo(header, true);
		sysRoleService.saveRole(sysRoleAddDTO,globalHeader.getUserId());
		return Result.ok();
	}
	@ApiOperation(value = "修改角色信息",notes = "修改")
	@PutMapping(value = "/updateRole")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "sysRoleModDTO", value = "角色对象", required = true, paramType = "body", dataType = "SysRoleModDTO"),
	})
	public Result updateRole(@RequestBody SysRoleModDTO sysRoleModDTO,
			@ApiIgnore @RequestHeader(name = Global.GLOBAL_HEADER,required = false) String header){
		GlobalHeader globalHeader = this.getUserInfo(header, true);
		sysRoleService.updateRole(sysRoleModDTO,globalHeader.getUserId());
		return Result.ok();
	}
	
	
	@ApiOperation(value = "分页查询角色信息",notes = "分页查询")
	@GetMapping(value = "/queryRoleByPage")
	public Result<PageVO<SysRoleVO>> queryRoleByPage(
			@ModelAttribute SysRolePageQueryDTO  dto,
			@ApiIgnore @RequestHeader(name = Global.GLOBAL_HEADER,required = false) String header){
		this.getUserInfo(header, true);
		PageVO<SysRoleVO> pageVo = this.sysRoleService.queryRoleByPage(dto);
		return (Result<PageVO<SysRoleVO>>) Result.ok(pageVo);
	}
	@ApiOperation(value = "删除角色信息",notes = "删除查询")
	@DeleteMapping(value = "/deleteRole")
	public Result remove(
			@RequestParam String roleid,
			@ApiIgnore @RequestHeader(name = Global.GLOBAL_HEADER,required = false) String header){
		GlobalHeader globalHeader = this.getUserInfo(header, true);
		this.sysRoleService.deleteRole(roleid,globalHeader.getUserId());
		return Result.ok();
	}
	@ApiOperation(value = "查询角色信息",notes = "查询")
	@GetMapping(value = "/query")
	public Result<SysRoleVO> queryRole(
			@RequestParam String roleid,
			@ApiIgnore @RequestHeader(name = Global.GLOBAL_HEADER,required = false,defaultValue = "{}") String header){
		this.getUserInfo(header, true);
		SysRoleQueryDTO dto = this.sysRoleService.queryRole(roleid,true);
		SysRoleVO roleVo = DataUtils.copyTo(dto, SysRoleVO.class);
		return (Result<SysRoleVO>) Result.ok(roleVo);
	}
	
	@ApiOperation(value = "分页查询角色信息",notes = "分页查询")
	@GetMapping(value = "/pageOrderRole")
	public Result<PageVO<SysRoleRVO>> pageOrderRole(
			@ModelAttribute SysRolePageQueryQVO  dto,
			@ApiIgnore @RequestHeader(name = Global.GLOBAL_HEADER,required = false) String header){
		this.getUserInfo(header, true);
		PageVO<SysRoleRVO> pageVo = new PageVO();
		Page page = new Page();
		page.setRecord(DataUtils.copyTo(dto, SysRole.class));
		int count = this.sysRoleService.pageCount(page);
		if(count>0) {
			List<SysRole> sysRoles = this.sysRoleService.pageOrderRole(dto);
			pageVo.setResult(DataUtils.copyTo(sysRoles, SysRoleRVO.class));
		}
		return (Result<PageVO<SysRoleRVO>>) Result.ok(pageVo);
	}
	
	@ApiOperation(value = "查询角色信息")
	@GetMapping(value = "/findByRoleIds")
	public Result<List<SysRoleRVO>> findByRoleIds(
			String roleIds,
			@ApiIgnore @RequestHeader(name = Global.GLOBAL_HEADER,required = false) String header){
		this.getUserInfo(header, true);
		List<SysRole> sysRoles = this.sysRoleService.findByRoleIds(Arrays.asList(roleIds.split(",")));
		return (Result<List<SysRoleRVO>>) Result.ok(DataUtils.copyTo(sysRoles, SysRole.class));
	}
	
	
	
}
