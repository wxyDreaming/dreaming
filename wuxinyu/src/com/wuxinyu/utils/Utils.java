package com.wuxinyu.utils;

/*
 * Created on 2004-7-6
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author fangkeliu
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Utils {
	
	private static Logger log = Logger.getLogger(Utils.class);
	
	private static DecimalFormat dfN = new DecimalFormat("#,##0");
	private static DecimalFormat df2 = new DecimalFormat("#,##0.00");
	private static DecimalFormat df4 = new DecimalFormat("#,##0.0000");

	public static boolean isEmpty(String value) {
		return (value == null || value.trim().equals("")) ? true : false;
	}

	public static boolean isEmpty(Object[] objects) {
		return (objects == null || objects.length == 0) ? true : false;
	}

	/**
	 * Str 转换为 Unicode
	 * 
	 * @param str
	 * @return
	 */
	public static String H2U(String str) {
		StringBuffer sb = new StringBuffer("");
		for (int j = 0; j < str.length(); j++) {
			char c = str.charAt(j);
			int i = c;
			if (i > 128)
				sb.append("\\u").append(Integer.toHexString(i));
			else
				sb.append(c);
		}
		return sb.toString();
	}

	/**
	 * Unicode 转换为 str
	 * 
	 * @param str
	 * @return
	 */
	public static String U2H(String src) {
		StringBuffer sb = new StringBuffer(src);
		StringBuffer ret = new StringBuffer("");
		int index = sb.indexOf("\\u");
		if (index == -1) {
			return sb.toString();
		}
		while (index != -1) {
			ret.append(sb.substring(0, index));
			String str = sb.substring(index + 2, index + 6);
			int i = Integer.parseInt(str, 16);
			char ch = (char) i;
			ret.append(ch);
			sb.delete(0, index + 6);
			index = sb.indexOf("\\u");

			log.debug(ch);
		}
		if (sb.length() > 0)
			ret.append(sb);
		return ret.toString();
	}

	public static boolean isEmpty(Object value) {
		return (value == null || value.equals("")) ? true : false;
	}

	public static boolean isEmpty(final Collection<?> obj) {
		if (obj == null || obj.size() == 0)
			return true;
		return false;
	}

	/**
	 * Judge the Map whether empty true: empty false: not empty
	 * 
	 * @param map 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(final Map map) {
		if ((map == null) || (map.size() == 0)) {
			return true;
		}
		return false;
	}

	/**
	 * 判断传入 List<String> 数组 字符 是否为空
	 * 
	 * @param list
	 * @return
	 */
	public static boolean isEmpty(List<String> list) {
		if (list == null || list.size() == 0) {
			return true;
		}
		for (int i = 0; i < list.size(); i++) {
			if (StringUtils.isBlank(list.get(i))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * get the last <h1>div</h1> of the <h1>str</h1>
	 * 
	 * @param str
	 * @param div
	 * @return
	 */
	public static String getLastDivBeforeName(String str, String div) {
		int lastDot = str.lastIndexOf(div);
		if (lastDot == -1) {
			return "";
		} else {
			return str.substring(0, lastDot);
		}
	}

	/**
	 * ȡ���div������ַ�
	 * 
	 * @param str
	 * @param div
	 * @return
	 */
	public static String getLastDivAfterName(String str, String div) {
		int lastDot = str.lastIndexOf(div);
		if (lastDot == -1) {
			return "";
		} else {
			return str.substring(lastDot + 1, str.length());
		}
	}

	/**
	 * 取到Date 的时间
	 * 
	 * @param date
	 * @return
	 */
	public static String getTime(Date date) {
		if (date == null)
			return "00:00";
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int min = cal.get(Calendar.MINUTE);
		String time = (hour < 10 ? "0" + hour : "" + hour) + ":"
				+ (min < 10 ? "0" + min : "" + min);
		return time;
	}

	/**
	 * 判断字符串如：2012-03-11 能否转换成指定格式的日期，如果可以返回true,否则返回false
	 * 
	 * @param dateStr
	 * @return
	 */
	public static boolean validateStringIsDate(String dateStr) {
		if (!StringUtils.isNotEmpty(dateStr))
			return false;
		String regex = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(dateStr);
		boolean flag = matcher.matches();
		return flag;
	}

	/**
	 * 格式化一个时间
	 * 
	 * @param format
	 * @param date
	 * @return
	 */
	public static String formatDate(String format, Date date) {
		if (date == null)
			return "";
		String sDate = "";
		try {
			SimpleDateFormat df = new SimpleDateFormat(format);
			sDate = df.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sDate;
	}
	
	
	/**
	 * 格式化一个时间
	 * 
	 * @param format
	 * @param date
	 * @return
	 */
	public static String formatDate(String format, String date) {
		if (date == null)
			return "";
		String sDate = "";
		try {
			SimpleDateFormat df = new SimpleDateFormat(format);
			sDate = df.format(df.parse(date));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sDate;
	}

	/**
	 * 取到当前的时间 默认为 yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String getCurrentDate() {
		Calendar currentIns = Calendar.getInstance();
		Date date = currentIns.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}

	/**
	 * 按照格式返回当前时间
	 * 
	 * @param format
	 * @return
	 */
	public static String getCurrentDate(String format) {
		Calendar currentIns = Calendar.getInstance(Locale.CHINA);
		Date date = currentIns.getTime();
		SimpleDateFormat ft = new SimpleDateFormat(format);
		return ft.format(date);
	}

	/**
	 * 得到距离现在number天的时间 默认格式化
	 * 
	 * @param number
	 * @return
	 */
	public static String getPreDateMonth(int number) {

		Calendar currentIns = Calendar.getInstance(Locale.CHINA);
		currentIns.add(Calendar.MONTH, number);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(currentIns.getTime());

	}
	
	/**
	 * Description: 判断字符串是否包含内容
	 * @param str
	 * @return
	 */
	public static boolean hasText(String str)
	{
		if (org.apache.commons.lang.StringUtils.isEmpty(str))
			return false;
		str = str.replaceAll(" ", "");
		if ("null".equalsIgnoreCase(str.trim()))
			return false;
		return org.apache.commons.lang.StringUtils.isNotBlank(str);
	}

	/**
	 * 得到距离现在number天的时间
	 * 
	 * @param number
	 * @return
	 */
	public static String getPreDateMonth(int number, String format) {

		Calendar currentIns = Calendar.getInstance(Locale.CHINA);
		currentIns.add(Calendar.MONTH, number);
		SimpleDateFormat ft = new SimpleDateFormat(format);
		return ft.format(currentIns.getTime());

	}

	/**
	 * 格式化为String
	 * 
	 * @param str
	 * @return
	 */
	public static final String dealObject(Object obj) {
		return isEmpty(obj) ? "" : obj.toString().trim();
	}

	/**
	 * 格式化为String
	 * 
	 * @param str
	 * @return
	 */
	public static final String dealString(String str) {
		return isEmpty(str) ? "" : str.trim();
	}

	/**
	 * 格式化为String,如果为空 返回默认的defaultString
	 * 
	 * @param str
	 * @param defaultString
	 * @return
	 */
	public static final String dealString(String str, String defaultString) {
		return isEmpty(str) ? defaultString : str;
	}

	/**
	 * trim String if null return defaultString
	 * 
	 * @param str
	 * @param defaultString
	 *            ֵ
	 * @return
	 */
	public static final String trim(String str, String defaultString) {
		if (str == null)
			return defaultString;
		return str.trim();
	}

	/**
	 * 
	 * 转换String 为小写
	 * 
	 * @param str
	 * @return
	 */
	public static final String toLowcase(String str) {
		if (str == null)
			return null;
		return str.toLowerCase();
	}

	/**
	 * 转换String 为小写 如果为null 返回默认 defaultString
	 * 
	 * @param str
	 * @param defaultStringֵ
	 * @return
	 */
	public static final String toLowcase(String str, String defaultString) {
		if (str == null)
			return defaultString;
		return str.toLowerCase();
	}

	/**
	 * format str to a size
	 * 
	 * @param str
	 * @return
	 */
	public static final String toSizeString(String str, int size) {
		StringBuffer sb = new StringBuffer("");
		if (str != null)
			sb.append(trim(str));
		int len = sb == null ? 0 : sb.length();
		if (len >= size)
			sb.delete(size, len);
		else
			for (int i = 0; i < size - len; i++) {
				sb.append(" ");
			}
		return sb.toString();
	}

	public static final String toSizeString(Integer obj, int size) {
		StringBuffer sb = new StringBuffer("");
		if (obj != null)
			sb.append("" + obj.intValue());
		int len = sb.length();
		if (len >= size)
			sb.delete(size, len);
		else
			for (int i = 0; i < size - len; i++) {
				sb.insert(0, "0");
			}
		return sb.toString();
	}

	public static final String toSizeString(Double obj, int size) {
		StringBuffer sb = new StringBuffer("");
		if (obj != null) {
			NumberFormat nf = NumberFormat.getCurrencyInstance();
			sb.append(nf.format(obj).substring(1));
		}
		int len = sb.length();
		if (len >= size)
			sb.delete(size, len);
		else
			for (int i = 0; i < size - len; i++) {
				sb.insert(0, "0");
			}
		return sb.toString();
	}

	/**
	 * trim string
	 * 
	 * @param str
	 * @return
	 */
	public static final String trim(String str) {
		if (str == null)
			return null;
		return str.trim();
	}

	/**
	 * 
	 * @param str
	 * @return
	 */
	public static final String dealZoneNo(String zoneNo) {
		if (isEmpty(zoneNo))
			return zoneNo;
		zoneNo = trim(zoneNo);
		char firstWord = zoneNo.charAt(0);
		if (firstWord == '0' && zoneNo.length() > 0) {
			zoneNo = zoneNo.substring(1);
		}
		return zoneNo;
	}

	/**
	 * 
	 * @param str
	 * @return
	 */
	public static final String getZoneNo(String phone) {
		String zoneNo = "";
		if (isEmpty(phone))
			return zoneNo;
		phone = trim(phone);
		if (phone.length() < 9)
			return zoneNo;
		phone = dealZoneNo(phone);
		String _2thNum = phone.substring(0, 2);
		if ("13".equalsIgnoreCase(_2thNum))
			return zoneNo;

		char c = phone.charAt(0);

		if (c == '1' || c == '2') {
			zoneNo = phone.substring(0, 2);
		} else {
			zoneNo = phone.substring(0, 3);
		}
		return zoneNo;
	}

	/**
	 * 
	 * @param str
	 * @return
	 */
	public static final String trimAndLowcase(String str) {

		return toLowcase(trim(str));
	}

	/**
	 * 
	 * @param str
	 * @param defaultStringֵ
	 * @return
	 */
	public static final String trimAndLowcase(String str, String defaultString) {
		return toLowcase(trim(str), defaultString);
	}

	/**
	 * @param b
	 * @return
	 */
	public static boolean toBoolean(String b) {
		boolean t = false;
		if (b.toLowerCase().equals("true") || b.equals("1")
				|| b.toLowerCase().equals("yes")
				|| b.toLowerCase().endsWith("y")) {
			t = true;
		}
		return t;
	}

	/**
	 * @param str
	 * @return
	 */
	public static int toInt(String str) {
		return toInt(str, 0);
	}

	/**
	 * @param str
	 * @return
	 */
	public static int countStr(String src, String str) {
		if (src == null || str == null)
			return 0;
		int i = 0;
		int pos = 0;
		while ((pos = src.indexOf(str, pos)) != -1) {
			i++;
			pos = pos + str.length();
		}
		return i;
	}

	/**
	 * @param str
	 * @param i
	 * @return
	 */
	public static int toInt(String str, int i) {
		int result = 0;
		try {
			result = java.lang.Integer.parseInt(str);
		} catch (Exception ex) {
			result = i;
		}
		return result;
	}

	public static Calendar toCalendar(String sdate, String delimate) {
		Calendar returnValue = null;

		if (sdate == null || sdate.trim().length() == 0 || delimate == null
				|| delimate.trim().length() < 1) {
			return returnValue;
		}

		java.util.StringTokenizer st = new java.util.StringTokenizer(sdate,
				delimate);
		try {
			int y = 0, m = 0, d = 0;
			if (st.hasMoreTokens()) {
				y = new Integer(st.nextToken()).intValue();
			}
			if (st.hasMoreTokens()) {
				m = new Integer(st.nextToken()).intValue();
			}
			if (st.hasMoreTokens()) {
				d = new Integer(st.nextToken()).intValue();
			}
			returnValue = Calendar.getInstance();
			returnValue.set(y, m - 1, d, 0, 0, 0);
		} catch (Exception e) {
		}
		return returnValue;

	}

	/**
	 * 
	 * @param source
	 * @param delim
	 * @return
	 */
	public static String[] split(String source, String delim) {
		int i = 0;
		int l = 0;
		if (source == null || delim == null) {
			return new String[0];
		}
		if (source.equals("") || delim.equals("")) {
			return new String[0];
		}

		StringTokenizer st = new StringTokenizer(source, delim);
		l = st.countTokens();
		String[] s = new String[l];
		while (st.hasMoreTokens()) {
			s[i++] = st.nextToken().trim();
		}
		return s;
	}

	/**
	 * 
	 * @param source
	 * @param delim
	 * @return
	 */
	public static String combo(String[] source, String delim) {
		if (source == null || source.length < 1)
			return "";
		if (delim == null || delim.trim().equals(""))
			delim = ",";
		String tmp = source[0];
		for (int i = 1; i < source.length; i++) {
			tmp += delim + source[i];
		}
		return tmp;
	}

	/**
	 * @param source
	 * @return
	 */
	public static String getUpperCase(String source) {
		if (source == null)
			return "";
		String tmp = "";
		for (int i = 0; i < source.length(); i++) {
			if ((source.charAt(i) >= 'A' && source.charAt(i) <= 'Z')
					|| (source.charAt(i) >= '0' && source.charAt(i) <= '9')) {
				tmp += source.charAt(i);
			}

		}
		return tmp;
	}

	/**
	 * 
	 * @param source
	 * @param delim1
	 * @param delim2
	 * @return
	 */
	public static Map<String,String> split(String source, String delim1, String delim2) {
		Map<String,String> map = new HashMap<String,String>();
		String[] sv = split(source, delim1);
		for (int i = 0; i < sv.length; i++) {
			String[] tmp = split(sv[i], delim2);
			map.put(tmp[0].trim().toLowerCase(), tmp[1].trim().toLowerCase());
		}
		return map;
	}

	/**
	 * @param key
	 * @param value
	 * @return
	 */
	public static String getKeyValue(String key, String value) {
		return key + (value == null ? "" : ("=" + value)) + "\r\n";
	}

	/**
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean equalStr(String str1, String str2) {
		if (str1 == null && str2 == null)
			return true;
		if (str1 == null || str2 == null)
			return false;
		if (str1.trim().equals(str2.trim()))
			return true;
		else
			return false;
	}

	/**
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean equalObject(Object obj1, Object obj2) {
		if (obj1 == null && obj2 == null)
			return true;
		if (obj1 == null || obj2 == null)
			return false;
		if (obj1.equals(obj2))
			return true;
		else
			return false;
	}

	@SuppressWarnings("unused")
	private static String replaceAllToken(final String src, final char token,
			final String rep) {
		StringBuffer sb = new StringBuffer(src);
		int index = sb.toString().indexOf('?');
		while (index != -1) {
			sb.delete(index, index + 1);
			sb.insert(index, rep.trim());
			index = sb.toString().indexOf('?');
		}
		return sb.toString();
	}

	public static Double toDouble(String str) {
		Double result = new Double(0);
		if (str == null || str.trim().equals(""))
			return result;
		try {
			result = java.lang.Double.valueOf(str.trim());
		} catch (Exception ex) {

		}
		return result;
	}

	public static Date toShortDate(String sdate) {
		return toDate(sdate, "yyyy-MM-dd");
	}

	public static Date toDate(String sdate, String format) {
		if (isEmpty(sdate))
			return null;
		SimpleDateFormat df = new SimpleDateFormat(format);
		Date ret = null;
		try {
			ret = (Date) df.parseObject(sdate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * @param str
	 * @return
	 */
	public static final String toSizeString(String str, int size,
			boolean fixedLength) {
		StringBuffer sb = new StringBuffer("");
		if (str == null) {
			str = "";
		}
		sb.append(trim(str));
		int len = sb.toString().getBytes().length; // added by Fan Yuansheng
		if (len > size) {
			byte[] bs = sb.toString().getBytes();
			return new String(bs, 0, size);
		} else {
			if (fixedLength) {
				for (int i = 0; i < size - len; i++) {
					sb.append(" ");
				}
			}
			return sb.toString();
		}
	}

	/**
	 * 将double保留N位小数(N必须在1~8之间)
	 */
	public static double retainDouble(double a, int n) {
		if ((int) (a * 1000000000) == 0) {
			return 0.0;
		}
		StringBuffer sb = new StringBuffer("#.");
		if (n <= 0 || n > 8) {
			sb.append("00");
		} else {
			for (int i = 0; i < n; i++)
				sb.append("0");
		}
		DecimalFormat df = new DecimalFormat(sb.toString());
		a = Double.parseDouble(df.format(a));
		return a;
	}

	/**
	 * 传两个Double,以及需要保留几位小数，用第一个比上第二个，返回保留N(1到8位之间)位小数的结果
	 * 
	 * @param a
	 * @param b
	 * @return double
	 */
	public static double divisionRetainDouble(double a, double b, int n) {
		if (a == 0)
			return retainDouble(0, n);
		if (b == 0)
			return retainDouble(a, n);
		return retainDouble(a / b, n);
	}

	/**
	 * 保留整数部分
	 * @param num
	 * @return
	 */
	public static String formatStringNum(String num){
		return dfN.format(Double.parseDouble(num));
	}
	/**
	 * 保留2位小数
	 * @param num
	 * @return
	 */
	public static String formatStringNum2(String num){
		return df2.format(Double.parseDouble(num));
	}
	/**
	 * 保留4位小数
	 * @param num
	 * @return
	 */
	public static String formatStringNum4(String num){
		return df4.format(Double.parseDouble(num));
	}
	/**
	 * 传两个Double,用第一个比上第二个，返回百分比字符串(精确到XX.XX%)
	 * 
	 * @param a
	 * @param b
	 * @return String
	 */
	public static String calcPercentage(double a, double b, int n) {
		String s1 = ((float)divisionRetainDouble(a, b, n) * 100) + "";
		for (int i = s1.length() - 1; i >= 0; i--) {
			if (s1.charAt(i) == '0') {
				s1 = s1.substring(0, i);
			} else {
				break;
			}
		}
		if (s1.charAt(s1.length() - 1) == '.')
			s1 = s1.substring(0, s1.length() - 1);
		String result = s1 + "%";
		return result;
	}
	
	/**
	 * 传两个Double,用第一个比上第二个，返回百分比字符串(XX%)
	 * 
	 * @param a
	 * @param b
	 * @return String
	 */
	public static String calcPercentage(double a, double b) {
		int c = (int) ((a/b)*100);
		return c + "%";
	}

	/**
	 * 将 例如EEL/dean.cai中的loginName取出
	 * 
	 * @param fullName
	 *            完整的用户名
	 * @param s
	 *            分隔符
	 * @return 登录名
	 */
	public static String convertLoginName(String fullName, String s) {
		if(isEmpty(fullName))return fullName;
		if (fullName.indexOf(s) != -1) {
			String[] tmp = fullName.split(s);
			return tmp[tmp.length - 1];
		} else {
			return fullName;
		}

	}
	
	/**
	 * 返回指定日期的前后多少天的日期。
	 * eg:day为正数，就是指当前日期加day天的日期。如date为2012-08-01，day=1;返回的日期为2012-08-02
	 * eg:day为负数，就是指当前日期减day天的日期。如date为2012-08-10，day=-1;返回的日期为2012-08-09
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date addDays(Date date,int day){
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, day);
		return cal.getTime();
	}
	
	
	/**
	 * windows文件夹或文件中不允许使用的字符
	 */
	public final static String[] windowFileParams = new String[]{"/","\\\\","<",">",":","\\*","\\?","\""};
	/**
	 * 将src中包含param的字符串统一替换成指定字符串
	 * @param src
	 * @param param
	 * @return
	 */
	public static String replaceStr(String src,String[] param,String newChar){
		if(param==null || param.length<=0) return src;
		for (int i = 0; i < param.length; i++) {
			String rep = param[i];
			src=src.replaceAll(rep, newChar);
		}
		return src;
	}

	/**
	 * 生成不重复的10位的随机数
	 * @return
	 */
	public static String createRadomNum(){
		long time =System.currentTimeMillis();
	    final int maxNum = 36;
	    int i;
	    int count = 0;
	    char[] str = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	    StringBuffer num = new StringBuffer("");
	    java.util.Random r = new java.util.Random();
	    while(count < 1){
	     i = Math.abs(r.nextInt(maxNum)); //生成的数最大为36-1
	     if (i >= 0 && i < str.length) {
	      num.append(str[i]);
	      count ++;
	     }
	    }
	   	return String.valueOf(time)+num.toString();
	}
	
	/**
	 * 生成批量订单的唯一编号
	 * 10位的随机数
	 * @return
	 */
	public static String createBatchOrderNo(){
		long time =System.currentTimeMillis();
	    final int maxNum = 36;
	    int i;
	    int count = 0;
	    char[] str = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	    StringBuffer num = new StringBuffer("");
	    java.util.Random r = new java.util.Random();
	    while(count < 1){
	     i = Math.abs(r.nextInt(maxNum)); //生成的数最大为36-1
	     if (i >= 0 && i < str.length) {
	      num.append(str[i]);
	      count ++;
	     }
	    }
	   	return String.valueOf(time)+num.toString();
	}
	
	
	
	static String regx = "^((-?\\d+.?\\d*)[Ee]{1}(-?\\d+))$";//科学计数法正则表达式
    static Pattern pattern = Pattern.compile(regx);
    
    /**
     * 判断输入字符串是否为科学计数法
     * @param input
     * @return
     */
    public static boolean isKeXueNum(String input){
        return pattern.matcher(input).matches();
    }

    /**
     * 获取客户要求的成本价;
     * 0.0000显示0,0.1200 显示0.12
     * @param price
     */
	public static String getNcoFormatPrice(String price) {
		if(!isEmpty(price)){
			double priceD = Double.parseDouble(price);
			if(priceD==0){
				return "0";
				//后面是否有多个0
			}else {
				return priceD+"";
			}
		}else{
			return "0";
		}
	}

	/**
	 * 判断传入集合的字符是否是数字类型
	 * 
	 * @param data
	 */
	public static boolean isNumber(List<String> data) {
		for (int i = 0; i < data.size(); i++) {
			if (StringUtils.isNotBlank(data.get(i))) {
				Pattern pattern = Pattern.compile("[0-9]*");
				Matcher isNum = pattern.matcher(data.get(i));
				if (!isNum.matches()) {
					return false;
				}
			} else {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断传入字符串是否是数字类型
	 * 
	 * @param data
	 */
	public static boolean isNumber(String data) {
		if (StringUtils.isNotBlank(data)) {
			Pattern pattern = Pattern.compile("[0-9]*");
			Matcher isNum = pattern.matcher(data);
			if (isNum.matches()) {
				return true;
			}
		}
		return false;
	}
    
}
