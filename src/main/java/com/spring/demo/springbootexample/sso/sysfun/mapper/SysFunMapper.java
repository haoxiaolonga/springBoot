package com.spring.demo.springbootexample.sso.sysfun.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.petecat.interchan.core.mapper.BaseMapper;
import com.petecat.interchan.protocol.InsertInto;
import com.petecat.interchan.protocol.data.Page;
import com.petecat.interchan.protocol.sso.sysfun.SysFunDTO;
import com.petecat.interchan.sso.sysfun.entity.SysFun;

/**
 * 
 * @ClassName:  SysFunMapper   
 * @Description:系统功能权限mapper  
 * @author: 张小虎
 * @date:   2017年7月19日 上午10:11:32
 */
@Mapper
@Repository
public interface SysFunMapper extends BaseMapper<SysFun, String> {

	/**   
	 * @Title: page   
	 * @Description: 分页查询功能权限信息 
	 * @param page
	 * @return List<SyChanmgfunm>     
	 */
	List<SysFun> page(Page<SysFun> page);

	/**   
	 * @Title: pageCount   
	 * @Description:分页查询数量
	 * @param page
	 * @return int     
	 */
	int pageCount(Page<SysFun> page);

	/**   
	 * @Title: queryFunTree   
	 * @Description: 查询树形结构信息
	 * @param funm
	 * @return List<SysFunDTO>     
	 */
	List<SysFunDTO> queryFunTree(SysFun funm);

	/**   
	 * @Title: updateById   
	 * @Description:更新功能权限
	 * @param fun
	 * @return void     
	 */
	void updateById(SysFun fun);

	/**   
	 * @Title: queryFunByParent   
	 * @Description: 通过父查询系统功能权限  
	 * @param funid
	 * @return List<SyChanmgfunm>     
	 */
	List<SysFun> queryFunByParent(String funid);

	/**   
	 * @Title: queryFunByParents   
	 * @Description:通过多个父查询系统功能权限  
	 * @param funids
	 * @return List<SyChanmgfunm>     
	 */
	List<SysFun> queryFunByParents(List<String> funids);

	/**   
	 * @Title: insertIntos   
	 * @Description: 插入多个历史表信息  
	 * @param into
	 * @return void     
	 */
	void insertIntos(InsertInto<List<String>> into);

	/**   
	 * @Title: deleteFunByIds   
	 * @Description: 删除功能号
	 * @param funid
	 * @return void     
	 */
	void deleteFunByIds(List<String> funids);
	
}