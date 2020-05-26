package com.spring.demo.springbootexample.protocol;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class Page<T> extends PageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private T record;
}
