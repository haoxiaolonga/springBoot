package com.spring.demo.springbootexample.base;

import com.spring.demo.springbootexample.protocol.InsertInto;
import com.spring.demo.springbootexample.protocol.Page;

import java.io.Serializable;
import java.util.List;

public interface BaseMapper<T extends Serializable, Id> {

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
     * @return int 新增个数
     * @Title: save
     * @Description: 新增单个实例
     */
    int save(T t);

    /**
     * @param t 实例对象
     * @return int
     * 修改个数
     * @Title: update
     * @Description: 修改单个实例
     */
    int update(T t);

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
     * @param t
     * @return int
     * @Title: delete
     * @Description: 删除
     */
    int delete(T t);

    /**
     * @param id
     * @return int
     * @Title: delete
     * @Description: 删除
     */
    int delete(Id id);

    /**
     * @param t 查询的实例
     * @return int
     * 查询的个数
     * @Title: count
     * @Description: 查询条数
     */
    int count(T t);

    /**
     * @param t
     * @return List<T>
     * @Title: query
     * @Description: 查询
     */
    List<T> query(T t);

    /**
     * @return List<T>
     * @Title: queryAll
     * @Description: 查询
     */
    List<T> queryAll();

    /**
     * @param page
     * @return List<T>
     * @Title: page
     * @Description: 分页
     */
    List<T> page(Page<T> page);

    int pageCount(Page<T> page);

    /**
     * @param insertInto
     * @return int
     * @Title: insertInto
     * @Description: 历史
     */
    int insertInto(InsertInto<Id> insertInto);

    int insert(T t);
}
