package com.wuxinyu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.wuxinyu.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>,JpaSpecificationExecutor<User> {

	User findByAccountOrTel(String account,String tel);

	User findByIdOrAccountOrTel(Integer id, String account, String tel);
	
}
