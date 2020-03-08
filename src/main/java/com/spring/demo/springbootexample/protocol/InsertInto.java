package com.spring.demo.springbootexample.protocol;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class InsertInto<T> implements Serializable {

    private T id;

    private String reqNo;

    private String status;

    private Date opDate;

    private String userId;

    public final static String ADD = "1";
    public final static String UPDATE = "2";
    public final static String DELETE = "3";
}
