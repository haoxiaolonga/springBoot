/**  
 * All rights Reserved, Designed By http://www.pete-cat.com/
 * @Title:  SysRoleFunController.java   
 * @Package com.petecat.interchan.sso.sysuserroleFun   
 * @Description:系统角色功能
 * @author: 成都皮特猫科技     
 * @date:2017年7月20日 下午6:48:48   
 * @version V1.0 
 * @Copyright: 2017 www.pete-cat.com Inc. All rights reserved. 
 * 注意：本内容仅限于成都皮特猫信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */  
package com.petecat.interchan.sso.sysrolefun.controller;

import com.mhuang.common.util.DataUtils;
import com.petecat.interchan.core.constans.Global;
import com.petecat.interchan.core.controller.BaseController;
import com.petecat.interchan.protocol.GlobalHeader;
import com.petecat.interchan.protocol.Result;
import com.petecat.interchan.protocol.sso.sysfunrole.*;
import com.petecat.interchan.sso.sysrolefun.service.ISysRoleFunService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**   
 * @ClassName:  SysRoleFunController   
 * @Description:系统角色功能  
 * @author: admin
 * @date:   2017年7月20日 下午6:48:48   
 */
@RestController
@Api(value="系统角色功能管理",tags="角色功能")
@RequestMapping("/sy/sysrolefun")
public class SysRoleFunController extends BaseController {

	@Autowired
	private ISysRoleFunService sysRoleFunService;
	
	@ApiOperation(value = "保存角色功能信息",notes = "保存")
	@PostMapping(value = "/setRoleFun")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "sysRoleFunAddDTO", value = "角色功能对象,多个funid用逗号隔开", required = true, paramType = "body", dataType = "SysRoleFunAddDTO"),
	})
	public Result setRoleFun(@RequestBody SysRoleFunAddDTO sysRoleFunAddDTO,
			@ApiIgnore @RequestHeader(name = Global.GLOBAL_HEADER,required = false) String header){
		GlobalHeader globalHeader = this.getUserInfo(header, true);
		sysRoleFunService.saveRoleFun(sysRoleFunAddDTO,globalHeader.getUserId());
		return Result.ok();
	}
	
	
	@ApiOperation(value = "树形结构查询角色权限信息",notes = "查询")
	@GetMapping(value = "/queryRoleFunTree")
	public Result<List<SysRoleFunTreeVO>> queryRoleFunTree(
			@RequestParam(required=false) String roleid,
			@ApiIgnore @RequestHeader(name = Global.GLOBAL_HEADER,required = false) String header){
		this.getUserInfo(header, true);
		List<SysRoleFunTreeDTO> roleFunVos = this.sysRoleFunService.queryRoleFunTree(roleid);
		List<SysRoleFunTreeVO> treeVos = this.structFunTree(roleFunVos);
		return (Result<List<SysRoleFunTreeVO>>) Result.ok(treeVos);
	}
	
	
	/**   
	 * @Title: structFunTree   
	 * @Description: 组装功能树  
	 * @param roleFunVos
	 * @return List<SysRoleFunTreeVo>     
	 */
	private List<SysRoleFunTreeVO> structFunTree(List<SysRoleFunTreeDTO> roleFunVos) {
		List<SysRoleFunTreeVO> vos = new CopyOnWriteArrayList<>();
		if(!CollectionUtils.isEmpty(roleFunVos)){
			roleFunVos.parallelStream().forEach((data)->{
				if("TOP".equals(data.getParentid())){
					SysRoleFunTreeVO vo = DataUtils.copyTo(data, SysRoleFunTreeVO.class);
					vos.add(vo);
					List<SysRoleFunTreeVO> children = this.getRoleFunChildren(data,roleFunVos);
					if(!CollectionUtils.isEmpty(children)){
						children.sort((data1,data2)->{
							return Integer.compare(data1.getOrderval(),data2.getOrderval());
						});
					}
					vo.setChildren(children);
				}
			});
		}
		if(!CollectionUtils.isEmpty(vos)){
			vos.sort((data1,data2)->{
				try{
					return Integer.compare(data1.getOrderval(),data2.getOrderval());
				}catch(Exception e){
					return 0;
				}
			});
		}
		return vos;
	}


	/**   
	 * @Title: getRoleFunChildren   
	 * @Description: 获取当前角色权限的子
	 * @param current
	 * @param roleFunVos
	 * @return List<SysRoleFunTreeVo>     
	 */
	private List<SysRoleFunTreeVO> getRoleFunChildren(SysRoleFunTreeDTO current, List<SysRoleFunTreeDTO> roleFunVos) {
		List<SysRoleFunTreeVO> vos = new CopyOnWriteArrayList<>();
		roleFunVos.parallelStream().forEach((data)->{
			if(current.getFunid().equals(data.getParentid())){
				SysRoleFunTreeVO vo = DataUtils.copyTo(data, SysRoleFunTreeVO.class);
				vos.add(vo);
				vo.setChildren(this.getRoleFunChildren(data,roleFunVos));
			}
		});
		return vos;
	}


	@ApiOperation(value = "查询角色功能信息",notes = "查询")
	@GetMapping(value = "/queryRoleFun")
	public Result<List<SysRoleFunVO>> queryRoleFun(
			@RequestParam String roleid,
			@ApiIgnore @RequestHeader(name = Global.GLOBAL_HEADER,required = false) String header){
		this.getUserInfo(header, true);
		List<SysRoleFunDTO> dtos = this.sysRoleFunService.queryRoleFun(roleid);
		List<SysRoleFunVO> roleFunVos = DataUtils.copyTo(dtos, SysRoleFunVO.class);
		return (Result<List<SysRoleFunVO>>) Result.ok(roleFunVos);
	}


	@ApiOperation(value = "查询角色有效的功能信息",notes = "查询")
	@GetMapping(value = "/queryRoleValidFun")
	public Result<List<SysRoleFunVO>> queryRoleValidFun(
			@RequestParam String roleid,
			@ApiIgnore @RequestHeader(name = Global.GLOBAL_HEADER,required = false) String header){
		this.getUserInfo(header, true);
		List<SysRoleFunDTO> dtos = this.sysRoleFunService.queryRoleFun(roleid);
		List<SysRoleFunVO> roleFunVos = DataUtils.copyTo(dtos, SysRoleFunVO.class).parallelStream().
				filter(t->"1".equals(t.getUseflag())).collect(Collectors.toList());
		return (Result<List<SysRoleFunVO>>) Result.ok(roleFunVos);
	}
	
}
