package com.wuxinyu.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

public interface IService<T, ID extends Serializable> {

	/**
	 * 保存对象
	 * @param obj
	 */
	 T save(T obj);
	 
	 /**
	  * 删除对象
	  * @param obj
	  */
	 void delete(T obj);
	 
	 /**
	  * 根据ID删除
	 * @param id
	 */
	void delete(ID id);
	 
	 /**
	  * 更新对象
	  * @param obj
	  */
	 void update(T obj);
	 
	 /**
	  * 得到对象
	  * @param id
	  * @return
	  */
	 T findById(ID id);
	 
	 /**
	  * 得到对象所有
	  * @return
	  */
	 List<T> findAll();
	 
	 /**
	  * 分页查询
	 * @param page
	 * @param size
	 * @return
	 */
	Page<T> findAll(Integer page,Integer size);
	 
	/**
	 * 分页带条件查询
	 * @param map
	 * @return
	 */
	Page<T> findAll(Map<String, Object> map);

}