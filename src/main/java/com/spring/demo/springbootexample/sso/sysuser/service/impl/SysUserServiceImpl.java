package com.spring.demo.springbootexample.sso.sysuser.service.impl;

import com.spring.demo.springbootexample.base.BaseServiceImpl;
import com.spring.demo.springbootexample.common.BusinessException;
import com.spring.demo.springbootexample.common.DataUtils;
import com.spring.demo.springbootexample.common.Global;
import com.spring.demo.springbootexample.common.IRedisExtCommands;
import com.spring.demo.springbootexample.common.IdWorker;
import com.spring.demo.springbootexample.protocol.InsertInto;
import com.spring.demo.springbootexample.protocol.Page;
import com.spring.demo.springbootexample.protocol.PageVO;
import com.spring.demo.springbootexample.protocol.Result;
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
import com.spring.demo.springbootexample.redis.RedisUtil;
import com.spring.demo.springbootexample.sso.sysuser.domain.SysUserDO;
import com.spring.demo.springbootexample.sso.sysuser.entity.SysUser;
import com.spring.demo.springbootexample.sso.sysuser.mapper.SysUserMapper;
import com.spring.demo.springbootexample.sso.sysuser.mapper.SysUserRecordMapper;
import com.spring.demo.springbootexample.sso.sysuser.service.ISysUserService;
import com.spring.demo.springbootexample.sso.util.AES;
import com.spring.demo.springbootexample.sso.util.MD5;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("sysUserService")
public class SysUserServiceImpl extends BaseServiceImpl<SysUser, String> implements ISysUserService {

	private SysUserMapper sysUserMapper;

	@Autowired
	private SysUserRecordMapper sysUserRecordMapper;

	@Autowired
	private IRedisExtCommands redisExtCommands;

	@Autowired
	private RedisUtil redisUtil;

	private final static String SYUSER_REDIS_MOBILE_TOUSERID_PREKEY="syur_moblie_uid_";

	private final static String SYUSER_REDIS_USERID_TO_USER_PREKEY="syur_user_uid_";

	private final static Logger logger = LoggerFactory.getLogger(SysUserServiceImpl.class);

	@Autowired
	private Environment environment;

	@Value("${sysuser_pwd_enc_key}")
	private String encryptKey;

	@Autowired
	public void setSysUserMapper(SysUserMapper sysUserMapper) {
		this.sysUserMapper = sysUserMapper;
		this.setBaseMapper(sysUserMapper);
	}

	@Override
	public PageVO<SysUserVO> queryUserByPage(SysUserDTO sysUserDTO) {
		PageVO<SysUserVO> result = new PageVO<SysUserVO>();
		Page<SysUser> pageUser = new Page<SysUser>();
		packPageQueryUser(sysUserDTO,pageUser);
		result.setTotalSize(sysUserMapper.count(pageUser.getRecord()));
		List<SysUser> sysUserList = sysUserMapper.page(pageUser);
		result.setResult(DataUtils.copyTo(sysUserList,SysUserVO.class));
		return result;
	}

	/*
	 * 组装分页查询对象
	 */
	private void packPageQueryUser(SysUserDTO source,Page<SysUser> target){
		target.setRecord(new SysUser());
		target.getRecord().setMobilephone(source.getMobilephone());
		target.getRecord().setUsername(source.getUsername());
		target.setRows(source.getRows());
		target.setStart((source.getStart() - 1) * source.getRows());
	}

	@Override
	public void saveUser(SysUserAddDTO sysUserAddDTO) {
		String userId = redisExtCommands.hget(SYUSER_REDIS_MOBILE_TOUSERID_PREKEY, sysUserAddDTO.getMobilephone());
    	if(StringUtils.isNotBlank(userId)){
			throw new BusinessException(Result.SYS_RESULT_FAILD,
				environment.getProperty("chan_mobile_repeat"));
		}
    	SysUser sysUser = sysUserMapper.getSysUserByMobilephone(sysUserAddDTO.getMobilephone());
    	if(sysUser != null){
    		throw new BusinessException(Result.SYS_RESULT_FAILD,
					environment.getProperty("chan_mobile_repeat"));
    	}
		sysUser = DataUtils.copyTo(sysUserAddDTO, SysUser.class);
		sysUser.setUserid(new IdWorker().nextId()+"");
		sysUser.setEntrantdate(new Date());
		sysUser.setOperateTime(new Date());
		try {
			sysUser.setPassword(MD5.GetMD5Code(AES.encrypt(encryptKey,
					sysUserAddDTO.getPassword()), 32));
		} catch (Exception e) {
			logger.error("新增用户加密密码失败", e);
			throw new  BusinessException(Result.SYS_RESULT_FAILD,
					this.environment.getProperty("sysuser_pwd_error"));
		}

		int count =  save(sysUser);
		if(count  == 0){
			throw new BusinessException(Result.SYS_FAILD, Result.FAILD_MSG);
		}

		InsertInto<String> insertInto = new InsertInto<>();
		insertInto.setId(sysUser.getUserid());
		insertInto.setReqNo(new IdWorker().nextId()+"");
		insertInto.setStatus(InsertInto.ADD);
		count = sysUserRecordMapper.insertInto(insertInto);
		if(count  == 0){
			throw new BusinessException(Result.SYS_FAILD, Result.FAILD_MSG);
		}

		this.setSyuserRedisMobileToUser(sysUserAddDTO.getMobilephone(), sysUser.getUserid());
		this.setSyuserRedisUserIdToUser(sysUser.getUserid(), sysUser);
	}

	@Override
	public void updateUser(SysUserUpdateDTO sysUserUpdateDTO) {
		SysUser sysUser = this.getSyuserRedisUserId(sysUserUpdateDTO.getUserid());
    	if(sysUser == null){
    		sysUser = sysUserMapper.getById(sysUserUpdateDTO.getUserid());
    		if(sysUser == null){
    			throw new BusinessException(Result.SYS_RESULT_FAILD,
    					environment.getProperty("chan_mobile_repeat"));
    		}
		}
		sysUser = DataUtils.copyTo(sysUserUpdateDTO, SysUser.class);
		sysUser.setOperateTime(new Date());
		if(StringUtils.isNotBlank(sysUserUpdateDTO.getPassword())){
			try {
				sysUser.setPassword(MD5.GetMD5Code(AES.encrypt(encryptKey,
						sysUserUpdateDTO.getPassword()), 32));
			} catch (Exception e) {
				logger.error("修改用户加密密码失败", e);
				throw new  BusinessException(Result.SYS_RESULT_FAILD,
						this.environment.getProperty("sysuser_pwd_error"));
			}
		}

		int count =  update(sysUser);
		if(count  == 0){
			throw new BusinessException(Result.SYS_FAILD, Result.FAILD_MSG);
		}

		InsertInto<String> insertInto = new InsertInto<>();
		insertInto.setId(sysUser.getUserid());
		insertInto.setReqNo(new IdWorker().nextId()+"");
		insertInto.setStatus(InsertInto.UPDATE);
		count = sysUserRecordMapper.insertInto(insertInto);
		if(count  == 0){
			throw new BusinessException(Result.SYS_FAILD, Result.FAILD_MSG);
		}

		sysUser = sysUserMapper.getById(sysUserUpdateDTO.getUserid());
		this.setSyuserRedisMobileToUser(sysUser.getMobilephone(), sysUser.getUserid());
		this.setSyuserRedisUserIdToUser(sysUser.getUserid(), sysUser);
	}

	@Override
	public void updatePwd(UpdatePwdDTO updatePwdDTO) {
		SysUser sysUser = this.getSyuserRedisUserId(updatePwdDTO.getUserid());
    	if(sysUser == null){
    		sysUser = sysUserMapper.getById(updatePwdDTO.getUserid());
    		if(sysUser == null){
    			throw new BusinessException(Result.SYS_RESULT_FAILD,
    					environment.getProperty("chan_mobile_repeat"));
    		}
		}
		sysUser = DataUtils.copyTo(updatePwdDTO, SysUser.class);
		sysUser.setOperateTime(new Date());
		sysUser.setOperateUser(updatePwdDTO.getUserid());
		try {
			sysUser.setPassword(MD5.GetMD5Code(AES.encrypt(encryptKey,
					updatePwdDTO.getPassword()), 32));
		} catch (Exception e) {
			logger.error("修改用户加密密码失败", e);
			throw new  BusinessException(Result.SYS_RESULT_FAILD,
					this.environment.getProperty("sysuser_pwd_error"));
		}

		int count =  update(sysUser);
		if(count  == 0){
			throw new BusinessException(Result.SYS_FAILD, Result.FAILD_MSG);
		}

		InsertInto<String> insertInto = new InsertInto<>();
		insertInto.setId(sysUser.getUserid());
		insertInto.setReqNo(new IdWorker().nextId()+"");
		insertInto.setStatus(InsertInto.UPDATE);
		count = sysUserRecordMapper.insertInto(insertInto);
		if(count  == 0){
			throw new BusinessException(Result.SYS_FAILD, Result.FAILD_MSG);
		}

		sysUser = sysUserMapper.getById(updatePwdDTO.getUserid());
		this.setSyuserRedisUserIdToUser(sysUser.getUserid(), sysUser);
	}

	@Override
	public void updateLoginPwd(UpdateCenterPwdDTO updatePwdDTO) {
		SysUser sysUser = this.getSyuserRedisUserId(updatePwdDTO.getUserid());
    	if(sysUser == null){
    		sysUser = sysUserMapper.getById(updatePwdDTO.getUserid());
    		if(sysUser == null){
    			throw new BusinessException(Result.SYS_RESULT_FAILD,
    					environment.getProperty("chan_mobile_repeat"));
    		}
		}

    	//判断旧密码
		try {
			String oldPasswd = MD5.GetMD5Code(AES.encrypt(encryptKey,
					updatePwdDTO.getOldPassword()), 32);
			if(!StringUtils.equals(oldPasswd, sysUser.getPassword())){
				throw new  BusinessException(Result.SYS_RESULT_FAILD,
						this.environment.getProperty("sysuser_pwd_error"));
			}
		}catch (Exception e) {
			logger.error("旧密码失败", e);
			throw new  BusinessException(Result.SYS_RESULT_FAILD,
					this.environment.getProperty("sysuser_pwd_error"));
		}

		sysUser = DataUtils.copyTo(updatePwdDTO, SysUser.class);
		sysUser.setOperateTime(new Date());
		sysUser.setOperateUser(updatePwdDTO.getUserid());
		try {
			sysUser.setPassword(MD5.GetMD5Code(AES.encrypt(encryptKey,
					updatePwdDTO.getPassword()), 32));
		} catch (Exception e) {
			logger.error("修改用户加密密码失败", e);
			throw new  BusinessException(Result.SYS_RESULT_FAILD,
					this.environment.getProperty("sysuser_pwd_error"));
		}

		int count =  update(sysUser);
		if(count  == 0){
			throw new BusinessException(Result.SYS_FAILD, Result.FAILD_MSG);
		}

		InsertInto<String> insertInto = new InsertInto<>();
		insertInto.setId(sysUser.getUserid());
		insertInto.setReqNo(new IdWorker().nextId()+"");
		insertInto.setStatus(InsertInto.UPDATE);
		count = sysUserRecordMapper.insertInto(insertInto);
		if(count  == 0){
			throw new BusinessException(Result.SYS_FAILD, Result.FAILD_MSG);
		}

		sysUser = sysUserMapper.getById(updatePwdDTO.getUserid());
		this.setSyuserRedisUserIdToUser(sysUser.getUserid(), sysUser);
	}
	@Override
	public void resetPwd(ResetPwdDTO resetPwdDTO) {
		SysUser sysUser = this.getSyuserRedisUserId(resetPwdDTO.getUserid());
    	if(sysUser == null){
    		sysUser = sysUserMapper.getById(resetPwdDTO.getUserid());
    		if(sysUser == null){
    			throw new BusinessException(Result.SYS_RESULT_FAILD,
    					environment.getProperty("chan_mobile_repeat"));
    		}
		}
		sysUser = DataUtils.copyTo(resetPwdDTO, SysUser.class);
		sysUser.setOperateTime(new Date());
		try {
			sysUser.setPassword(MD5.GetMD5Code(AES.encrypt(encryptKey,
					resetPwdDTO.getPassword()), 32));
		} catch (Exception e) {
			logger.error("修改用户加密密码失败", e);
			throw new  BusinessException(Result.SYS_RESULT_FAILD,
					this.environment.getProperty("sysuser_pwd_error"));
		}

		int count =  update(sysUser);
		if(count  == 0){
			throw new BusinessException(Result.SYS_FAILD, Result.FAILD_MSG);
		}

		InsertInto<String> insertInto = new InsertInto<>();
		insertInto.setId(sysUser.getUserid());
		insertInto.setReqNo(new IdWorker().nextId()+"");
		insertInto.setStatus(InsertInto.UPDATE);
		count = sysUserRecordMapper.insertInto(insertInto);
		if(count  == 0){
			throw new BusinessException(Result.SYS_FAILD, Result.FAILD_MSG);
		}

		sysUser = sysUserMapper.getById(resetPwdDTO.getUserid());
		this.setSyuserRedisUserIdToUser(sysUser.getUserid(), sysUser);
	}

	@Override
	public void lockUser(UserDTO lockUserDTO) {
		SysUser sysUser = this.getSyuserRedisUserId(lockUserDTO.getUserid());
    	if(sysUser == null){
    		sysUser = sysUserMapper.getById(lockUserDTO.getUserid());
    		if(sysUser == null){
    			throw new BusinessException(Result.SYS_RESULT_FAILD,
    					environment.getProperty("chan_mobile_repeat"));
    		}
		}
		sysUser = DataUtils.copyTo(lockUserDTO, SysUser.class);
		sysUser.setOperateTime(new Date());

		int count =  sysUserMapper.lock(sysUser);
		if(count  == 0){
			throw new BusinessException(Result.SYS_FAILD, Result.FAILD_MSG);
		}

		InsertInto<String> insertInto = new InsertInto<>();
		insertInto.setId(sysUser.getUserid());
		insertInto.setReqNo(new IdWorker().nextId()+"");
		insertInto.setStatus(InsertInto.UPDATE);
		count = sysUserRecordMapper.insertInto(insertInto);
		if(count  == 0){
			throw new BusinessException(Result.SYS_FAILD, Result.FAILD_MSG);
		}

		sysUser = sysUserMapper.getById(lockUserDTO.getUserid());
		this.setSyuserRedisUserIdToUser(sysUser.getUserid(), sysUser);
	}
	/**
	 * <p>Title: loginUsePwd</p>
	 * <p>Description: </p>
	 * @param mobilephone
	 * @param password
	 * @see com.spring.demo.springbootexample.sso.sysuser.service.ISysUserService#loginUsePwd(String, String)
	 */
	@Override
	public LoginSysUserDTO loginUsePwd(String mobilephone, String password) {
		SysUser user = this.getCacheUserByMobileToLogin(mobilephone,"");
		LoginSysUserDTO dto = null;
		boolean setRedis = false;
		String realPwd = password;
		try {
			realPwd = MD5.GetMD5Code(AES.encrypt(encryptKey,realPwd), 32);
		} catch (Exception e) {
			logger.error("解析用户密码出错,手机号：{}",mobilephone,e);
			throw new BusinessException(Result.SYS_FAILD,
					environment.getProperty("sysuser_mobile_or_pwd_error"));
		}
		if( user==null ){
			user = this.sysUserMapper.getSysUserByMobilephone(mobilephone);
			setRedis = true;
		}
		if(user == null){
			this.setSyuserRedisMobileToUser(mobilephone, null);
			throw new BusinessException(Result.SYS_RESULT_FAILD,"密码错误");
		}else{
			if(setRedis){
				this.setSyuserRedisMobileToUser(mobilephone, user.getUserid());
				this.setSyuserRedisUserIdToUser(user.getUserid(), user);
			}
			if(!realPwd.equals(user.getPassword())){
	    		throw new BusinessException(Result.SYS_RESULT_FAILD,
						environment.getProperty("账号或密码错误"));
	    	}
			this.checkSysuser(user);
			dto = DataUtils.copyTo(user, LoginSysUserDTO.class);
		}
		return dto;
	}

	/**
	 * @Title: checkSysuser
	 * @Description: 检查当前用户是否过期
	 * @param user
	 * @return void
	 */
	private void checkSysuser(SysUser user) {
		if("0".equals(user.getAvailable())){
			throw new BusinessException(Result.SYS_RESULT_FAILD,
					this.environment.getProperty("sysuser_is_invalid"));
		}
		if("9".equals(user.getChgpwdflag())){
			throw new BusinessException(Result.SYS_RESULT_FAILD,
					this.environment.getProperty("sysuser_is_lock"));
		}

	}

	/**
	 * @Title: getCacheUserByMobileToLogin
	 * @Description:
	 * @param mobilephone
	 * @return SysUser
	 */
	private SysUser getCacheUserByMobileToLogin(String mobilephone, String errorKey) {
		SysUser  sysuser = null;
		//从缓存中加载用户信息
		String userId =	 this.getUserIdByMobileFromCache(mobilephone);
		if(StringUtils.isNotBlank(userId)  ){
			sysuser =  loadSysUserFromCache(userId);
		}else{
			if(StringUtils.isNotBlank(errorKey)){
			   throw new BusinessException(Result.SYS_RESULT_FAILD,
					environment.getProperty(errorKey));
			}
		}
		return sysuser;
	}

	/**
	 * @Title: loadSysUserFromCache
	 * @Description: 加载用户从缓存中
	 * @param userId
	 * @return SysUser
	 */
	private SysUser loadSysUserFromCache(String userId) {
		SysUser  sysuser = null;
    	try {
    		//从缓存中加载用户信息
    		sysuser = redisExtCommands.hget(SYUSER_REDIS_USERID_TO_USER_PREKEY, userId,SysUser.class);
		} catch (Exception e) {
			logger.error("使用userId从缓存中获取用户信息失败，userId为：{}",userId,e);
		}
		return sysuser;
	}

	/**
	 * @Title: getUserIdByMobileFromCache
	 * @Description:手机号换用户ID
	 * @param mobilephone
	 * @return String
	 */
	private String getUserIdByMobileFromCache(String mobilephone) {
		try {
			return String.valueOf(redisUtil.hget(SYUSER_REDIS_MOBILE_TOUSERID_PREKEY, mobilephone));
		} catch (Exception e) {
			logger.error("getUserIdByMobileFromCache 获取用户缓存信息失败,手机号为：{}",mobilephone,e);
		}
		return null;
	}

	/**
	 * @Title:  getSyuserRedisMobileToUserIdKey
	 * @Description:获取SYUSER_REDIS_MOBILE_TOUSERID_KEY的值
	 * @return: String
	 */
	public  String getSyuserRedisMobileToUserIdKey(String mobilephone) {
		return SYUSER_REDIS_MOBILE_TOUSERID_PREKEY+mobilephone;
	}

	/**
	 * @Title:  getSyuserRedisUseridToUserPrekey
	 * @Description:获取SYUSER_REDIS_USERID_TO_USER_PREKEY的值
	 * @return: String
	 */
	public  String getSyuserRedisUserIdToUserKey(String userId) {
		return SYUSER_REDIS_USERID_TO_USER_PREKEY+userId;
	}

	/**
	 * @Title:  setSyuserRedisMobileToUser
	 * @Description:设置手机号到用户ID的缓存
	 * @return: String
	 */
	public  boolean setSyuserRedisMobileToUser(String mobilephone,String userId) {
		try {
			return redisExtCommands.hset(SYUSER_REDIS_MOBILE_TOUSERID_PREKEY,
					mobilephone, userId, Global.EXPIRE_THIRTY_DAYS);
		} catch (Exception e) {
			logger.error("setSyuserRedisMobileToUser 设置缓存信息失败,手机号为：{}",mobilephone,e);
			return false;
		}

	}

	/**
	 * @Title:  setSyuserRedisUserIdToUser
	 * @Description:设置用户ID到用户缓存
	 * @return: String
	 */
	public  boolean setSyuserRedisUserIdToUser(String userId,SysUser user) {
		try {
			return redisExtCommands.hset(SYUSER_REDIS_USERID_TO_USER_PREKEY,
					userId,user,Global.EXPIRE_THIRTY_DAYS);
		} catch (Exception e) {
			logger.error("setSyuserRedisUserIdToUser 设置缓存信息失败,用户ID为：{}",userId,e);
			return false;
		}
	}

	private SysUser getSyuserRedisUserId(String userId){
		try {
			return redisExtCommands.hget(SYUSER_REDIS_USERID_TO_USER_PREKEY, userId, SysUser.class);
		} catch (Exception e) {
			logger.error("getSyuserRedisUserId 获取缓存失败,用户ID为：{}",userId,e);
			return null;
		}
	}

	/**
	 * <p>Title: pageForOrder</p>
	 * <p>Description: </p>
	 * @param sysUserDTO
	 * @return
	 * @see com.spring.demo.springbootexample.sso.sysuser.service.ISysUserService#pageForOrder(com.spring.demo.springbootexample.protocol.sso.sysuser.dto.SysUserPageQryDTO)
	 */
	@Override
	public List<SysUserPageDTO> pageForOrder(SysUserPageQryDTO sysUserDTO) {
		Page<SysUserDO> pageUser = DataUtils.copyTo(sysUserDTO,Page.class);
		SysUserDO userDo = DataUtils.copyTo(sysUserDTO, SysUserDO.class);
		pageUser.setRecord(userDo);
		pageUser.setStart(pageUser.getStart()-1);
		List<SysUser> datas = this.sysUserMapper.pageForOrder(pageUser);
		return DataUtils.copyTo(datas, SysUserPageDTO.class);
	}

	/**
	 * <p>Title: findByUserIds</p>
	 * <p>Description: </p>
	 * @param userIds
	 * @return
	 * @see com.spring.demo.springbootexample.sso.sysuser.service.ISysUserService#findByUserIds(List)
	 */
	@Override
	public List<SysUser> findByUserIds(List<String> userIds) {
		return this.sysUserMapper.findByUserIds(userIds);
	}

}
