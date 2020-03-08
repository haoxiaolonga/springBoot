package com.spring.demo.springbootexample.common;

import com.spring.demo.springbootexample.protocol.sso.sysuserrole.SysUserRoleDTO;
import com.spring.demo.springbootexample.sso.sysuser.entity.SysUser;

import java.util.List;

public class RedisExtCommands implements IRedisExtCommands {
    @Override
    public String hget(String syuserRedisMobileTouseridPrekey, String mobilephone) {
        return null;
    }

    @Override
    public void hset(String systemUserRoleCacheKey, String userId, List<SysUserRoleDTO> userRoles) {

    }

    @Override
    public SysUser hget(String syuserRedisUseridToUserPrekey, String userId, Class<SysUser> sysUserClass) {
        return null;
    }

    @Override
    public boolean hset(String syuserRedisMobileTouseridPrekey, String mobilephone, Object userId, int expireThirtyDays) {
        return false;
    }
}
