package com.spring.demo.springbootexample.protocol.sso.sysfun;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SysFunModLevelSeqDTO implements Serializable {
    private List<SysFunDTO> funs;
    private SysFunDTO targetFun;
    private String moveType;
}