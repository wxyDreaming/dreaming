package com.wuxinyu.service;

import java.util.List;

import com.wuxinyu.entity.User;

public interface IUserService {
	
	User addUser(User user);
	
	void deleteUser(User user);
	
	void deleteUserById(Integer id);
	
	void updateUser(User user);
	
	List<User> findAll();
	
	User findById(Integer id);

	User getUser(String account, String password);

	User findByAccount(String account);

	User findByIdOrAccountOrTel(Integer id, String account, String tel);
	
}
