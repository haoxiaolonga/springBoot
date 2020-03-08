package com.spring.demo.springbootexample.base;

import com.spring.demo.springbootexample.protocol.InsertInto;
import com.spring.demo.springbootexample.protocol.Page;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

public abstract class BaseServiceImpl <T extends Serializable,Id> implements BaseService<T, Id>{

    @Autowired
    private BaseMapper<T, Id> baseMapper;

    public void setBaseMapper(BaseMapper<T, Id> baseMapper) {
        this.baseMapper = baseMapper;
    }
    /**
     *
     * @Title: get
     * @Description: 获取单个实例
     * @param t
     * 		查询的实例对象
     * @return
     * @return T
     */
    @Override
    public T get(T t) {
        return baseMapper.get(t);
    }

    /**
     *
     * @Title: getById
     * @Description: 根据Id获取单个实例
     * @param id
     * 		查询的主键id
     * @return
     * @return T
     */
    @Override
    public T getById(Id id) {
        return baseMapper.getById(id);
    }

    /**
     *
     * @Title: save
     * @Description: 新增单个实例
     * @param t
     * 		实例对象
     * @return
     * @return int
     * 		新增个数
     */
    @Override
    public int save(T t) {
        return baseMapper.save(t);
    }

    /**
     *
     * @Title: update
     * @Description: 修改单个实例
     * @param t
     * 		实例对象
     * @return
     * @return int
     * 		修改个数
     */
    @Override
    public int update(T t){
        return baseMapper.update(t);
    }

    /**
     * @Title: remove
     * @Description: 删除单个实例
     * @param t
     * 		实例对象
     * @return
     * @return int
     * 		删除的个数
     */
    @Override
    public int remove(T t) {
        return baseMapper.remove(t);
    }

    /**
     * @Title: remove
     * @Description: 删除单个实例
     * @param id
     * 		删除实例的id
     * @return
     * @return int
     * 		删除的个数
     */
    @Override
    public int remove(Id id) {
        return baseMapper.remove(id);
    }

    /**
     * @Title: remove
     * @Description: 拆线呢总条数
     * @param t
     * 		查询实例
     * @return
     * @return int
     * 		查询的个数
     */
    @Override
    public int count(T t) {
        return baseMapper.count(t);
    }

    @Override
    public int insert(T t) {
        return baseMapper.insert(t);
    }
    @Override
    public List<T> page(Page<T> page){
        return baseMapper.page(page);
    }
    @Override
    public int pageCount(Page<T> page) {
        return baseMapper.pageCount(page);
    }

    @Override
    public List<T> queryAll() {
        return baseMapper.queryAll();
    }

    @Override
    public List<T> query(T t) {
        return baseMapper.query(t);
    }
    @Override
    public int delete(Id id) {
        return baseMapper.delete(id);
    }

    @Override
    public int insertInto(InsertInto<Id> into) {
        return baseMapper.insertInto(into);
    }
}