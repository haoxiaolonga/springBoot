package com.spring.demo.springbootexample.protocol.sso.sysuserrole;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class SysUserRoleBatchDTO  implements Serializable {
    /**
     * 用户代码
     */
    private String userid;



    /**
     * 操作时间
     */
    private Date operateTime;

    /**
     * 操作者
     */
    private String operateUser;
    
    private List<String> roleids;
    
    
    
    private static final long serialVersionUID = 1L;
}