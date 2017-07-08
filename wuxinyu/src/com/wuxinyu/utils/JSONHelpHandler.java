package com.wuxinyu.utils;

import java.util.LinkedHashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.helpers.MessageFormatter;

/**
 * Json help
 * @author fangkeliu
 * @since 2016-04-25
 * @version 1.0.0
 *
 */
public class JSONHelpHandler {

	/**
	 * 返回状态
	 */
	private static final String JSON_CODE = "statusCode";
	
	/**
	 * 返回数据
	 */
	private static final String JSON_DATA = "data";
	
	/**
	 * 返回成功
	 */
	private static final String JSON_RESULT_SUCESS = "200";
	
	/**
	 * 返回失败
	 */
	private static final String JSON_RESULT_FAIL = "300";
	
	/**
	 * 返回提示
	 */
	private static final String JSON_MESSAGE = "message";
	
	/**
	 * 成功的数据
	 * @param data
	 * @return
	 */
	public static JSONObject sucessMap(Map<String,Object> data){
		
		Map<String, Object> jsonData = new LinkedHashMap<String, Object>();
		jsonData.put(JSON_CODE, JSON_RESULT_SUCESS);
		jsonData.put(JSON_DATA, data);
		
		return JSONObject.fromObject(jsonData);
	}
	
	/**
	 * 成功的数据
	 * @param data
	 * @return
	 */
	public static JSONObject sucessJson(JSONObject json){
		
		Map<String, Object> jsonData = new LinkedHashMap<String, Object>();
		jsonData.put(JSON_CODE, JSON_RESULT_SUCESS);
		jsonData.put(JSON_DATA, json);
		
		return JSONObject.fromObject(jsonData);
	}
	
	/**
	 * 返回成功
	 * @return
	 */
	public static JSONObject sucess(){
		Map<String, Object> jsonData = new LinkedHashMap<String, Object>();
		jsonData.put(JSON_CODE, JSON_RESULT_SUCESS);
		
		return JSONObject.fromObject(jsonData);
	}
	
	/**
	 * 返回成功
	 * @return
	 */
	public static JSONObject sucess(String msg,Object... obj){
		
		Map<String, Object> jsonData = new LinkedHashMap<String, Object>();
		if(obj.length >0){
			msg = MessageFormatter.arrayFormat(msg, obj).getMessage();
			jsonData.put(JSON_MESSAGE, msg);
		}
		
		jsonData.put(JSON_CODE, JSON_RESULT_SUCESS);
		
		return JSONObject.fromObject(jsonData);
	}
	
	/**
	 * 错误的数据
	 * @param data
	 * @return
	 */
	public static JSONObject fail(String msg){
		
		Map<String, Object> jsonData = new LinkedHashMap<String, Object>();
		jsonData.put(JSON_CODE, JSON_RESULT_FAIL);
		jsonData.put(JSON_MESSAGE, msg);
		
		return JSONObject.fromObject(jsonData);
	}
	
	/**
	 * 错误的数据
	 * @param data
	 * @return
	 */
	public static JSONObject fail(String msg,Object... obj){
		
		if(obj.length >0) {
			msg = MessageFormatter.arrayFormat(msg, obj).getMessage();
		}
		
		Map<String, Object> jsonData = new LinkedHashMap<String, Object>();
		jsonData.put(JSON_CODE, JSON_RESULT_FAIL);
		jsonData.put(JSON_MESSAGE, msg);
		
		return JSONObject.fromObject(jsonData);
	}
	
	/**
	 * 表格或者树的数据
	 * @param data
	 * @return
	 */
	public static JSONObject data(JSONArray array){
		Map<String, Object> jsonData = new LinkedHashMap<String, Object>();
		
		jsonData.put(JSON_DATA , array);
		jsonData.put(JSON_CODE, JSON_RESULT_SUCESS);
		
		return  JSONObject.fromObject(jsonData);
	}
}
