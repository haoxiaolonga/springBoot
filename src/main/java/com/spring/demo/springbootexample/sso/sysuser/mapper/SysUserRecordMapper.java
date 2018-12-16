package com.petecat.interchan.sso.sysuser.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.petecat.interchan.core.mapper.BaseMapper;
import com.petecat.interchan.sso.sysuser.entity.SysUserRecord;

/**
 * 
 * @ClassName:  SysUserRecordMapper   
 * @Description:资金方历史信息mapper  
 * @author: mhuang
 * @date:   2017年7月19日 上午10:11:32
 */
@Mapper
@Repository
public interface SysUserRecordMapper extends BaseMapper<SysUserRecord, String> {
}