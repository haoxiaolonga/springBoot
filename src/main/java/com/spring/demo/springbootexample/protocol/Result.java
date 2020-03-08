package com.spring.demo.springbootexample.protocol;

import lombok.Data;

@Data
public class Result<T> {

    private T data;

    private int code;

    private String message;

    public final static int SYS_RESULT_FAILD= 500;
    public final static int SYS_FAILD = 600;
    public final static String FAILD_MSG = "操作失败";

    public static Result ok(Object t){
        Result result = ok();
        result.setData(t);
        return result;
    }

    public static Result ok(){
        Result result = new Result();
        result.setCode(200);
        result.setMessage("操作成功");
        return result;
    }

}
