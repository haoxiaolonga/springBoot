package com.petecat.interchan.sso.sysfun.service;

import java.util.List;

import com.petecat.interchan.core.service.BaseService;
import com.petecat.interchan.protocol.data.PageVO;
import com.petecat.interchan.protocol.sso.sysfun.SysFunAddDTO;
import com.petecat.interchan.protocol.sso.sysfun.SysFunModDTO;
import com.petecat.interchan.protocol.sso.sysfun.SysFunPageQueryDTO;
import com.petecat.interchan.protocol.sso.sysfun.SysFunQueryDTO;
import com.petecat.interchan.protocol.sso.sysfun.SysFunTreeQueryDTO;
import com.petecat.interchan.protocol.sso.sysfun.SysFunVO;
import com.petecat.interchan.sso.sysfun.entity.SysFun;

/**
 * 
 * @ClassName:  ISysFunService   
 * @Description:系统功能权限服务
 * @author: 张小虎
 * @date:   2017年7月19日 上午10:06:08
 */
public interface ISysFunService extends BaseService<SysFun, String>{

	/**   
	 * @Title: saveFun   
	 * @Description: 新增功能权限信息
	 * @param sysFunAddDTO 功能权限参数
	 * @param userId 操作人
	 * @return void     
	 */
	void saveFun(SysFunAddDTO sysFunAddDTO, String userId);

	/**   
	 * @Title: updateFun   
	 * @Description:修改功能权限  
	 * @param sysFunModDTO
	 * @param userId
	 * @return void     
	 */
	void updateFun(SysFunModDTO sysFunModDTO, String userId);

	/**   
	 * @Title: queryFunByPage   
	 * @Description: 分页查询功能权限  
	 * @param dto
	 * @return PageVo<SysFunVo>     
	 */
	PageVO<SysFunVO> queryFunByPage(SysFunPageQueryDTO dto);

	/**   
	 * @Title: deleteFun   
	 * @Description: 删除功能权限
	 * @param fun 功能权限id
	 * @param userid 用户ID
	 * @return void     
	 */
	void deleteFun(String fun, String userid);

	/**   
	 * @Title: queryFun   
	 * @Description:查询功能权限
	 * @param fun 功能权限id
	 * @param nullException 为空是否异常
	 * @return SysFunQueryDTO    
	 */
	SysFunQueryDTO queryFun(String fun, boolean nullException);

	/**   
	 * @Title: queryFunTree   
	 * @Description: 查询功能权限树形结构
	 * @param dto
	 * @return List<SysFunVo>     
	 */
	List<SysFunVO> queryFunTree(SysFunTreeQueryDTO dto);
	
}
