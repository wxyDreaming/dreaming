package com.wuxinyu.utils;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.LogFactoryImpl;

public class JDBCUtil {
	
	
	private static Log log = LogFactoryImpl.getLog(JDBCUtil.class);
    private static String DRIVER = ""; 
    private static String URL = "";
    private static String username = "";
    private static String password = "";
	private static Properties properties = new Properties();
	
	static {
		FileInputStream in = null;
		String path = Thread.currentThread().getContextClassLoader().getResource("eleave.properties").getPath().replace("%20", " ");
		try{
			in = new FileInputStream(path);
			properties.load(in);
			in.close();
			DRIVER = properties.getProperty("jdbc.driverClassName");
			URL = properties.getProperty("jdbc.url");
			username = properties.getProperty("jdbc.username");
			password = properties.getProperty("jdbc.password");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	//打开数据库连接
	public static Connection  getConnection(){
		log.debug("JDBCUtil  getConnection start");
		Connection conn = null;
		try{
			//驱动加载
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL,username,password);
			conn.setAutoCommit(false);
		}catch(Exception e){
			e.printStackTrace();
		
		}
		log.debug("JDBCUtil  getConnection end");
		return conn;
	}
	
	//关闭数据库连接
	public static void closeConnection(Connection conn){
		log.debug("JDBCUtil  closeConnection start");
		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		log.debug("JDBCUtil  closeConnection end");
	}
}