package com.hsj.kindeditor.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

/**
 * String帮助类
 * 
 * @author Cai zongyou
 */
public class StringUtil {

	private final static char[] DIGITS = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	private final static int DIGITS_0 = '0';

	private final static int DIGITSA9 = 'A' - '9' - 1;

	private final static String[] PRE_ZERO = { "", "0", "00", "000", "0000",
			"00000", "000000", "0000000" };

	public static Boolean checkDateFormat(String gs,String str){
		try{
			SimpleDateFormat sdf = new SimpleDateFormat(gs);
			sdf.parse(str);
			return true;
		}catch(Exception e){return false;}
	}
	
	
	/**
	 * 判断是不是一个合法的电子邮件地址
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email){
		if (isEmpty(email)) return false;    
//		Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配  
        Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配  
        Matcher m = p.matcher(email);  
        return m.matches();  
	}
	
	/**
	 * 返回异常的堆栈信息
	 */
	public static String throwableToString(Throwable t) {
		if (t == null)
			return "";
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		t.printStackTrace(pw);
		return sw.toString();
	}

	public static Boolean isBoolean(String str){
		try{
			Boolean.parseBoolean(str);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	public static Boolean isDouble(String str){
		if(!isEmpty(str)){
			try{
				Double.parseDouble(str);
				return true;
			}catch(Exception e){return false;}
		}else{
			return false;
		}
	}
	
	/**
	 * 解析 IP 地址为长整数
	 */
	public static long ip2number(String strIP) {
		if (strIP != null) {
			StringTokenizer st = new StringTokenizer(strIP, ".");
			if (st.countTokens() == 4) {
				long lResultIP = 0;
				while (st.hasMoreTokens()) {
					int iToken = 0;
					try {
						iToken = Integer.parseInt(st.nextToken().trim());
					} catch (Exception ex) {
						iToken = -1;
					}
					if (iToken < 0 || iToken > 255) {
						return -1L;
					}
					lResultIP = lResultIP << 8;
					lResultIP = lResultIP | iToken;
				}
				return lResultIP;
			}
		}
		return -1L;
	}

	/**
	 * 转化16进制字符串为整数
	 */
	public static int hex2int(String s) {
		int iResult = 0;
		int iLength = s.length() < 8 ? s.length() : 8;
		int iTemp = 0;
		for (int i = 0; i < iLength; i++) {
			iTemp = s.charAt(i) - DIGITS_0;
			if (iTemp > 9)
				iTemp = iTemp - DIGITSA9;
			iTemp &= 0xF;
			iResult <<= 4;
			iResult |= iTemp;
		}
		return iResult;
	}

	/**
	 * 格式化整数为16进制字符串
	 */
	public static String int2dec(int i, int digit) {
		String s = String.valueOf(i);
		int l = s.length();
		if (l < digit) {
			return PRE_ZERO[digit - l] + s;
		}
		if (l > digit) {
			return s.substring(0, digit);
		}
		return s;
	}

	/**
	 * 格式化整数为16进制字符串
	 */
	public static String int2hex(int j) {
		char[] buf = new char[8];
		for (int i = buf.length - 1; i >= 0; i--) {
			buf[i] = DIGITS[j & 0xF];
			j >>>= 4;
		}
		return new String(buf);
	}

	/**
	 * 把输入的字节树组，逐字节转化为其16进制的文本描述形式
	 * 
	 * @param buf
	 *            输入的字节树组
	 * @param off
	 *            需要转化的开始位置
	 * @param len
	 *            需要转化的结束位置
	 * @return 转化结果
	 */
	public final static String bytesToHex(byte[] buf, int off, int len) {
		char[] out = new char[len * 2];

		for (int i = 0, j = 0; i < len; i++) {
			int a = buf[off++];
			out[j++] = DIGITS[(a >>> 4) & 0X0F];
			out[j++] = DIGITS[a & 0X0F];
		}
		return (new String(out));
	}

	/**
	 * 检测字符串是否为null，或者trim()以后的长度是否为0。
	 * 
	 * @author <a href="mailto:myreligion@163.com">liu kaixuan</a>
	 * @date 2005-8-13
	 */
	public static boolean isEmpty(String s) {
		if (s == null)
			return true;
		if (s.trim().length() == 0)
			return true;
		return false;
	}

	public static boolean isEmpty(Object obj) {
		if (obj == null)
			return true;
		if (obj.toString().trim().length() == 0)
			return true;
		return false;
	}

	public static boolean isInteger(Object obj) {
		try {
			if (obj == null)
				return false;
			else if (obj.equals(""))
				return false;
			Integer.parseInt(obj.toString());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public final static char[] TRSServerReservedChars = { '=', '(', ')', '[',
			']', '，', '/', '@', '>', '<', '!', '&', '*', '^', '-', '+', '\'',
			'\\' };

	public final static char[] filterChars = { ',', '，', ';', '；', '"', '“',
			'”', '‘', '’', '=', '(', ')', '[', ']', '，', '/', '@', '>', '<',
			'!', '&', '*', '^', '-', '+', '\'', '\\' };

	/**
	 * 把TRS Search检索中关键字的保留字符去掉，免得出现问题。 <br>
	 * 在过滤关键词时不把?, % 转意。
	 * 
	 * @param keyword
	 *            要处理的关键字
	 * @return 如果keyword为null，返回""；否则返回保留字符被转意以后的串。
	 * @see #replace4TRS(String, boolean)
	 */
	public static String replace4TRS(String keyword) {
		return replace4TRS(keyword, false);
	}

	/**
	 * 把TRS Search检索中关键字的特殊字符换成空格，免得出现问题。<br>
	 * 关键字中的字符将会转换成空格。
	 * 
	 * @param keyword
	 *            要处理的关键字
	 * @param escapeSimilarSearchChar
	 *            是否把keyword中的 ?, % 转意。
	 * @return 如果keyword为null，返回""；否则返回保留字符被换成空格以后的串。
	 */
	public static String removeTRSChars(String keyword,
			boolean escapeSimilarSearchChar) {
		if (keyword == null)
			return "";
		StringBuffer bufKeywords = new StringBuffer(
				(int) (keyword.length() * 1.5));

		for (int i = 0; i < keyword.length(); i++) {
			char ch = keyword.charAt(i);
			boolean removed = false;

			for (int j = 0; j < filterChars.length; j++) {
				if (ch == filterChars[j]) {
					removed = true;
					break;
				}
			}

			if (!removed && escapeSimilarSearchChar) {
				if (ch == '%') {
					removed = true;
				} else if (ch == '?') {
					removed = true;
				}
			}
			if (!removed) {
				bufKeywords.append(ch);
			} else {
				bufKeywords.append(' ');
			}
		}

		return bufKeywords.toString();
	}

	/**
	 * 把TRS Search检索中关键字的保留字符去掉，免得出现问题。
	 * 
	 * @param keyword
	 *            要处理的关键字
	 * @param escapeSimilarSearchChar
	 *            是否把keyword中的 ?, % 转意。
	 * @return 如果keyword为null，返回""；否则返回保留字符被转意以后的串。
	 */
	public static String replace4TRS(String keyword,
			boolean escapeSimilarSearchChar) {
		if (keyword == null)
			return "";
		StringBuffer bufKeywords = new StringBuffer(
				(int) (keyword.length() * 1.5));

		for (int i = 0; i < keyword.length(); i++) {
			char ch = keyword.charAt(i);
			boolean escaped = false;

			for (int j = 0; j < TRSServerReservedChars.length; j++) {
				if (ch == TRSServerReservedChars[j]) {
					bufKeywords.append("\\");
					escaped = true;
					break;
				}
			}

			if (!escaped && escapeSimilarSearchChar) {
				if (ch == '%') {
					bufKeywords.append("\\");
				} else if (ch == '?') {
					bufKeywords.append("\\");
				}
			}

			bufKeywords.append(ch);
		}

		return bufKeywords.toString();
	}

	private static boolean[] xlstInvalidChars = new boolean[256];

	static {
		for (int i = 0; i < xlstInvalidChars.length; i++) {
			xlstInvalidChars[i] = false;
		}

		// 0-8
		for (int i = 0; i < 9; i++) {
			xlstInvalidChars[i] = true;
		}

		// 11 0B
		xlstInvalidChars[11] = true;

		// 14-31
		for (int i = 14; i < 32; i++) {
			xlstInvalidChars[i] = true;
		}

		xlstInvalidChars[127] = true;
	}

	/** 删掉xlst不支持的字符，然后返回原文 */
	public static String getXSLTFreeText(String text) {
		if (isEmpty(text))
			return null;

		StringBuffer sb = new StringBuffer(text.length() * 2);
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (c > 127) {
				sb.append(c);
			} else {
				if (!xlstInvalidChars[c]) {
					sb.append(c);
				}
			}
		}

		return sb.toString();
	}

	/**
	 * 将URL地址中给定的一个参数去掉。
	 * 
	 * @param queryString
	 *            将要处理的URL地址
	 * @param toescape
	 *            要删掉的参数名称。
	 */
	public static String getSubQueryString(String queryString, String toescape) {
		// String queryString = request.getQueryString() ;
		if (queryString == null)
			return "";

		int pos = queryString.indexOf(toescape);
		if (pos < 0)
			return queryString; // 不存在需要的数

		StringBuffer sb = new StringBuffer(128);
		int total = queryString.length();

		if (pos > 0) {
			int i = 0;
			while (i < pos) {
				sb.append(queryString.charAt(i));
				i++;
			}
		}

		while (pos < total && queryString.charAt(pos++) != '&') {
			;
		}
		while (pos < total) {
			sb.append(queryString.charAt(pos));
			pos++;
		}
		int sbl = sb.length();
		if (sbl > 0 && sb.charAt(sbl - 1) == '&') {
			sb.setLength(sbl - 1);
		}

		return sb.toString();
	}

	/**
	 * 把null变成""<br>
	 * 
	 * @author <a href="mailto:myreligion@163.com">liu kaixuan</a>
	 * @date 2003年7月28日,2003年8月3日
	 */
	public static String dealNull(String str) {
		String returnstr = null;
		if (str == null)
			returnstr = "";
		else
			returnstr = str;
		return returnstr;
	}

	/**
	 * 字符串替换函数
	 * 
	 * @author <a href="mailto:myreligion@163.com">liu kaixuan</a>
	 * @date 2003年7月28日,2003年8月3日
	 * @param str
	 *            原始字符串
	 * @param substr
	 *            要替换的字符
	 * @param restr
	 *            替换后的字符
	 * @return 替换完成的字符串
	 */
	public static String replaceString(String str, String substr, String restr) {
		String[] tmp = splitString(str, substr);
		String returnstr = null;
		if (tmp.length != 0) {
			returnstr = tmp[0];
			for (int i = 0; i < tmp.length - 1; i++)
				returnstr = dealNull(returnstr) + restr + tmp[i + 1];
		}
		return dealNull(returnstr);
	}

	/**
	 * 分割字串，一般情况下客户端要考虑把返回字符数组中每个字符串进行trim()<br>
	 * 本方法本身不会添加空格，不过也不会把前后空格删除。
	 * 
	 * @author <a href="mailto:myreligion@163.com">liu kaixuan</a>
	 * @date 2003年7月28日,2003年8月3日
	 * @param toSplit
	 *            原始字符串
	 * @param delimiter
	 *            分割字符串
	 * @return 字符串数组
	 */
	public static String[] splitString(String toSplit, String delimiter) {
		if (toSplit == null)
			return new String[0];

		int arynum = 0, intIdx = 0, intIdex = 0, div_length = delimiter
				.length();
		if (toSplit.compareTo("") != 0) {
			if (toSplit.indexOf(delimiter) != -1) {
				intIdx = toSplit.indexOf(delimiter);
				for (int intCount = 1;; intCount++) {
					if (toSplit.indexOf(delimiter, intIdx + div_length) != -1) {
						intIdx = toSplit
								.indexOf(delimiter, intIdx + div_length);
						arynum = intCount;
					} else {
						arynum += 2;
						break;
					}
				}
			} else
				arynum = 1;
		} else
			arynum = 0;

		intIdx = 0;
		intIdex = 0;
		String[] returnStr = new String[arynum];

		if (toSplit.compareTo("") != 0) {
			if (toSplit.indexOf(delimiter) != -1) {
				intIdx = (int) toSplit.indexOf(delimiter);
				returnStr[0] = (String) toSplit.substring(0, intIdx);
				for (int intCount = 1;; intCount++) {
					if (toSplit.indexOf(delimiter, intIdx + div_length) != -1) {
						intIdex = (int) toSplit.indexOf(delimiter, intIdx
								+ div_length);
						returnStr[intCount] = (String) toSplit.substring(intIdx
								+ div_length, intIdex);
						intIdx = (int) toSplit.indexOf(delimiter, intIdx
								+ div_length);
					} else {
						returnStr[intCount] = (String) toSplit.substring(intIdx
								+ div_length, toSplit.length());
						break;
					}
				}
			} else {
				returnStr[0] = (String) toSplit.substring(0, toSplit.length());
				return returnStr;
			}
		} else {
			return returnStr;
		}
		return returnStr;
	}

	/**
	 * 删除字符串中的多余空格。并且把字符串的前后空格删掉。
	 * 
	 * <pre>
	 *       例如把&quot;     &quot;变成&quot; &quot;，把制表符'\t'变成&quot; &quot;;
	 * </pre>
	 * 
	 * @author <a href="mailto:liu.kaixuan@trs.com.cn">liu kaixuan</a>
	 * @date 2005-8-11
	 */
	public static String squeezeWhiteSpace(String str) {
		str = dealNull(str);
		StringBuffer sb = new StringBuffer(str);
		StringBuffer sb2 = new StringBuffer();
		boolean flag = false;

		for (int i = 0; i < sb.length(); i++) {
			char c = sb.charAt(i);
			if (c != ' ' && c != '\t') {
				if (flag) {
					sb2.append(' ');
					flag = false;
				}
				sb2.append(c);
			} else {
				flag = true;
			}
		}
		return sb2.toString().trim();
	}

	public static int toInt(String s, int defaultValue) {
		try {
			return new Integer(s).intValue();
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * 把string转换成int
	 * 
	 * @param s
	 *            要转换成int的String
	 * @return 如果转换失败，返回-1
	 */
	public static int toInt(String s) {
		return toInt(s, -1);
	}

	/**
	 * 把string数组转换成int数组
	 * 
	 * @param s
	 *            要转换成int的String数组
	 * @return 如果转换失败，返回null
	 */
	public static int[] toIntArray(String[] strArray) {

		if (null == strArray || strArray.length <= 0)
			return null;

		int[] intArray = new int[strArray.length];
		for (int i = 0; i < strArray.length; i++) // 同时封多个版
		{
			int j = 0;
			try {
				j = Integer.valueOf(strArray[i]);
			} catch (Exception e) {
				return null;
			}
			if (j > 0)
				intArray[i] = j;
		}
		return intArray;
	}

	/**
	 * 分割字串，合并相同项
	 * 
	 * @param str
	 *            输入的字符串
	 * @param delimiter
	 *            分隔符
	 * @return set 分割并合并相同项后的字符串set
	 * @author li.wenying
	 */
	public static HashSet<String> uniqueSplitString(String str, String delimiter) {
		if (str == null || delimiter == null) {
			return null;
		}
		//
		HashSet<String> set = new HashSet<String>();
		StringTokenizer st = new StringTokenizer(str, delimiter);
		while (st.hasMoreTokens()) {
			String s = st.nextToken();
			set.add(s);
		}
		//
		if (set.size() == 0) {
			return null;
		}
		return set;
	}

	/**
	 * 比较两个字符串数组的差异
	 * 
	 * @param str
	 *            待比较的字符串
	 * @param originalStr
	 *            原字符串
	 * @return map 含有删除的和新增的字符串数组
	 * @author li.wenying
	 */
	public static Map compareStrArray(String str, String originalStr) {
		HashSet<String> addTag = new HashSet<String>();
		HashSet<String> newSet = uniqueSplitString(str, " ");

		String[] tempArray = new String[newSet.size()];
		newSet.toArray(tempArray);

		HashSet<String> originalSet = uniqueSplitString(originalStr, " ");
		for (int n = 0; n < newSet.size(); n++) {
			if (originalSet.contains(tempArray[n]))
				originalSet.remove(tempArray[n]);
			else
				addTag.add(tempArray[n]);
		}

		Map<String, String[]> map = new HashMap<String, String[]>();

		String[] addTags = new String[addTag.size()];
		addTag.toArray(addTags);
		String[] deleteTags = new String[originalSet.size()];
		originalSet.toArray(deleteTags);

		map.put("addTag", addTags);
		map.put("deleteTag", deleteTags);

		return map;
	}

	/**
	 * 重新构造用户的输入表达式，把多余的空格删掉。 把无用的字符删掉。
	 * 
	 * @param sw
	 *            要处理的字符串
	 * @param similarSearch
	 *            是否在检索词前后添加%
	 */
	public static String formatTRSSearchWord(String sw, boolean similarSearch) {

		sw = StringUtil.replaceString(sw, "\\", "\\\\");
		sw = StringUtil.replaceString(sw, "　", " ");
		sw = StringUtil.replaceString(sw, "'", "\\'");
		sw = StringUtil.replaceString(sw, "%", "\\%");
		sw = StringUtil.replaceString(sw, "?", "\\?");
		sw = StringUtil.squeezeWhiteSpace(sw);

		// TODO: 合并到common包的时候，考虑AND操作符当作一个参数之类的传入。
		if (similarSearch) {
			sw = StringUtil.replaceString(sw, " ", "%' AND '%");
		} else {
			sw = StringUtil.replaceString(sw, " ", "' AND  '");
		}

		sw = StringUtil.replaceString(sw, "<", "");
		sw = StringUtil.replaceString(sw, ">", "");

		if (similarSearch) {
			sw = "('%" + sw + "%')";
		} else {
			sw = "('" + sw + "')";
		}

		return sw;
	}

	public static String cleanTRSSearchInput(String tocheckString) {
		if (tocheckString != null && tocheckString.length() > 0) {
			return tocheckString.replaceAll("<", "").replaceAll(">", "");
		} else {
			return tocheckString;
		}
	}

	/**
	 * String2Alpha
	 */
	// 字母Z使用了两个标签，这里有２７个值
	// i, u, v都不做声母, 跟随前面的字母
	private static char[] chartable = { '啊', '芭', '擦', '搭', '蛾', '发', '噶', '哈',
			'哈', '击', '喀', '垃', '妈', '拿', '哦', '啪', '期', '然', '撒', '塌', '塌',
			'塌', '挖', '昔', '压', '匝', '座' };

	public static char[] alphatable = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
			'I',

			'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
			'W', 'X', 'Y', 'Z' };

	private static int[] table = new int[27];

	// 初始化
	static {
		for (int i = 0; i < 27; ++i) {
			table[i] = gbValue(chartable[i]);
		}
	}

	// 主函数,输入字符,得到他的声母,
	// 英文字母返回对应的大写字母
	// 其他非简体汉字返回 '0'

	private static char Char2Alpha(char ch) {

		if (ch >= 'a' && ch <= 'z')
			return (char) (ch - 'a' + 'A');
		if (ch >= 'A' && ch <= 'Z')
			return ch;

		int gb = gbValue(ch);
		if (gb < table[0])
			return '0';

		int i;
		for (i = 0; i < 26; ++i) {
			if (match(i, gb))
				break;
		}

		if (i >= 26)
			return '0';
		else
			return alphatable[i];
	}

	// 根据一个包含汉字的字符串返回一个汉字拼音首字母的字符串
	public static String String2Alpha(String SourceStr) {
		String Result = "";
		try {

			Result = "" + Char2Alpha(SourceStr.charAt(0));
		} catch (Exception e) {
			Result = "";
		}
		return Result;
	}

	private static boolean match(int i, int gb) {
		if (gb < table[i])
			return false;

		int j = i + 1;

		// 字母Z使用了两个标签
		while (j < 26 && (table[j] == table[i]))
			++j;

		if (j == 26)
			return gb <= table[j];
		else
			return gb < table[j];

	}

	// 取出汉字的编码
	private static int gbValue(char ch) {
		String str = new String();
		str += ch;
		try {
			byte[] bytes = str.getBytes("GB2312");
			if (bytes.length < 2)
				return 0;
			return (bytes[0] << 8 & 0xff00) + (bytes[1] & 0xff);
		} catch (Exception e) {
			return 0;
		}

	}

	public static Set<Integer> String2HashSet(String string, String spiltStr) {
		if (StringUtil.isEmpty(string) || StringUtil.isEmpty(spiltStr))
			return null;
		String[] strArray = string.split(spiltStr);
		Set<Integer> set = new HashSet<Integer>();

		int i = 0;
		for (String str : strArray) {
			try {
				i = Integer.parseInt(str);
				if (i > 0) {
					set.add(i);
					i++;
				}
			} catch (Exception e) {
				continue;
			}

		}
		return set;
	}

	/**
	 * 将字符串数组转换成以delimit指定的字符分割字符串
	 * 
	 * @param strarr
	 *            要转换的数组
	 * @param delimit
	 *            分隔符
	 * @return
	 */
	public static String toString(String[] strarr, String delimit) {
		String str = "";
		if (strarr != null) {
			for (int i = 0; i < strarr.length; i++) {
				str = str + delimit + strarr[i];
			}
		}
		if (str.length() > 0) {
			str = str.substring(1);
		}
		return str;
	}

	/**
	 * 将以delimit分割的字符串str转换成字符数组
	 * 
	 * @param delimitStr
	 *            以delimit指定的符号分割的字符串
	 * @param delimit
	 *            分隔符
	 * @return
	 */
	public static String[] toStrArray(String delimitStr, String delimit) {
		if (delimitStr != null && !"".equals(delimitStr)) {
			String[] strarr = delimitStr.split(delimit);
			return strarr;
		}
		return null;
	}

	/**
	 * 
	 * @param s
	 *            String类型的传入参数
	 * @return String类型 如88.00
	 */
	public static String parseToRmb(String s) {
		DecimalFormat fmt = new DecimalFormat("##.00");
		double result;
		String outStr = "";
		try {
			result = Double.parseDouble(s);
			outStr = fmt.format(result);
		} catch (Exception e) {
			// System.out.println("将金额转换为金额出错!");
			return s;
		}
		return outStr;
	}

	public static String parseToString(Object obj) {
		if (obj != null) {
			return obj.toString().trim();
		}
		return null;
	}

	public static double parseToDouble(Object obj) {
		double result = -1;
		if (obj != null) {
			try {
				result = Double.parseDouble(obj.toString());
			} catch (Exception e) {
				System.out.println("Exception in StringUtil :"
						+ e.getLocalizedMessage());
			}
		}
		return result;
	}

	public static int parseToInt(Object obj) {
		int result = -1;
		if (obj != null) {
			try {
				result = Integer.parseInt(obj.toString());
			} catch (Exception e) {
				System.out.println("Exception in StringUtil :"
						+ e.getLocalizedMessage());
			}
		}
		return result;
	}

	// null转空字符串
	public static String nullToString(String str) {
		if (!isEmpty(str)) {
			return str;
		} else {
			return "";
		}
	}

	// 国标码和区位码转换常量
	static final int GB_SP_DIFF = 160;

	// 存放国标一级汉字不同读音的起始区位码
	static final int[] secPosValueList = { 1601, 1637, 1833, 2078, 2274, 2302,
			2433, 2594, 2787, 3106, 3212, 3472, 3635, 3722, 3730, 3858, 4027,
			4086, 4390, 4558, 4684, 4925, 5249, 5600 };

	// 存放国标一级汉字不同读音的起始区位码对应读音
	static final char[] firstLetter = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
			'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'W', 'X',
			'Y', 'Z' };

	// 获取一个字符串的拼音码
	public static String getFirstLetter(String oriStr) {
		String str = oriStr.toUpperCase();
		StringBuffer buffer = new StringBuffer();
		char ch;
		char[] temp;
		for (int i = 0; i < str.length(); i++) { // 依次处理str中每个字符
			ch = str.charAt(i);
			temp = new char[] { ch };
			byte[] uniCode = new String(temp).getBytes();
			if (uniCode[0] < 128 && uniCode[0] > 0) { // 非汉字
				buffer.append(temp);
			} else {
				buffer.append(convert(uniCode));
			}
		}
		return buffer.toString();
	}

	/**
	 * 获取一个汉字的拼音首字母。 GB码两个字节分别减去160，转换成10进制码组合就可以得到区位码
	 * 例如汉字“你”的GB码是0xC4/0xE3，分别减去0xA0（160）就是0x24/0x43
	 * 0x24转成10进制就是36，0x43是67，那么它的区位码就是3667，在对照表中读音为‘n’
	 */

	static char convert(byte[] bytes) {

		char result = '-';
		int secPosValue = 0;
		int i;
		for (i = 0; i < bytes.length; i++) {
			bytes[i] -= GB_SP_DIFF;
		}
		secPosValue = bytes[0] * 100 + bytes[1];
		for (i = 0; i < 23; i++) {
			if (secPosValue >= secPosValueList[i]
					&& secPosValue < secPosValueList[i + 1]) {
				result = firstLetter[i];
				break;
			}
		}
		return result;
	}

	/**
	 * 
	 * @param size
	 *            长度，过长度小于等于0 ，则默认长度 6 默认最大长度为32，超过32，则按32计
	 * @param type
	 *            [number:生成纯数字] [english:生成纯英文] [其他:英文数字组合]
	 * @return
	 */
	public static String RadomCode(int size, String type) {
		String[] code = null;
		if (type != null && type == "number") {
			code = new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		} else if (type != null && type == "english") {
			code = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I",
					"J", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "U",
					"V", "W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f", "g",
					"h", "i", "j", "k", "l", "m", "n", "p", "q", "r", "s",
					"t", "u", "v", "w", "x", "y", "z" };
		} else {
			code = new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9",
					"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
					"L", "M", "N", "P", "Q", "R", "S", "T", "U", "V", "W",
					"X", "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h", "i",
					"j", "k", "l", "m", "n", "p", "q", "r", "s", "t", "u",
					"v", "w", "x", "y", "z", "_"};
		}
		Integer length = code.length;
		int random = 0;
		StringBuffer randomStr = new StringBuffer();
		if (size <= 0)
			size = 6;
		if (size > 32)
			size = 32;
		for (int i = 0; i < size; i++) {
			random = (int) (Math.random() * length);
			randomStr.append(code[random]);
		}
		return randomStr.toString();
	}

	/**
	 * 对给定的字符串执行加密/解密操作<br/>
	 * 方法使用异或的方式进行加密操作，<br/>
	 * 也就是说，将字符串第一次传入该方法后，字符串被加密<br/>
	 * 将被加密字符串传入该方法后，字符串被解密<br/>
	 * 
	 * @return
	 */
	public static String transformStr(String str) {
		byte[] seacrtStr = "1lemai".getBytes();
		byte[] resourceBytes = null;
		byte[] resultBytes = null;
		String resultStr = null;
		try {
			str = URLDecoder.decode(str, "UTF-8");
			resourceBytes = str.getBytes("UTF-8");
			resultBytes = new byte[resourceBytes.length];
			for (int i = 0; i < resourceBytes.length; i++) {
				for (int j = 0; j < seacrtStr.length; j++) {
					resultBytes[i] = (byte) (resourceBytes[i] ^ seacrtStr[j]);
				}
			}
			resultStr = new String(resultBytes, "UTF-8");
			resultStr = URLEncoder.encode(resultStr, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return resultStr;
	}

	public static String substring(String str, Integer start, Integer end, String append){
		if(str.length() < start){
			return str;
		}else{
			if(str.length() < end){
				return str.substring(start);
			}else{
				return str.substring(start,end)+append;
			}
		}
	}
	
	/**
	 * 将数字转换为汉字 例如123转换后为一百二十三 最大单位万
	 * 
	 * @return
	 */
	public static String changeToChinese(String number) {
		try {
			try {
				while (number.substring(0, 1).equals("0")) {
					number = number.substring(1);
				}
			} catch (StringIndexOutOfBoundsException e) {
				return null;
			}
			if (isInteger(number)) {
				String[] nums = new String[] { "1_一", "2_二", "3_三", "4_四",
						"5_五", "6_六", "7_七", "8_八", "9_九", "0_零" }; // 定义匹配数字
				int length = number.length();
				String[] chinese = new String[length];// 保存转换后的中文数字
				for (int i = 0; i < length; i++) {
					for (int j = 0; j < nums.length; j++) {
						if (number.substring(i, i + 1).equals(
								nums[j].substring(0, 1))) {
							chinese[i] = nums[j].substring(2, 3);
						}
					}
				}
				StringBuffer buffer = null;
				if (length == 1) {// 个位数
					return chinese[0];
				} else if (length == 2) {// 十位数
					buffer = new StringBuffer();
					if (!chinese[1].equals("零"))
						buffer.append(chinese[0] + "十" + chinese[1]);
					else
						buffer.append(chinese[0] + "十");
					return buffer.toString();
				} else if (length == 3) {// 百位数
					buffer = new StringBuffer();
					buffer.append(chinese[0] + "百");
					if (chinese[1].equals("零")) {
						if (!chinese[2].equals("零"))
							buffer.append(chinese[1] + chinese[2]);
					} else {
						if (!chinese[2].equals("零"))
							buffer.append(chinese[1] + "十" + chinese[2]);
						else
							buffer.append(chinese[1] + "十");
					}
					return buffer.toString();
				} else if (length == 4) {// 千位数
					buffer = new StringBuffer();
					buffer.append(chinese[0] + "千");
					if (chinese[1].equals("零")) {
						if (chinese[2].equals("零")) {
							if (!chinese[3].equals("零"))
								buffer.append(chinese[1] + chinese[3]);
						} else {
							if (!chinese[3].equals("零"))
								buffer.append(chinese[1] + chinese[2] + "十"
										+ chinese[3]);
							else
								buffer.append(chinese[1] + chinese[2] + "十");
						}
					} else {
						buffer.append(chinese[1]);
						if (!chinese[2].equals("零") || !chinese[3].equals("零"))
							buffer.append("百");
						if (chinese[2].equals("零")) {
							if (!chinese[3].equals("零"))
								buffer.append(chinese[2] + chinese[3]);
						} else {
							if (!chinese[3].equals("零"))
								buffer.append(chinese[2] + "十" + chinese[3]);
							else
								buffer.append(chinese[2] + "十");
						}
					}
					return buffer.toString();
				} else if (length == 5) {// 万位数
					buffer = new StringBuffer();
					buffer.append(chinese[0] + "万");
					if (chinese[1].equals("零")) {
						if (chinese[2].equals("零")) {
							if (chinese[3].equals("零")) {
								if (!chinese[4].equals("零"))
									buffer.append(chinese[1] + chinese[4]);
							} else {
								if (!chinese[4].equals("零"))
									buffer.append(chinese[1] + chinese[3] + "十"
											+ chinese[4]);
								else
									buffer
											.append(chinese[1] + chinese[3]
													+ "十");
							}
						} else {
							buffer.append(chinese[0] + "百");
							if (chinese[3].equals("零")) {
								if (!chinese[4].equals("零"))
									buffer.append(chinese[3] + chinese[4]);
							} else {
								if (!chinese[4].equals("零"))
									buffer
											.append(chinese[3] + "十"
													+ chinese[4]);
								else
									buffer.append(chinese[3] + "十");
							}
						}
					} else {
						buffer.append(chinese[1] + "千");
						if (chinese[2].equals("零")) {
							if (chinese[3].equals("零")) {
								if (!chinese[4].equals("零"))
									buffer.append(chinese[2] + chinese[4]);
							} else {
								if (!chinese[4].equals("零"))
									buffer.append(chinese[2] + chinese[3] + "十"
											+ chinese[4]);
								else
									buffer
											.append(chinese[2] + chinese[3]
													+ "十");
							}
						} else {
							buffer.append(chinese[2]);
							if (!chinese[3].equals("零")
									|| !chinese[4].equals("零"))
								buffer.append("百");
							if (chinese[3].equals("零")) {
								if (!chinese[4].equals("零"))
									buffer.append(chinese[3] + chinese[4]);
							} else {
								if (!chinese[4].equals("零"))
									buffer
											.append(chinese[3] + "十"
													+ chinese[4]);
								else
									buffer.append(chinese[3] + "十");
							}
						}
					}
					return buffer.toString();
				}
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	/**
	 * 
	 * 冒泡排序 方法：相邻两元素进行比较，如有需要则进行交换， 每完成一次循环就将最大元素排在最后（如从小到大排序），
	 * 下一次循环是将其他的数进行类似操作。
	 * 
	 * @param data
	 *            要排序的数组
	 * @param sortType
	 *            排序类型
	 * @return
	 */

	public static int[] bubbleSort(int[] data, String sortType) {
		if (sortType.equals("asc")) { // 正排序，从小排到大
			// 比较的轮数
			for (int i = 1; i < data.length; i++) { // 数组有多长,轮数就有多长
				// 将相邻两个数进行比较，较大的数往后冒泡
				for (int j = 0; j < data.length - i; j++) { // 每一轮下来会将比较的次数减少
					if (data[j] > data[j + 1]) {
						// 交换相邻两个数
						swap(data, j, j + 1);
					}
				}
			}
		} else if (sortType.equals("desc")) { // 倒排序，从大排到小
			// 比较的轮数
			for (int i = 1; i < data.length; i++) { // 数组有多长,轮数就有多长
				// 将相邻两个数进行比较，较大的数往后冒泡
				for (int j = 0; j < data.length - i; j++) { // 每一轮下来会将比较的次数减少
					if (data[j] < data[j + 1]) {
						// 交换相邻两个数
						swap(data, j, j + 1);
					}
				}
			}
		} else {
			System.out.println("您输入的排序类型错误！");
		}
		return data;
	}

	private static void swap(int[] data, int x, int y) {
		int temp = data[x];
		data[x] = data[y];
		data[y] = temp;
	}

	/**
	 * 抽奖
	 * 
	 * @param str
	 * @return 返回中奖并且获得多少钱或者不中奖
	 */
	public static Double luckDraw(String str) {
		try {
			String[] newStr = str.split("@");
			Map<String, Integer> map = new HashMap<String, Integer>();
			Integer randomNum = Integer.valueOf((int) (Math.random() * 100));
			Integer beforNum = 0;
			System.out.println(randomNum);
			for (String string : newStr) {
				if (!StringUtil.isEmpty(string)) {
					if (randomNum > beforNum
							&& randomNum <= beforNum
									+ Integer.parseInt(string.substring(string
											.indexOf("|") + 1))) {
						return Double.valueOf(string.substring(0, string
								.indexOf("|")));
					}
					beforNum = beforNum
							+ Integer.parseInt(string.substring(string
									.indexOf("|") + 1));
				}
			}
		} catch (Exception e) {
			return 0.0;
		}
		return 0.0;
	}

	/**
	 * 读取txt文本内容
	 * 
	 * @param readPath
	 * @return
	 */
	public String readTxt(String readPath) {
		try {
			File file = new File(readPath);
			if (!file.exists()) {
				System.exit(0);
			}
			BufferedReader br = new BufferedReader(new FileReader(readPath));
			String temp = "";
			temp = br.readLine();
			br.close();
			return temp;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static final boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}

	public static final boolean isChinese(String strName) {
		char[] ch = strName.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (isChinese(c)) {
				return true;
			}
		}
		return false;
	}
	

	
	/**
	 * 将驼峰式命名的字符串转换为下划线大写方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串。</br>
	 * 例如：HelloWorld->HELLO_WORLD
	 * @param name 转换前的驼峰式命名的字符串
	 * @return 转换后下划线大写方式命名的字符串
	 */
	public static String underscoreName(String name) {
	    StringBuilder result = new StringBuilder();
	    if (name != null && name.length() > 0) {
	        // 将第一个字符处理成大写
	        result.append(name.substring(0, 1).toUpperCase());
	        // 循环处理其余字符
	        for (int i = 1; i < name.length(); i++) {
	            String s = name.substring(i, i + 1);
	            // 在大写字母前添加下划线
	            if (s.equals(s.toUpperCase()) && !Character.isDigit(s.charAt(0))) {
	                result.append("_");
	            }
	            // 其他字符直接转成大写
	            result.append(s.toLowerCase());
	        }
	    }
	    return result.toString();
	}
	

	
	/**  
     * 计算两个日期之间相差的天数  
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return 相差天数 
     * @throws ParseException  
     */    
    public static int daysBetween(Date smdate,Date bdate) throws ParseException{    
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        smdate=sdf.parse(sdf.format(smdate));  
        bdate=sdf.parse(sdf.format(bdate));  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));           
    }  
    
    /**
	 * 获取指定HTML标签的指定属性的值
	 * @param source 要匹配的源文本
	 * @param element 标签名称
	 * @param attr 标签的属性名称
	 * @return 属性值列表
	 */
    public static List<String> matchHtmlProperty(String source, String element, String attr) {
		List<String> result = new ArrayList<String>();
    	if(!StringUtil.isEmpty(source)){
			String reg = "<" + element + "[^<>]*?\\s" + attr + "=['\"]?(.*?)['\"]?\\s.*?>";
    		System.out.println(source);
			Matcher m = Pattern.compile(reg).matcher(source);
			while (m.find()) {
				String r = m.group(1);
				result.add(r);
			}
    	}
		return result;
	}
    
    /**
    * 通过HttpServletRequest返回IP地址
    * @param request HttpServletRequest
    * @return ip String
    * @throws Exception
    */
   public static String getIpAddr(HttpServletRequest request) throws Exception {
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

   /**
   * 通过IP地址获取MAC地址
   * @param ip String,127.0.0.1格式
   * @return mac String
   * @throws Exception
   */
  public static String getMACAddress(String ip) throws Exception {
      String line = "";
      String macAddress = "";
      final String MAC_ADDRESS_PREFIX = "MAC Address = ";
      final String LOOPBACK_ADDRESS = "127.0.0.1";
      //如果为127.0.0.1,则获取本地MAC地址。
      if (LOOPBACK_ADDRESS.equals(ip)) {
          InetAddress inetAddress = InetAddress.getLocalHost();
          //貌似此方法需要JDK1.6。
          byte[] mac = NetworkInterface.getByInetAddress(inetAddress).getHardwareAddress();
          //下面代码是把mac地址拼装成String
          StringBuilder sb = new StringBuilder();
          for (int i = 0; i < mac.length; i++) {
              if (i != 0) {
                  sb.append("-");
              }
              //mac[i] & 0xFF 是为了把byte转化为正整数
              String s = Integer.toHexString(mac[i] & 0xFF);
              sb.append(s.length() == 1 ? 0 + s : s);
          }
          //把字符串所有小写字母改为大写成为正规的mac地址并返回
          macAddress = sb.toString().trim().toUpperCase();
          return macAddress;
      }
      //获取非本地IP的MAC地址
      try {
          Process p = Runtime.getRuntime().exec("nbtstat -A " + ip);
          InputStreamReader isr = new InputStreamReader(p.getInputStream());
          BufferedReader br = new BufferedReader(isr);
          while ((line = br.readLine()) != null) {
              if (line != null) {
                  int index = line.indexOf(MAC_ADDRESS_PREFIX);
                  if (index != -1) {
                      macAddress = line.substring(index + MAC_ADDRESS_PREFIX.length()).trim().toUpperCase();
                  }
              }
          }
          br.close();
      } catch (IOException e) {
          e.printStackTrace(System.out);
      }
      return macAddress;
  }

    
    public static void main(String[] args) {
		String str = "<p style=\"margin:0cm 0cm 0pt;\" class=\"MsoNormal\"><span style=\"font-family:宋体;font-size:24px;color:#e53333;\">北京市政交通一卡通的各种异型卡（迷你一卡通）已经可以在线购买了，欢迎大家选购。 &nbsp;</span><span style=\"font-size:small;font-family:宋体;text-indent:21pt;color:#337fe5;\"><b><span style=\"color:#e53333;\"><a href=\"http://www.elemai.com/shop/goods_list/miniyikatong.htm\" target=\"_blank\"><span style=\"font-size:24px;color:#e53333;\">&gt;&gt;&gt;</span></a></span></b></span></p><span style=\"font-size:24px;\"> </span><p style=\"margin:0cm 0cm 0pt;\" class=\"MsoNormal\"><span style=\"font-size:small;font-family:宋体;text-indent:21pt;color:#337fe5;\"><b><br /><span style=\"font-size:24px;\"> </span></b></span></p><p><a href=\"http://www.elemai.com/shop/goods_list/miniyikatong.htm\" target=\"_blank\"><img border=\"0\" alt=\"\" src=\"/upload/image/201209/ef982083c8904795bfd464dcec7a39ff.png\" /></a></p>";
		List<String> s = matchHtmlProperty(str, "img", "src");
		System.out.println(s.size());
		for (String string : s) {
			System.out.println(string);
		}
    }
}
