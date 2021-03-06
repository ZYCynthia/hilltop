package com.hilltop.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串操作通用类
 * 
 * @author sunjun
 * @version v5.0
 */
public class StringUtil {

	// 字符串常量枚举
	public static enum REGEX_ENUM {
		EMAIL("^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$"), CHINESE_CHARACTER(
				"[\\u4E00-\\u9FA5]+");
		private String value;

		private REGEX_ENUM(String value) {
			this.value = value;
		}

		public String toString() {
			return this.value;
		}
	};

	/**
	 * 检查字符串str是否匹配正则表达式regex
	 * 
	 * @param regex
	 * @param str
	 * @return
	 */
	public static boolean matcherRegex(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	/**
	 * 是否为汉字
	 * 
	 * @param ch
	 * @return
	 */
	public static boolean isChineseCharacter(char ch) {
		return matcherRegex(REGEX_ENUM.CHINESE_CHARACTER.toString(), String.valueOf(ch));
	}

	/**
	 * 按字节截取字符串
	 * 
	 * @param str
	 *            要截取的字符串
	 * @param byteLength
	 *            长度
	 * @return 结果字符串
	 */
	public static String subString(String str, int byteLength) {
		if (isBlank(str))
			return "";
		if (str.getBytes().length <= byteLength)
			return str;
		if (str.length() >= byteLength)
			str = str.substring(0, byteLength);
		int readLen = 0;
		String c = null;
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < str.length(); i++) {
			c = String.valueOf(str.charAt(i));
			readLen += c.getBytes().length;
			if (readLen > byteLength)
				return sb.toString();
			sb.append(c);
		}
		return sb.toString();
	}

	/**
	 * 检查字符串长度是否在指定长度范围内(minLength<=str.length<=maxLength)
	 * 
	 * @param str
	 *            要检查的字符串
	 * @param minLength
	 *            最小长度
	 * @param maxLength
	 *            最大长度
	 * @return boolean 字符串长度在指定长度范围内返回true，否则返回false
	 */
	public static boolean checkLength(String str, int minLength, int maxLength) {
		if (isBlank(str))
			return false;
		int len = str.length();
		if (minLength == 0)
			return len <= maxLength;
		else if (maxLength == 0)
			return len >= minLength;
		else
			return (len >= minLength && len <= maxLength);
	}

	/**
	 * 按UTF-8编码来解码字符串
	 * 
	 * @param str
	 *            要解码的字符串
	 * @return String 解码str后字符串
	 */
	public static String decodeString(String str) {
		return decodeString(str, "UTF-8");
	}

	/**
	 * 按指定编码来解码字符串
	 * 
	 * @param str
	 * @param encoding
	 * @return
	 */
	public static String decodeString(String str, String encoding) {
		if (isBlank(str))
			return "";
		try {
			return URLDecoder.decode(str.trim(), encoding);
		} catch (UnsupportedEncodingException e) {
		}
		return "";
	}

	/**
	 * 按指定编码来解码字符串
	 * 
	 * @param str
	 * @param encoding
	 * @return
	 */
	public static String decodeURI(String str) {
		if (isBlank(str))
			return "";
		try {
			return new String(str.getBytes("ISO8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		return "";
	}

	/**
	 * 按UTF-8编码来编码字符串
	 * 
	 * @param str
	 *            要编码的字符串
	 * @return String 编码str后字符串
	 */
	public static String encodeString(String str) {
		return encodeString(str, "UTF-8");
	}

	/**
	 * 按UTF-8编码来编码字符串
	 * 
	 * @param str
	 *            要编码的字符串
	 * @return String 编码str后字符串
	 */
	public static String encodeString(String str, String encoding) {
		if (isBlank(str))
			return "";
		try {
			return URLEncoder.encode(str.trim(), encoding);
		} catch (UnsupportedEncodingException e) {
		}
		return "";
	}

	/**
	 * 根据时间得到唯一字符串
	 * 
	 * @return
	 */
	public static String getOnlyString() {
		return String.valueOf(System.currentTimeMillis());
	}

	/**
	 * 检查对象obj是否为空
	 * 
	 * @param str
	 *            要检查的字符串
	 * @return boolean str为空返回true，否则返回false
	 */
	public static boolean isBlank(Object obj) {
		if (obj == null)
			return true;
		if (obj instanceof String && obj.toString().trim().length() == 0)
			return true;
		return false;
	}

	/**
	 * 检查字符串str是否为整型
	 * 
	 * @param str
	 *            要检查的字符串
	 * @return boolean str为整型返回true，否则返回false
	 */
	public static boolean isInteger(String str) {
		if (isBlank(str))
			return false;
		try {
			Integer.parseInt(str.trim());
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * 检查字符串str是否为长整型
	 * 
	 * @param str
	 *            要检查的字符串
	 * @return boolean str为长整型返回true，否则返回false
	 */
	public static boolean isLong(String str) {
		if (isBlank(str))
			return false;
		try {
			Long.parseLong(str.trim());
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * 检查字符串str是否为布尔型
	 * 
	 * @param str
	 *            要检查的字符串
	 * @return boolean str为布尔型返回true，否则返回false
	 */
	public static boolean isBoolean(String str) {
		if (isBlank(str))
			return false;
		try {
			Boolean.parseBoolean(str.trim());
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * 检查字符串str是否为double类型
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isDouble(String str) {
		if (isBlank(str))
			return false;
		try {
			Double.parseDouble(str.trim());
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * 检查字符串str是否为时间型
	 * 
	 * @param str
	 *            要检查的字符串
	 * @return boolean str为时间型返回true，否则返回false
	 */
	public static boolean isDate(String str) {
		if (isBlank(str))
			return false;
		try {
			java.sql.Date sqlDate = java.sql.Date.valueOf(str.trim());
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * 检查对象数组strings的每个元素是否为空
	 * 
	 * @param objs
	 *            要检查的对象数组
	 * @return boolean objs数组元素为空返回true，否则返回false
	 */
	public static boolean isBlanks(Object... objs) {
		for (Object obj : objs) {
			if (StringUtil.isBlank(obj))
				return true;
		}
		return false;
	}

	/**
	 * 检查字符串数组str是否为长整型数组
	 * 
	 * @param str
	 *            要检查的字符串
	 * @return boolean str为长整型数组返回true，否则返回false
	 */
	public static boolean isLongs(String str[]) {
		for (int i = 0; i < str.length; i++) {
			if (!isLong(str[i]))
				return false;
		}
		return true;
	}

	/**
	 * 检查字符串数组str是否为整型数组
	 * 
	 * @param str
	 *            要检查的字符串
	 * @return boolean str为整型数组返回true，否则返回false
	 */
	public static boolean isIntegers(String str[]) {
		for (int i = 0; i < str.length; i++)
			if (!isInteger(str[i]))
				return false;
		return true;
	}

	/**
	 * 检查字符串数组str是否为布尔型数组
	 * 
	 * @param str
	 *            要检查的字符串
	 * @return boolean str为布尔型数组返回true，否则返回false
	 */
	public static boolean isBooleans(String str[]) {
		for (int i = 0; i < str.length; i++)
			if (!isBoolean(str[i]))
				return false;
		return true;
	}

	/**
	 * 检查字符串str是否为时间
	 * 
	 * @param str
	 *            要检查的字符串
	 * @return str为时间型返回true，否则返回false
	 */
	public static boolean isTimestamp(String str) {
		if (isBlank(str))
			return false;
		try {
			java.sql.Date d = java.sql.Date.valueOf(str.trim());
			return true;
		} catch (Exception ex) {
		}
		return false;
	}

	/**
	 * 检查字符串str是否为(yyyy-MM-dd HH:mm:ss)模式的时间
	 * 
	 * @param str
	 *            要检查的字符串
	 * @return str为时间型返回true，否则返回false
	 */
	public static boolean isFullTimestamp(String str) {
		if (isBlank(str))
			return false;
		try {
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			Date date = format.parse(str.trim());
			return date != null;
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * 将字符数组转换为长整型数组
	 * 
	 * @param str
	 *            字符数组
	 * @return Long[] 长整型数组
	 */
	public static Long[] stringsToLongs(String str[]) {
		Long lon[] = new Long[str.length];
		for (int i = 0; i < lon.length; i++)
			lon[i] = new Long(str[i]);
		return lon;
	}

	/**
	 * 将字符数组转换为整型数组
	 * 
	 * @param str
	 *            字符数组
	 * @return Integer[] 整型数组
	 */
	public static Integer[] stringsToIntegers(String str[]) {
		Integer array[] = new Integer[str.length];
		for (int i = 0; i < array.length; i++)
			array[i] = new Integer(str[i]);
		return array;
	}

	/**
	 * 将字符数组转换为布尔型数组
	 * 
	 * @param str
	 *            字符数组
	 * @return Boolean[] 布尔型数组
	 */
	public static Boolean[] stringsToBooleans(String str[]) {
		Boolean array[] = new Boolean[str.length];
		for (int i = 0; i < array.length; i++)
			array[i] = new Boolean(str[i]);
		return array;
	}

	/**
	 * 将字符数组转换为浮点型数组
	 * 
	 * @param str
	 *            字符数组
	 * @return double[] 浮点型数组
	 */
	public static double[] stringsToDoubles(String str[]) {
		double array[] = new double[str.length];
		for (int i = 0; i < array.length; i++)
			array[i] = Double.parseDouble(str[i]);
		return array;
	}

	/**
	 * 根据指定时间和格式字符串得到时间格式字符串
	 * 
	 * @param d
	 *            时间
	 * @param pattern
	 *            格式字符串
	 * @return String 时间格式字符串
	 */
	public static String formatDate(Date d, String pattern) {
		if (isBlank(d))
			return "";
		SimpleDateFormat format = new SimpleDateFormat(
				isBlank(pattern) ? "yyyy-MM-dd HH-mm-ss" : pattern);
		return format.format(d);
	}

	/**
	 * 根据时间字符串得到时间(yyyy-MM-dd)
	 * 
	 * @param str
	 *            时间字符串
	 * @return Timestamp 时间
	 */
	public static Timestamp getTimestamp(String str) {
		try {
			Date d = java.sql.Date.valueOf(str.trim());
			return new Timestamp(d.getTime());
		} catch (Exception ex) {
		}
		return null;
	}

	/**
	 * 根据时间字符串得到(yyyy-MM-dd HH-mm-ss)格式时间
	 * 
	 * @param str
	 *            时间字符串
	 * @return Timestamp 时间
	 */
	public static Timestamp getFullTimestamp(String str) {
		try {
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			Date date = format.parse(str.trim());
			return new Timestamp(date.getTime());
		} catch (Exception ex) {
		}
		return null;
	}

	/**
	 * 得到数字格式化后的字符串
	 * 
	 * @param number
	 *            Number类型
	 * @param minFractionDigits
	 *            小数最小位数
	 * @param maxFractionDigits
	 *            小数最大位数
	 * @return String 格式化后的字符串
	 */
	public static String formatNumber(Number number, int minFractionDigits,
			int maxFractionDigits) {
		NumberFormat format = NumberFormat.getInstance();
		format.setMinimumFractionDigits(minFractionDigits);
		format.setMaximumFractionDigits(maxFractionDigits);
		return format.format(number);
	}

	/**
	 * 字符串高亮<br>
	 * 解决了高亮前缀或高亮后缀在要高亮显示的字符串数组在存在时的问题，根据本算法可解决JS高亮显示时相同的问题
	 * 
	 * @param text
	 *            内容
	 * @param replaceStrs
	 *            要高亮显示的字符串数组
	 * @param beginStr
	 *            高亮前缀，如<font color=red>
	 * @param endStr
	 *            高亮后缀，如</font>
	 * @return
	 */
	public static String heightLight(String text, String[] replaceStrs,
			String beginStr, String endStr) {
		if (text.length() == 0)
			return text;
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < replaceStrs.length; i++) {
			String replaceStr = replaceStrs[i];
			int index = text.indexOf(replaceStr);
			if (index >= 0) {
				String afterStr = null;
				if (index > 0) {
					String beforeStr = text.substring(0, index);
					afterStr = text.substring(index + replaceStr.length());
					str.append(heightLight(beforeStr, replaceStrs, beginStr,
							endStr));
				} else
					afterStr = text.substring(replaceStr.length());
				str.append(beginStr).append(replaceStr).append(endStr);
				str.append(heightLight(afterStr, replaceStrs, beginStr, endStr));
				break;
			}
		}
		if (str.length() == 0)
			return text;
		return str.toString();
	}

	/**
	 * 替换指定的字符串数组为一个字符串<br>
	 * 速度比String.replaceAll快3倍左右，比apache-common StringUtils.replace快2倍左右
	 * 
	 * @param text
	 * @param replaceStrs
	 * @param newStr
	 * @return
	 */
	public static String replaceAll(String text, String[] replaceStrs,
			String newStr) {
		if (text.length() == 0)
			return text;
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < replaceStrs.length; i++) {
			String replaceStr = replaceStrs[i];
			int index = text.indexOf(replaceStr);
			if (index >= 0) {
				String afterStr = null;
				if (index > 0) {
					String beforeStr = text.substring(0, index);
					afterStr = text.substring(index + replaceStr.length());
					str.append(replaceAll(beforeStr, replaceStrs, newStr));
				} else
					afterStr = text.substring(replaceStr.length());
				str.append(newStr);
				str.append(replaceAll(afterStr, replaceStrs, newStr));
				break;
			}
		}
		if (str.length() == 0)
			return text;
		return str.toString();
	}

	/**
	 * 替换指定的字符串为一个字符串<br>
	 * 速度比String.replaceAll快3倍左右，比apache-common StringUtils.replace快2倍左右
	 * 
	 * @param text
	 * @param replaceStr
	 * @param newStr
	 * @return
	 */
	public static String replaceAll(String text, String replaceStr,
			String newStr) {
		if (text.length() == 0)
			return text;
		StringBuilder str = new StringBuilder();
		int index = text.indexOf(replaceStr);
		if (index >= 0) {
			String afterStr = null;
			if (index > 0) {
				String beforeStr = text.substring(0, index);
				afterStr = text.substring(index + replaceStr.length());
				str.append(replaceAll(beforeStr, replaceStr, newStr));
			} else
				afterStr = text.substring(replaceStr.length());
			str.append(newStr);
			str.append(replaceAll(afterStr, replaceStr, newStr));
		}
		if (str.length() == 0)
			return text;
		return str.toString();
	}

	/**
	 * 替换指定的字符串数组为一个字符串数组<br>
	 * 速度比String.replaceAll快3倍左右，比apache-common StringUtils.replace快2倍左右
	 * 
	 * @param text
	 * @param replaceStrs
	 * @param newStrs
	 * @return
	 */
	public static String replaceAllArray(String text, String[] replaceStrs,
			String[] newStrs) {
		if (text.length() == 0)
			return text;
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < replaceStrs.length; i++) {
			String replaceStr = replaceStrs[i];
			int index = text.indexOf(replaceStr);
			if (index >= 0) {
				String afterStr = null;
				if (index > 0) {
					String beforeStr = text.substring(0, index);
					afterStr = text.substring(index + replaceStr.length());
					str.append(replaceAllArray(beforeStr, replaceStrs, newStrs));
				} else
					afterStr = text.substring(replaceStr.length());
				str.append(newStrs[i]);
				str.append(replaceAllArray(afterStr, replaceStrs, newStrs));
				break;
			}
		}
		if (str.length() == 0)
			return text;
		return str.toString();
	}

	/**
	 * 解码HTML(将&gt;,&lt;,&quot;,&amp;转换成>,<,",& )
	 * 
	 * @param html
	 * @return
	 */
	public static String decodeHTML(String html) {
		if (isBlank(html))
			return "";
		String[] replaceStr = { "&amp;", "&lt;", "&gt;", "&quot;" };
		String[] newStr = { "&", "<", ">", "\"" };
		return replaceAllArray(html, replaceStr, newStr);
	}

	/**
	 * 编码HTML(将>,<,",&
	 * 转换成&gt;,&lt;,&quot;,&amp;)(高效率，来自FreeMarker模板源码，比replaceAll速度快很多)
	 * 
	 * @param html
	 * @return
	 */
	public static String encodeHTML(String html) {
		if (isBlank(html))
			return "";
		int ln = html.length();
		char c;
		StringBuffer b;
		for (int i = 0; i < ln; i++) {
			c = html.charAt(i);
			if (c == '<' || c == '>' || c == '&' || c == '"') {
				b = new StringBuffer(html.substring(0, i));
				switch (c) {
				case '<':
					b.append("&lt;");
					break;
				case '>':
					b.append("&gt;");
					break;
				case '&':
					b.append("&amp;");
					break;
				case '"':
					b.append("&quot;");
					break;
				}
				i++;
				int next = i;
				while (i < ln) {
					c = html.charAt(i);
					if (c == '<' || c == '>' || c == '&' || c == '"') {
						b.append(html.substring(next, i));
						switch (c) {
						case '<':
							b.append("&lt;");
							break;
						case '>':
							b.append("&gt;");
							break;
						case '&':
							b.append("&amp;");
							break;
						case '"':
							b.append("&quot;");
							break;
						}
						next = i + 1;
					}
					i++;
				}
				if (next < ln)
					b.append(html.substring(next));
				html = b.toString();
				break;
			}
		}
		return html;
	}

	/**
	 * MD5加密
	 * 
	 * @param plainText
	 *            要加密的字符串
	 * @return 加密后的字符串
	 */
	public static String Md5(String plainText) {
		StringBuffer buf = new StringBuffer("");
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();
			int i = 0;
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buf.toString();
	}

	/**
	 * MD5加密(32)
	 * 
	 * @param plainText
	 *            要加密的字符串
	 * @return
	 */
	public final static String MD5(String plainText) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = plainText.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
		}
		return "";
	}
}