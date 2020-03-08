package com.spring.demo.springbootexample.protocol;

import lombok.Data;

import java.io.Serializable;

@Data
public class Page<T> extends PageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private T record;
}
