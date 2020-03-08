package com.spring.demo.springbootexample.common;

import com.spring.demo.springbootexample.protocol.sso.sysuserrole.SysUserRoleDTO;
import com.spring.demo.springbootexample.sso.sysuser.entity.SysUser;

import java.util.List;

public interface IRedisExtCommands {
    String hget(String syuserRedisMobileTouseridPrekey, String mobilephone);

    void hset(String systemUserRoleCacheKey, String userId, List<SysUserRoleDTO> userRoles);

    SysUser hget(String syuserRedisUseridToUserPrekey, String userId, Class<SysUser> sysUserClass);

    boolean hset(String syuserRedisMobileTouseridPrekey, String mobilephone, Object userId, int expireThirtyDays);
}
