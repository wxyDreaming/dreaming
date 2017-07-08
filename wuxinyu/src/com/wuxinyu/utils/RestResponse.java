package com.wuxinyu.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;;

@JsonInclude(Include.NON_NULL)
public class RestResponse {
	
	private Integer code;
    private String message;
    private Object data;
    
    public RestResponse(){
        this.code = 1 ;
        this.message = "OK";
    }

    public RestResponse(String message, Object data) {
        this.message = message;
        this.data = data;
        this.code = 1;
    }

    public RestResponse(Object data) {
        this.message = "OK";
        this.data = data;
        this.code = 1;
    }

    public RestResponse(String message, Integer code, Object data) {
        this.message = message;
        this.code = code;
        this.data = data;
    }

    public RestResponse(String message,Integer code){
        this.message = message;
        this.code = code;
        this.data = null;
    }

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
    
}
