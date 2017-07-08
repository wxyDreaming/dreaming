package com.wuxinyu.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Eleave properties 文件的读写
 * @author ZX
 * @date 2014-04-10
 */
public class PropertiesUtils {

	public static final String EMAIL_LOG_ADDRESS="emaillog.address";
	
    private static Properties propertie;
    
    private static PropertiesUtils instince = null;
    
    static{
    	InputStream  inputStream = PropertiesUtils.class.getResourceAsStream("/eleave.properties");
    	try {
    		propertie = new Properties();
			propertie.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    private  PropertiesUtils(){
        
    }
    
    public synchronized static PropertiesUtils getInstance(){
    	if(instince == null){
    		instince = new PropertiesUtils();
    	}
    	return instince;
    }
    
    /**
     * 读取properties值
     * @param key
     * @return
     */
    public String getProperty(String key) {
    	String value = "";
    	if(propertie!=null && propertie.containsKey(key)){
    		value = propertie.getProperty(key);
    	}
    	return value;
	}
    
    /**
     * 更新或新增properties信息
     * @param key
     * @return
     */
    public void setValue(String key, String value){
        propertie.setProperty(key, value);
    }
    

}
