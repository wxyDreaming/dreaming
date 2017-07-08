package com.wuxinyu.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wuxinyu.entity.User;

public interface UserRepository extends JpaRepository<User, Serializable>{

	User findByAccountAndPassword(String account, String password);

	User findByAccount(String account);

	User findByIdOrAccountOrTel(Integer id, String account, String tel);
	
}
