package com.wuxinyu.utils;

public class ToHtmlUtils {

	
	/**
	 * 转换内容为HTML内容
	 * @param content
	 * @return
	 */
	public static String toHtml(String content){
		
		StringBuilder html = new StringBuilder();
		html.append("<html><head></head><body>");
		html.append(content);
		html.append("</body></html>");
		return html.toString();
	}
}
