package com.spring.demo.springbootexample.sso.sysfun.controller;

import com.spring.demo.springbootexample.base.BaseController;
import com.spring.demo.springbootexample.common.DataUtils;
import com.spring.demo.springbootexample.common.Global;
import com.spring.demo.springbootexample.common.GlobalHeader;
import com.spring.demo.springbootexample.protocol.PageVO;
import com.spring.demo.springbootexample.protocol.Result;
import com.spring.demo.springbootexample.protocol.sso.sysfun.SysFunAddDTO;
import com.spring.demo.springbootexample.protocol.sso.sysfun.SysFunModDTO;
import com.spring.demo.springbootexample.protocol.sso.sysfun.SysFunPageQueryDTO;
import com.spring.demo.springbootexample.protocol.sso.sysfun.SysFunQueryDTO;
import com.spring.demo.springbootexample.protocol.sso.sysfun.SysFunTreeQueryDTO;
import com.spring.demo.springbootexample.protocol.sso.sysfun.SysFunVO;
import com.spring.demo.springbootexample.sso.sysfun.service.ISysFunService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;


@RestController
@Api(value="系统功能权限管理",tags="功能权限")
@RequestMapping("/sy/sysfun")
public class SysFunController extends BaseController {

	@Autowired
	private ISysFunService sysFunService;
	
	@ApiOperation(value = "新增功能权限信息",notes = "新增")
	@PostMapping(value = "/saveFun")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "sysFunAddDTO", value = "功能权限对象", required = true, paramType = "body", dataType = "SysFunAddDTO"),
	})
	public Result saveFun(@RequestBody SysFunAddDTO sysFunAddDTO,@ApiIgnore @RequestHeader(name = Global.GLOBAL_HEADER,required = false) String header){
		GlobalHeader globalHeader = this.getUserInfo(header, true);
		sysFunService.saveFun(sysFunAddDTO,globalHeader.getUserId());
		return Result.ok();
	}
	@ApiOperation(value = "修改功能权限信息",notes = "修改")
	@PutMapping(value = "/updateFun")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "sysFunModDTO", value = "功能权限对象", required = true, paramType = "body", dataType = "SysFunModDTO"),
	})
	public Result updateFun(@RequestBody SysFunModDTO sysFunModDTO,@ApiIgnore @RequestHeader(name = Global.GLOBAL_HEADER,required = false) String header){
		GlobalHeader globalHeader = this.getUserInfo(header, true);
		sysFunService.updateFun(sysFunModDTO,globalHeader.getUserId());
		return Result.ok();
	}
	
	
	@ApiOperation(value = "分页查询功能权限信息",notes = "分页查询")
	@GetMapping(value = "/queryFunByPage")
	public Result<PageVO<SysFunVO>> queryFunByPage(
			@ModelAttribute SysFunPageQueryDTO pageQry,
			@ApiIgnore @RequestHeader(name = Global.GLOBAL_HEADER,required = false) String header){
		this.getUserInfo(header, true);
		PageVO<SysFunVO> pageVo = this.sysFunService.queryFunByPage(pageQry);
		return (Result<PageVO<SysFunVO>>) Result.ok(pageVo);
	}
	@ApiOperation(value = "树形结构查询权限信息",notes = "查询")
	@GetMapping(value = "/queryFunTree")
	public Result<PageVO<SysFunVO>> queryFunTree(
			@ModelAttribute SysFunTreeQueryDTO queryTree,
			@ApiIgnore @RequestHeader(name = Global.GLOBAL_HEADER,required = false) String header){
		this.getUserInfo(header, true);
		if(!StringUtils.isEmpty(queryTree.getNodeid())){
		 queryTree.setParentid(queryTree.getNodeid());
		}else{
			queryTree.setParentid(queryTree.getParentid());
		}
		List<SysFunVO> funVos = this.sysFunService.queryFunTree(queryTree);
		funVos.parallelStream().forEach(t->{
		  if(t.isHasChild()){
			 t.setExpand(false);
			 t.setLeaf(false);
		  }else{
			  t.setExpand(true);
			  t.setLeaf(true);
		  }
		});
		PageVO pageVo = new PageVO();
		pageVo.setResult(funVos);
		pageVo.setTotalSize(funVos.size());
		return  (Result<PageVO<SysFunVO>>) Result.ok(pageVo);
	}
	
	@ApiOperation(value = "删除功能权限信息",notes = "删除")
	@DeleteMapping(value = "/deleteFun")
	public Result remove(
			@RequestParam String funid,
			@ApiIgnore @RequestHeader(name = Global.GLOBAL_HEADER,required = false) String header){
		GlobalHeader globalHeader = this.getUserInfo(header, true);
		this.sysFunService.deleteFun(funid,globalHeader.getUserId());
		return Result.ok();
	}
	@ApiOperation(value = "查询功能权限信息",notes = "查询")
	@GetMapping(value = "/queryFun")
	public Result<SysFunVO> queryFun(
			@RequestParam String funid,
			@ApiIgnore @RequestHeader(name = Global.GLOBAL_HEADER,required = false) String header){
		this.getUserInfo(header, true);
		SysFunQueryDTO dto = this.sysFunService.queryFun(funid,true);
		SysFunVO funVo = DataUtils.copyTo(dto, SysFunVO.class);
		return (Result<SysFunVO>) Result.ok(funVo);
	}
	
}
