package com.wuxinyu.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wuxinyu.entity.User;
import com.wuxinyu.enumeration.Constant;
import com.wuxinyu.service.IUserService;
import com.wuxinyu.utils.DateUtil;
import com.wuxinyu.utils.RestResponse;

@RestController
@RequestMapping("/userController")
public class UserController {
	
	@Autowired
	private IUserService userService;
	
	@RequestMapping(value = "/register",method = RequestMethod.POST)
	public RestResponse registerUser(@RequestBody User user){
//		user.setAccount("wuxinyu");
//		user.setAge(20);
//		user.setCreateDate(DateUtil.getSystemTime());
//		user.setNickname("无心鱼");
//		user.setPassword("123");
//		user.setStatus(1);
		String account = user.getAccount();
		String passWord = user.getPassword();
		String nickName = user.getNickname();
		if(StringUtils.isEmpty(account) && StringUtils.isEmpty(passWord) && StringUtils.isEmpty(nickName)){
			return new RestResponse("请将信息填写完整",0);
		}
		user.setCreateDate(DateUtil.getSystemTime());
		user.setStatus(Constant.STATUS);
		userService.addUser(user);
		return new RestResponse();
	}
	
	@RequestMapping(value = "/updateUser",method = RequestMethod.POST)
	public RestResponse updateUser(User user){
		Integer id = user.getId();
		String account = user.getAccount();
		String tel = user.getTel();
		User user2 = userService.findByIdOrAccountOrTel(id, account, tel);
		if(user2 != null){
			if(!user.getAge().toString().isEmpty()){
				user2.setAge(user.getAge());
			}
			if(!user.getName().isEmpty()){
				user2.setName(user.getName());
			}
			if (!user.getNickname().isEmpty()) {
				user2.setNickname(user.getNickname());
			}
			if(!user.getSex().isEmpty()){
				user2.setSex(user.getSex());
			}
			if(!user.getQq().isEmpty()){
				user2.setQq(user.getQq());
			}
			if(!user.getPassword().isEmpty()){
				user2.setPassword(user.getPassword()); 
			}
//			user2.setTel(user.getTel()); 暂不开放修改手机号
			userService.updateUser(user2);
		}else {
			return new RestResponse("找不到该用户",0);
		}
		return new RestResponse();
	}
	
	@RequestMapping(value = "/isExist")
	public RestResponse isExist(String account){
		User user = userService.findByAccount(account);
		if(user != null){
			return new RestResponse("该用户已存在",0);
		}
		return new RestResponse();
	}
	
	
}
