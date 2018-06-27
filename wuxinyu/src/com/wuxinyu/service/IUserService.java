package com.wuxinyu.service;

import com.wuxinyu.entity.User;

public interface IUserService extends IService<User, Integer>{

	User getUser(String account);

	User findByAccount(String account);

	User findByIdOrAccountOrTel(Integer id, String account, String tel);
	
	/**
	 * 判断指定id的对象是否存在
	 * @param id
	 * @return
	 */
	boolean isExist(Integer id);
}
