package com.wuxinyu.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.lang3.StringUtils;

/**
 * 关于异常的工具类.
 * 
 */
public class Exceptions {

	/**
	 * 将CheckedException转换为UncheckedException.
	 */
	public static RuntimeException unchecked(Exception e) {
		if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		} else {
			return new RuntimeException(e);
		}
	}

	/**
	 * 将ErrorStack转化为String.
	 */
	public static String getStackTraceAsString(Exception e) {
		StringWriter stringWriter = new StringWriter();
		e.printStackTrace(new PrintWriter(stringWriter));
		return stringWriter.toString();
	}

	/**
	 * 判断异常是否由某些底层的异常引起.
	 */
	public static boolean isCausedBy(Exception ex, Class<? extends Exception>... causeExceptionClasses) {
		Throwable cause = ex;
		while (cause != null) {
			for (Class<? extends Exception> causeClass : causeExceptionClasses) {
				if (causeClass.isInstance(cause)) {
					return true;
				}
			}
			cause = cause.getCause();
		}
		return false;
	}
	
	
	/**
	 * 得到exception的消息的文本部分
	 * @param e
	 * @return
	 */
	public static String getExceptionMsg(Exception e){
		
		String msg = e.getMessage();
		if(StringUtils.isEmpty(msg)){
			msg = e.getCause().getMessage();
		}
		
		if(StringUtils.isEmpty(msg)){
			msg = "未知错误,请联系管理员";
		}else{
			if(msg.indexOf(":")>-1){
				msg = StringUtils.substring(msg,msg.indexOf(":")+1);
			}
		}
		
		return msg;
	}
}
