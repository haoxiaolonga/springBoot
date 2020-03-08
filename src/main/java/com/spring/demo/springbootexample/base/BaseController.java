package com.spring.demo.springbootexample.base;

import com.spring.demo.springbootexample.common.GlobalHeader;

public abstract class BaseController {

    /**
     * @Title: getUserInfo
     * @Description: 获取用户信息
     * @param jsonHeadUser
     * @param nullThrowException 为null时要不要抛出异常
     * @return GlobalHeader
     */
    public GlobalHeader getUserInfo(String jsonHeadUser, boolean nullThrowException){
        return null;
    }
}