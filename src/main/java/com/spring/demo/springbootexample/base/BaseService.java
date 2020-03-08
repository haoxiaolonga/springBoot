package com.spring.demo.springbootexample.base;

import com.spring.demo.springbootexample.protocol.InsertInto;
import com.spring.demo.springbootexample.protocol.Page;

import java.io.Serializable;
import java.util.List;

public interface BaseService<T extends Serializable,Id> {

    /**
     * @param t 查询的实例对象
     * @return T
     * @Title: get
     * @Description: 获取单个实例
     */
    T get(T t);

    /**
     * @param id 查询的主键id
     * @return T
     * @Title: getById
     * @Description: 根据Id获取单个实例
     */
    T getById(Id id);

    /**
     * @param t 实例对象
     * @return int
     * 新增个数
     * @Title: save
     * @Description: 新增单个实例
     */
    int save(T t);

    int insert(T t);

    /**
     * @param t 实例对象
     * @return int
     * 修改个数
     * @Title: update
     * @Description: 修改单个实例
     */
    int update(T t);

    int delete(Id id);

    /**
     * @param t 实例对象
     * @return int
     * 删除的个数
     * @Title: remove
     * @Description: 删除单个实例
     */
    int remove(T t);

    /**
     * @param id 删除的Id
     * @return int
     * 删除的个数
     * @Title: remove
     * @Description: 删除单个实例
     */
    int remove(Id id);

    /**
     * @param t 查询的实例
     * @return int
     * 查询的个数
     * @Title: count
     * @Description: 查询条数
     */
    int count(T t);

    public List<T> page(Page<T> page);

    public int pageCount(Page<T> page);

    List<T> queryAll();

    List<T> query(T t);

    int insertInto(InsertInto<Id> into);
}