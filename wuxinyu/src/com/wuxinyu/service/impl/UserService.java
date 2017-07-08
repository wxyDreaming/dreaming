package com.wuxinyu.service.impl;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wuxinyu.entity.User;
import com.wuxinyu.repository.UserRepository;
import com.wuxinyu.service.IUserService;

@Service
public class UserService implements IUserService{
	private static final Logger LOGGER = LogManager.getLogger(UserService.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User addUser(User user){
		return userRepository.save(user);
	}
	
	@Override
	public void deleteUser(User user){
		userRepository.delete(user);
	}

	@Override
	public void deleteUserById(Integer id) {
		userRepository.delete(id);		
	}

	@Override
	public void updateUser(User user) {
		userRepository.saveAndFlush(user);
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public User findById(Integer id) {
		return userRepository.findOne(id);
	}

	@Override
	public User getUser(String account, String password) {
		return userRepository.findByAccountAndPassword(account,password);
	}

	@Override
	public User findByAccount(String account) {
		return userRepository.findByAccount(account);
	}

	@Override
	public User findByIdOrAccountOrTel(Integer id, String account, String tel) {
		return userRepository.findByIdOrAccountOrTel(id,account,tel);
	}
}
