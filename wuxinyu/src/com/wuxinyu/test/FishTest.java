package com.wuxinyu.test;

import java.util.UUID;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.wuxinyu.service.IUserService;

public class FishTest extends BaseJunit4Test{
	
	@Autowired
	private IUserService userService;
	
	public static void main(String[] args) {
		String id = UUID.randomUUID().toString().replaceAll("\\-", "");
		System.out.println(id);
		
		//mysql 生成UUID
//		SELECT replace(UUID(),"-","") AS uuid from DUAL 
	}
	
	
	@Test
	@Transactional   //标明此方法需使用事务  
    @Rollback(true)  //标明使用完此方法后事务不回滚,true时为回滚  
	public void insertBu(){
		System.out.println("111");
		userService.findAll();
	}
}
