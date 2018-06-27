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

import com.wuxinyu.entity.LogLog;
import com.wuxinyu.entity.User;
import com.wuxinyu.enumeration.Constant;
import com.wuxinyu.service.ILogLogService;
import com.wuxinyu.service.IUserService;
import com.wuxinyu.utils.DateUtil;
import com.wuxinyu.utils.RestResponse;

@RestController
@RequestMapping("/loginController")
public class LoginController {
	
	@Autowired
	private IUserService userService;
	@Autowired
	private ILogLogService logService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/login")
	public RestResponse login(String account,String password,HttpServletRequest request,HttpServletResponse response){
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		response.addHeader("Access-Control-Max-Age", "3600");
		response.addHeader("Access-Control-Allow-Headers", "x-requested-with");
		
		HttpSession session = request.getSession();
		if(StringUtils.isNotEmpty(account) && StringUtils.isNotEmpty(password)){
			User user = userService.getUser(account);
			if(user != null){
				if(!StringUtils.equals(password, user.getPassword())){
					return new RestResponse("用户名或密码错误",0);
				}
				if(user.getStatus() != 1){
					return new RestResponse("用户被禁止登陆，请联系管理员",0);
				}
				
				user.setLasttime(DateUtil.getSystemTime());
				ServletContext application = session.getServletContext();
				Map<String, User> userMap = (Map<String, User>)application.getAttribute(Constant.USERMAP);
				if(userMap == null){
					userMap = new HashMap<String, User>();
				}
				userMap.put(user.getAccount(), user);
//				session.setAttribute(Constant.USER, user);
				session.setAttribute("userId", user.getId());
				application.setAttribute(Constant.USERMAP, userMap);
				
				//登录日志
				LogLog logLog = new LogLog();
				String ip = getIpAddress(request);//用户ip
				logLog.setUser(user);
				logLog.setIp(ip);
				logLog.setLoginDate(DateUtil.getSystemTime());
				logService.save(logLog);
				
				return new RestResponse(user.getAccount());
			} else {
				return new RestResponse("该用户不存在",0);
			}
		}
		return new RestResponse("没有输入用户名或密码",0);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/logout")
	public RestResponse logout(String account,HttpServletRequest request,HttpServletResponse response){
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		response.addHeader("Access-Control-Max-Age", "3600");
		response.addHeader("Access-Control-Allow-Headers", "x-requested-with");
		
		HttpSession session = request.getSession();
		ServletContext application = session.getServletContext();
		Map<String, User> userMap = (Map<String, User>)application.getAttribute(Constant.USERMAP);
		userMap.remove(account);
		application.setAttribute(Constant.USERMAP, userMap);
		session.removeAttribute(Constant.USER);
		return new RestResponse();
	}
	
	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
