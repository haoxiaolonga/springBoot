package com.spring.demo.springbootexample.sso.sysfun.service.impl;

import com.spring.demo.springbootexample.base.BaseServiceImpl;
import com.spring.demo.springbootexample.common.BusinessException;
import com.spring.demo.springbootexample.common.DataUtils;
import com.spring.demo.springbootexample.common.IdWorker;
import com.spring.demo.springbootexample.protocol.InsertInto;
import com.spring.demo.springbootexample.protocol.Page;
import com.spring.demo.springbootexample.protocol.PageVO;
import com.spring.demo.springbootexample.protocol.Result;
import com.spring.demo.springbootexample.protocol.sso.sysfun.SysFunAddDTO;
import com.spring.demo.springbootexample.protocol.sso.sysfun.SysFunDTO;
import com.spring.demo.springbootexample.protocol.sso.sysfun.SysFunModDTO;
import com.spring.demo.springbootexample.protocol.sso.sysfun.SysFunPageQueryDTO;
import com.spring.demo.springbootexample.protocol.sso.sysfun.SysFunQueryDTO;
import com.spring.demo.springbootexample.protocol.sso.sysfun.SysFunTreeQueryDTO;
import com.spring.demo.springbootexample.protocol.sso.sysfun.SysFunVO;
import com.spring.demo.springbootexample.protocol.sso.sysfunvisturl.SyChanmgfunVistUrlmAddDTO;
import com.spring.demo.springbootexample.sso.sysfun.entity.SysFun;
import com.spring.demo.springbootexample.sso.sysfun.mapper.SysFunMapper;
import com.spring.demo.springbootexample.sso.sysfun.service.ISysFunService;
import com.spring.demo.springbootexample.sso.sysfunvisturl.service.ISyChanmgfunVistUrlService;
import com.spring.demo.springbootexample.sso.sysrolefun.service.ISysRoleFunService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service("sysFunService")
@Transactional(readOnly = true)
public class SysFunServiceImpl extends BaseServiceImpl<SysFun, String> implements ISysFunService {

	@Autowired
	private SysFunMapper sysFunMapper;

	@Autowired
	private Environment environment;

	@Autowired
	private ISysRoleFunService sysRoleFunService;

	@Autowired
	private ISyChanmgfunVistUrlService syChanmgfunVistUrlService;

	public void setMapper(SysFunMapper sysFunMapper) {
		this.setBaseMapper(sysFunMapper);
	}

	/**
	 * <p>
	 * Title: saveFun
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 *
	 * @param sysFunAddDTO
	 * @param userId
	 * @see com.spring.demo.springbootexample.sso.sysfun.service.ISysFunService#saveFun(com.spring.demo.springbootexample.protocol.sso.sysfun.SysFunAddDTO,
	 *      String)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void saveFun(SysFunAddDTO sysFunAddDTO, String userId) {
		// 检查代号是否存在
		SysFun fun = this.sysFunMapper.getById(sysFunAddDTO.getFunid());
		if (fun != null) {// 存在功能权限代码
			throw new BusinessException(Result.SYS_RESULT_FAILD, this.environment.getProperty("sysfunid_exists"));
		}
		fun = DataUtils.copyTo(sysFunAddDTO, SysFun.class);
		if ("TOP".equals(fun.getFunid())) {
			throw new BusinessException(Result.SYS_RESULT_FAILD, this.environment.getProperty("sysfunid_is_error"));
		}
		fun.setOperateTime(new Date());
		fun.setOperateUser(userId);
		if (StringUtils.isNotBlank(fun.getParentid()) && !"TOP".equals(fun.getParentid())) {
			SysFun parent = this.getById(fun.getParentid());
			if (parent == null) {
				throw new BusinessException(Result.SYS_RESULT_FAILD,
						this.environment.getProperty("sysfunparent_not_exists"));
			}
			fun.setLayid(parent.getLayid() + 1);
		} else {
			fun.setParentid("TOP");
			fun.setLayid(1);
		}
		this.sysFunMapper.save(fun);
		InsertInto<String> into = new InsertInto<>();
		into.setReqNo(new IdWorker().nextId()+"");
		into.setId(fun.getFunid());
		into.setStatus(InsertInto.ADD);
		into.setUserId(userId);
		this.sysFunMapper.insertInto(into);
		if(StringUtils.isNotBlank(sysFunAddDTO.getPowerPaths())){
			  final String funid = fun.getFunid();
			  List<String> urls = new ArrayList<>();
			  List<SyChanmgfunVistUrlmAddDTO> dtos =
					Arrays.asList(sysFunAddDTO.getPowerPaths().split(",")).parallelStream().map(value->{
					SyChanmgfunVistUrlmAddDTO dto = new SyChanmgfunVistUrlmAddDTO();
					dto.setFunid(funid);
					dto.setUrl(value);
					return dto;
			}).filter(value->{
				if(StringUtils.isNotBlank(value.getUrl())
						&& !urls.contains(value.getUrl())){
					urls.add(value.getUrl());
					return true;
				}else{
					return false;
				}
			}).collect(Collectors.toList());
			this.syChanmgfunVistUrlService.insertPowersUrl(dtos,userId,into.getReqNo());
		}
	}

	/**
	 * @param userId
	 * @see com.spring.demo.springbootexample.sso.sysfun.service.ISysFunService#saveFun(com.spring.demo.springbootexample.protocol.sso.sysfun.SysFunAddDTO,
	 *      String)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void updateFun(SysFunModDTO sysFunModDTO, String userId) {
		// 检查代号是否存在
		SysFun fun = this.sysFunMapper.getById(sysFunModDTO.getFunid());
		if (fun == null) {// 存在不存在功能权限代码
			throw new BusinessException(Result.SYS_RESULT_FAILD, this.environment.getProperty("sysfun_not_exists"));
		}
//		DataUtils.copyTo(sysFunModDTO, fun);|
		fun.setOperateTime(new Date());
		fun.setOperateUser(userId);
		if (StringUtils.isNotBlank(sysFunModDTO.getParentid()) && !"TOP".equals(fun.getParentid())) {
			SysFun parent = this.getById(sysFunModDTO.getParentid());
			if (parent == null) {
				throw new BusinessException(Result.SYS_RESULT_FAILD,
						this.environment.getProperty("sysfunparent_not_exists"));
			}
			fun.setLayid(parent.getLayid() + 1);
		} else {
			fun.setParentid("TOP");
			fun.setLayid(1);
		}

		this.sysFunMapper.updateById(fun);
		InsertInto<String> into = new InsertInto<>();
		into.setReqNo(new IdWorker().nextId()+"");
		into.setId(fun.getFunid());
		into.setStatus(InsertInto.UPDATE);
		into.setUserId(userId);
		this.sysFunMapper.insertInto(into);
		this.syChanmgfunVistUrlService.insertHByAuth(fun.getFunid(),userId,InsertInto.DELETE,new IdWorker().nextId()+"");
		this.syChanmgfunVistUrlService.deleteByAuth(fun.getFunid());
		if(StringUtils.isNotBlank(sysFunModDTO.getPowerPaths())){
			  final String funid = fun.getFunid();
			  List<String> urls = new ArrayList<>();
			  List<SyChanmgfunVistUrlmAddDTO> dtos =
					  Arrays.asList(sysFunModDTO.getPowerPaths().split(",")).parallelStream().map(value->{
				SyChanmgfunVistUrlmAddDTO dto = new SyChanmgfunVistUrlmAddDTO();
				dto.setFunid(funid);
				dto.setUrl(value);
				return dto;
			}).filter(value->{
				if(StringUtils.isNotBlank(value.getUrl())
						&& !urls.contains(value.getUrl())){
					urls.add(value.getUrl());
					return true;
				}else{
					return false;
				}
			}).collect(Collectors.toList());
			this.syChanmgfunVistUrlService.insertPowersUrl(dtos,userId,into.getReqNo());
		}
	}

	/**
	 * <p>
	 * Title: queryFunByPage
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 *
	 * @param dto
	 * @return
	 * @see com.spring.demo.springbootexample.sso.sysfun.service.ISysFunService#queryFunByPage(com.spring.demo.springbootexample.protocol.sso.sysfun.SysFunPageQueryDTO)
	 */
	@Override
	public PageVO<SysFunVO> queryFunByPage(SysFunPageQueryDTO dto) {
		Page<SysFun> page = new Page<>();
		SysFun funm = DataUtils.copyTo(dto, SysFun.class);
		page.setRecord(funm);
		page.setRows(dto.getRows());
		page.setStart((dto.getStart() - 1) * dto.getRows());
		PageVO<SysFunVO> pageVo = new PageVO<>();
		List<SysFun> sysFuns = sysFunMapper.page(page);
		pageVo.setResult(DataUtils.copyTo(sysFuns, SysFunVO.class));
		pageVo.setTotalSize(this.sysFunMapper.pageCount(page));
		return pageVo;
	}

	/**
	 * @param funid
	 * @param nullException
	 *            是否抛出数据不存在异常
	 * @return SysFunQueryDTO
	 */
	@Override
	public SysFunQueryDTO queryFun(String funid, boolean nullException) {
		SysFun fun = sysFunMapper.getById(funid);
		SysFunQueryDTO dto = null;
		if (fun == null) {
			if (nullException) {
				throw new BusinessException(Result.SYS_RESULT_FAILD, this.environment.getProperty("sysfun_not_exists"));
			}
		} else {
			dto = new SysFunQueryDTO();
			if (StringUtils.isNotBlank(fun.getParentid())) {
				try {
					dto.setParentName(this.getById(fun.getParentid()).getFundesc());
				} catch (Exception e) {
				}
			}
//			DataUtils.copyTo(fun, dto);
		}
		return dto;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void deleteFun(String funid, String userId) {
		List<SysFun> childFuns = this.sysFunMapper.queryFunByParent(funid);
		List<String> ids = new ArrayList<>();
		ids.add(funid);
		List<String> queryIds = new ArrayList<>();
		while (!CollectionUtils.isEmpty(childFuns)) {
			childFuns.parallelStream().forEachOrdered((data) -> {
				if (!ids.contains(data.getFunid())) {
					ids.add(data.getFunid());
					queryIds.add(data.getFunid());
				}
			});
			childFuns = null;
			if (!CollectionUtils.isEmpty(queryIds)) {
				childFuns = this.sysFunMapper.queryFunByParents(queryIds);
			}
			queryIds.clear();
		}
		InsertInto<List<String>> into = new InsertInto<>();
		into.setReqNo(new IdWorker().nextId()+"");
		into.setId(ids);
		into.setStatus(InsertInto.DELETE);
		into.setUserId(userId);
		into.setOpDate(new Date());
		this.sysFunMapper.insertIntos(into);
		this.sysFunMapper.deleteFunByIds(ids);
		this.sysRoleFunService.deleteRoleFunByFuns(ids, userId);
		this.syChanmgfunVistUrlService.deleteByFunsIds(ids,userId,InsertInto.DELETE,into.getReqNo());
	}

	public List<SysFunVO> queryFunTree(SysFunTreeQueryDTO dto) {
		SysFun funm = DataUtils.copyTo(dto, SysFun.class);
		List<SysFunDTO> funs = this.sysFunMapper.queryFunTree(funm);
		return DataUtils.copyTo(funs, SysFunVO.class);
	}

}
