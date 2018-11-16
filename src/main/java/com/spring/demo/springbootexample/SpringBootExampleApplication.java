package com.spring.demo.springbootexample;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.spring.demo.springbootexample.mybatis.mapper")
public class SpringBootExampleApplication {

    public static void main(String[] args) {

        SpringApplication.run(SpringBootExampleApplication.class, args);
    }
}
