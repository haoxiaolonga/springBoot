package com.spring.demo.springbootexample.mybatis.po;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

import java.util.List;

public class Test {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column test.id
     *
     * @mbggenerated Fri Nov 16 14:49:15 CST 2018
     */
//    @ApiParam("id")
    @ApiModelProperty("参数ID")
    private String id;

    @ApiModelProperty("内置对象参数")
    List<Test2> data;

    public List<Test2> getData() {
        return data;
    }

    public void setData(List<Test2> data) {
        this.data = data;
    }

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column test.name
     *
     * @mbggenerated Fri Nov 16 14:49:15 CST 2018
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column test.phone
     *
     * @mbggenerated Fri Nov 16 14:49:15 CST 2018
     */
    private String phone;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column test.id
     *
     * @return the value of test.id
     *
     * @mbggenerated Fri Nov 16 14:49:15 CST 2018
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column test.id
     *
     * @param id the value for test.id
     *
     * @mbggenerated Fri Nov 16 14:49:15 CST 2018
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column test.name
     *
     * @return the value of test.name
     *
     * @mbggenerated Fri Nov 16 14:49:15 CST 2018
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column test.name
     *
     * @param name the value for test.name
     *
     * @mbggenerated Fri Nov 16 14:49:15 CST 2018
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column test.phone
     *
     * @return the value of test.phone
     *
     * @mbggenerated Fri Nov 16 14:49:15 CST 2018
     */
    public String getPhone() {
        return phone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column test.phone
     *
     * @param phone the value for test.phone
     *
     * @mbggenerated Fri Nov 16 14:49:15 CST 2018
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }
}