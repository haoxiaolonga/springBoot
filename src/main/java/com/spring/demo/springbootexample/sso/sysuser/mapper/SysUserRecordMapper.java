package com.spring.demo.springbootexample.sso.sysuser.mapper;

import com.spring.demo.springbootexample.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.spring.demo.springbootexample.sso.sysuser.entity.SysUserRecord;

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