/**  
 * All rights Reserved, Designed By http://www.pete-cat.com/
 * @Title:  SysFunController.java   
 * @Package com.spring.demo.springbootexample.sso.sysuserfun
 * @Description:系统功能权限
 * @author: 成都皮特猫科技     
 * @date:2017年7月20日 下午6:48:48   
 * @version V1.0 
 * @Copyright: 2017 www.pete-cat.com Inc. All rights reserved. 
 * 注意：本内容仅限于成都皮特猫信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */  
package com.spring.demo.springbootexample.sso.sysfunvisturl.controller;

import com.spring.demo.springbootexample.base.BaseController;
import com.spring.demo.springbootexample.common.DataUtils;
import com.spring.demo.springbootexample.common.Global;
import com.spring.demo.springbootexample.common.GlobalHeader;
import com.spring.demo.springbootexample.protocol.Result;
import com.spring.demo.springbootexample.protocol.sso.sysfunvisturl.SyChanmgfunVistUrlmQryDTO;
import com.spring.demo.springbootexample.protocol.sso.sysfunvisturl.SyChanmgfunVistUrlmQryVO;
import com.spring.demo.springbootexample.sso.sysfunvisturl.service.ISyChanmgfunVistUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**   
 * @ClassName:  SysFunController   
 * @Description:系统功能权限  
 * @author: admin
 * @date:   2017年7月20日 下午6:48:48   
 */
@RestController
@Api(value="系统功能权限访问地址管理",tags="功能权限访问地址管理")
@RequestMapping("/sy/sysfunvist")
public class SysFunVistController extends BaseController {

	@Autowired
	private ISyChanmgfunVistUrlService syChanmgfunVistUrlService;
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "通过功能权限查询访问vo",notes = "查询")
	@GetMapping(value = "/getVistUrl")
	public Result<List<SyChanmgfunVistUrlmQryVO>> getVistUrl(
			@RequestParam String funid,
			@RequestHeader(Global.GLOBAL_HEADER) String header){
		this.getUserInfo(header, true);
		List<SyChanmgfunVistUrlmQryDTO> dtos = this.syChanmgfunVistUrlService.queryFunVist(funid);
		List<SyChanmgfunVistUrlmQryVO>  vos = DataUtils.copyTo(dtos, SyChanmgfunVistUrlmQryVO.class);
		return (Result<List<SyChanmgfunVistUrlmQryVO>>) Result.ok(vos);
	}
	

	@ApiOperation(value = "重新加载可访问地址等",notes = "查询")
	@GetMapping(value = "/reloadVistUrl")
	public Result reloadVistUrl(
			@RequestHeader(Global.GLOBAL_HEADER) String header){
		GlobalHeader userHead = this.getUserInfo(header, false);
		if(userHead != null) {
			this.syChanmgfunVistUrlService.reloadVistUrl(userHead.getUserId());
		}else {
			this.syChanmgfunVistUrlService.reloadVistUrl(null);
		}
		
		return Result.ok();
	}
}
