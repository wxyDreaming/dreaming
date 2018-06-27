package com.wuxinyu.enumeration;

/**
 * 使用方法 enumTest.userName.getId/getName
 * @author wxy
 *
 */
public enum enumTest {
	userName("张三",1),
	userAge("李四",2);
	
	private String name;
	private int id;
	
	private enumTest(String name, int id) {
		this.name = name;
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
