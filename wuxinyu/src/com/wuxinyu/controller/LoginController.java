package com.wuxinyu.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wuxinyu.entity.User;
import com.wuxinyu.enumeration.Constant;
import com.wuxinyu.service.IUserService;
import com.wuxinyu.utils.RestResponse;

@RestController
@RequestMapping("/loginController")
public class LoginController {
	
	@Autowired
	private IUserService userService;
	
	@RequestMapping("/login")
	public RestResponse login(String account,String password,HttpServletRequest request,HttpServletResponse response){
		HttpSession session = request.getSession();
		if(StringUtils.isNotEmpty(account) && StringUtils.isNotEmpty(password)){
			User user = userService.getUser(account,password);
			if(user != null){
				if(user.getStatus() != 1){
					return new RestResponse("此用户被禁止登陆，请联系管理员",0);
				}
				
				ServletContext application = session.getServletContext();
				Map<String, User> userMap = (Map<String, User>)application.getAttribute(Constant.USERMAP);
				if(userMap == null){
					userMap = new HashMap<String, User>();
				}
				userMap.put(user.getAccount(), user);
				session.setAttribute(Constant.USER, user);
				application.setAttribute(Constant.USERMAP, userMap);
			}else {
				return new RestResponse("没有此用户",0);
			}
		}else{
			return new RestResponse("没有输入用户名或密码",0);
		}
		return new RestResponse();
	}
	
	@RequestMapping("/logout")
	public RestResponse logout(String account,HttpServletRequest request,HttpServletResponse response){
		HttpSession session = request.getSession();
		ServletContext application = session.getServletContext();
		Map<String, User> userMap = (Map<String, User>)application.getAttribute(Constant.USERMAP);
		userMap.remove(account);
		application.setAttribute(Constant.USERMAP, userMap);
		session.removeAttribute(Constant.USER);
		return new RestResponse();
	}
	
	
}
