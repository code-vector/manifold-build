package com.lanyine.manifold.tools;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

/**
 * 字符串工具类
 * 
 * @author shadow
 * 
 */
public class StringTool extends StringUtils {

	/**
	 * 判断字符串 是否为空
	 * 
	 * @param text
	 * @return boolean 为空：true 不为空：false
	 * 
	 *         <pre>
	 *  eg:  str1 = null;
	 *           str2 = "abc"
	 *           返回：  str1 返回 true  ,, str2返回 false
	 *         </pre>
	 */
	public static boolean isEmptyByTrim(String text) {
		return text == null || text.trim().isEmpty();
	}

	/**
	 * 判断字符串 是否为空
	 * 
	 * @param text
	 * @return boolean 为空：false 不为空：true
	 * 
	 *         <pre>
	 *  eg:  str1 = null;
	 *           str2 = "abc"
	 *           返回：  str1 返回 false  ,, str2返回 true
	 *         </pre>
	 */
	public static boolean isNotEmptyByTrim(String text) {
		return !isEmptyByTrim(text);
	}

	/**
	 * 判断字符串 是否为空
	 * 
	 * @param text
	 * @return String 为空：返回""空串 不为空：正常返回
	 * 
	 *         <pre>
	 *  eg:  str1 = null;
	 *           str2 = "abc"
	 *           返回：  str1 返回 ""  ,, str2返回 abc
	 *         </pre>
	 */
	public static String trimNotEmpty(String text) {
		return isEmptyByTrim(text) ? "" : text;
	}

	/**
	 * 判断字符串 是否空值
	 * 
	 * @param str
	 * @return String 为空：返回""空串 不为空：正常返回
	 * 
	 *         <pre>
	 *  eg:  str1 = null;
	 *           str2 = "abc"
	 *           返回：  str1 返回 ""  ,, str2返回 abc
	 *         </pre>
	 */
	public static String ifNull(String str) {
		return str == null ? "" : str;
	}

	/**
	 * Set集合 装换成以“,”相连的字符串
	 * 
	 * @param set
	 * @return String
	 * 
	 *         <pre>
	 * eg:  set.add("a");
	 *           set.add("b");
	 *           set.add("c");
	 *            返回   str = a,b,c
	 *         </pre>
	 */
	public static String set2SplitStrs(Set<String> set) {

		if (set == null || set.isEmpty())
			return "";

		return set.stream().collect(Collectors.joining(","));

		// StringBuffer buffer = new StringBuffer();
		// for (String str : set) {
		// buffer.append(str).append(",");
		// }
		// return buffer.substring(0, buffer.length() - 1);

	}

	/**
	 * 删除最后一个字符
	 * 
	 * @param text
	 * @return String
	 * 
	 *         <pre>
	 * eg:  str = abcdefg
	 *            返回   str = abcdef
	 *         </pre>
	 */
	public static String deleteLast(String text) {
		if (text == null || text.length() == 0)
			return text;
		return text.substring(0, text.length() - 1);
	}

	/**
	 * 删除最后一个字符
	 * 
	 * @param strBuilder
	 * @return StringBuilder
	 * 
	 *         <pre>
	 * eg:  str = abcdefg
	 *            返回   str = abcdef
	 *         </pre>
	 */
	public static StringBuilder deleteLast(StringBuilder strBuilder) {
		if (strBuilder == null || strBuilder.length() == 0)
			return strBuilder;
		return strBuilder.deleteCharAt(strBuilder.length() - 1);
	}

	/**
	 * 从”,“转成”|“
	 * 
	 * @param str
	 * @return String
	 * 
	 *         <pre>
	 * eg:  str = a,b,c,d,e
	 *         返回    str = a|b|c|d|e
	 *         </pre>
	 */
	public static String commaToVertical(String str) {
		if (StringTool.isEmpty(str)) {
			return "";
		}
		/*
		 * String string = null; String[] strs = str.split(","); // for (int i =
		 * 0; i < strs.length; i++) { if (strs[i] != null) { if (i == 0) {
		 * string = strs[i] + "|"; } else if (i == strs.length - 1) { string +=
		 * strs[i]; } else { string += strs[i] + "|"; } } } return string;</pre>
		 */
		String[] strs = str.split(",");
		return Arrays.asList(strs).stream().filter(s -> StringTool.isNotEmptyByTrim(s))
				.collect(Collectors.joining("|"));
	}

	/**
	 * 反转 从“|”转为","
	 * 
	 * @param str
	 * @return
	 * 
	 * 		<pre>
	 * eg:  str = a|b|c|d|e
	 *         返回    str = a,b,c,d,e
	 *         </pre>
	 */
	public static String ReCommaToVertical(String str) {
		if (StringTool.isEmpty(str)) {
			return "";
		}
		String[] strs = str.split("\\|");
		return Arrays.asList(strs).stream().filter(s -> StringTool.isNotEmptyByTrim(s))
				.collect(Collectors.joining(","));
	}

	/**
	 * 从”|“转成” “
	 * 
	 * @param str
	 * @return String
	 * 
	 *         <pre>
	 * eg:  str = a|b|c|d|e
	 *         返回    str = a b c d e
	 *         </pre>
	 */
	public static String verticalToSpace(String str) {
		if (StringTool.isEmpty(str)) {
			return "";
		}
		/*
		 * String string = null; String[] strs = str.split("\\|"); // for (int i
		 * = 0; i < strs.length; i++) { if (i == 0) { string = strs[i] + " "; }
		 * else if (i == strs.length - 1) { string += strs[i]; } else { string
		 * += strs[i] + " "; } } return string;</pre>
		 */
		String[] strs = str.split("\\|");
		return Arrays.asList(strs).stream().filter(s -> StringTool.isNotEmptyByTrim(s))
				.collect(Collectors.joining(" "));
	}

	/**
	 * 从”_“转成“ ”
	 * 
	 * @param str
	 * @return String
	 * 
	 *         <pre>
	 * eg:  str = a_b_c_d_e
	 *         返回    str = a b c d e
	 *         </pre>
	 */
	public static String underlineToSpace(String str) {
		if (StringTool.isEmpty(str)) {
			return "";
		}
		return str.replace("_", " ");
	}

	/**
	 * 拆分order$size_quantity_item表 中 item_json 去掉' [] '、' "" '、第一个字符串、最后一个字符串
	 * ，拿到每一个尺码（size）的数量
	 * 
	 * @param str
	 * @return String[]
	 * 
	 *         <pre>
	 * eg:  json = ["a","b","c"]
	 *         返回    str[] = {a,b,c}
	 *         </pre>
	 */
	public static String[] convertItemJson(String str) {
		if (StringTool.isEmpty(str)) {
			return null;
		}
		String string = convertItemJson2(str);
		String[] strs1 = string.split(",");
		String[] strs2 = new String[strs1.length - 2];
		for (int i = 0; i < strs1.length; i++) {
			if (i != 0 && i != strs1.length - 1) {
				strs2[i - 1] = strs1[i];
			}
		}
		return strs2;
	}

	/**
	 * 拆分order$size_quantity_item表 中 item_json 去掉' [] '、' "" '
	 * 
	 * @param str
	 * @return String
	 * 
	 *         <pre>
	 * eg:  json = ["a","b","c"]
	 *         返回    str = a,b,c
	 *         </pre>
	 */
	public static String convertItemJson2(String str) {
		if (StringTool.isEmpty(str)) {
			return "";
		}
		return str.replace("[", "").replace("]", "").replace("\"", "");
	}

	/**
	 * 
	 * </pre>
	 */
	/**
	 * 电话 从"|"拆分，再进行合并 成 “(+xxx)xxxxxxxxxx”
	 * 
	 * @param phone
	 * @return String
	 * 
	 *         <pre>
	 * eg:  str = 086|13425896489
	 *         返回    str = (+088)13425896489
	 *         </pre>
	 */
	public static String changePhone(String phone) {
		if (StringTool.isEmpty(phone)) {
			return "";
		}
		/*
		 * String[] phoneStr = verticalToArray(phone); if (phoneStr.length == 2)
		 * { return "(+" + phoneStr[0] + ")" + phoneStr[1]; } else { return
		 * phone; }</pre>
		 */
		String[] strs = phone.split("\\|");
		return Arrays.asList(strs).stream().collect(Collectors.joining(")", "(+", ""));
	}

	/**
	 * 姓名 从"|"拆分，再进行合并 成 “xx xxxxxx”
	 * 
	 * @param name
	 * @return String
	 * 
	 *         <pre>
	 * eg:  str = liang|zhicong
	 *         返回    str = liang zhicong
	 *         </pre>
	 */
	public static String changeName(String name) {
		if (StringTool.isEmpty(name)) {
			return "";
		}
		/*
		 * String[] nameStr = verticalToArray(name); if (nameStr.length == 2) {
		 * return nameStr[0] + " " + nameStr[1]; } else { return name; }</pre>
		 */
		String[] strs = name.split("\\|");
		return Arrays.asList(strs).stream().collect(Collectors.joining(" "));
	}

	/**
	 * 地址 从"|"拆分，再进行合并 成 “xxx xxx xxx xxx”
	 * 
	 * @param address
	 * @return String
	 * 
	 *         <pre>
	 *  eg:  str = 广东省|广州市|海珠区|琶洲
	 *         返回    str = 广东省 广州市 海珠区 琶洲
	 *         </pre>
	 *         </pre>
	 */
	public static String changeAddress(String address) {
		if (StringTool.isEmpty(address)) {
			return "";
		}
		/*
		 * String[] addressStr = verticalToArray(address); if (addressStr.length
		 * == 4) { return addressStr[0] + " " + addressStr[1] + " " +
		 * addressStr[2] + " " + addressStr[3]; } else { return address; }</pre>
		 */
		String[] strs = address.split("\\|");
		return Arrays.asList(strs).stream().collect(Collectors.joining(" "));
	}

	/**
	 * 合同分期期数 从“|”拆分，并合并成 xx% : xx%
	 * 
	 * @param periods
	 * @return String
	 * 
	 *         <pre>
	 * eg:  str = 20|50|30
	 *         返回    str = 20% : 50% : 30%
	 *         </pre>
	 */
	public static String changePeriods(String periods) {
		if (StringTool.isEmpty(periods)) {
			return "";
		}
		/*
		 * String[] periodsStr = periods.split("\\|"); String str = ""; for (int
		 * i = 0; i < periodsStr.length; i++) { if (i != periodsStr.length - 1)
		 * {// 不是最后一个 str += periodsStr[i] + "%" + " : "; } else { str +=
		 * periodsStr[i] + "%"; } } return str; </pre>
		 */
		String[] strs = periods.split("\\|");
		return Arrays.asList(strs).stream().filter(s -> StringTool.isNotEmptyByTrim(s))
				.collect(Collectors.joining("% : ", "", "%"));
	}

	/**
	 * 数组 转成 “,”字符串
	 * 
	 * @param strs
	 * @return String
	 * 
	 *         <pre>
	 * eg:  strs[] = [a b c d]
	 *         返回    str = a,b,c,d
	 *         </pre>
	 */
	public static String arrayToVertical(String[] strs) {
		if (strs == null) {
			return "";
		}
		return Arrays.asList(strs).stream().filter(p -> StringTool.isNotEmptyByTrim(p))
				.collect(Collectors.joining(","));
	}

	/**
	 * 数组 转成 “某种分隔符” 字符串
	 * 
	 * @param strs
	 * @return
	 * 
	 * 		<pre>
	 * eg:  strs[] = [a b c d]
	 *         reg = ","
	 *         返回    str = a,b,c,d
	 *         </pre>
	 */
	public static String array2Str(String[] strs, String reg) {
		if (strs == null) {
			return "";
		}
		return Arrays.asList(strs).stream().filter(p -> StringTool.isNotEmptyByTrim(p))
				.collect(Collectors.joining(reg));
	}

	/**
	 * 集合 按某分隔符转string
	 * 
	 * @param list
	 * @param reg
	 * @return
	 * 
	 * 		<pre>
	 * eg:  list = {a,b,c,d}
	 *         reg = "|"
	 *         返回    str = a|b|c|d
	 *         </pre>
	 */
	public static String list2Str(List<String> list, String reg) {
		if (list == null || reg == null) {
			return null;
		}
		return list.stream().filter(l -> StringTool.isNotEmptyByTrim(l)).collect(Collectors.joining(reg));
	}

	/**
	 * 将strs分割转换成String，按照传入的字符分割,取其第一个
	 * 
	 * @param strs
	 * @return List
	 * 
	 *         <pre>
	 *  eg:  strs = a,b,c,d,d,d;
	 *       reg = ","
	 *    返回：String = a
	 *         </pre>
	 */
	public static String splitStrsFrist(String strs, String reg) {
		if (StringTool.isEmpty(strs)) {
			return null;
		}
		return strs.split(reg)[0];
	}

	/**
	 * 验证是否是数字
	 * 
	 * @param number
	 */
	public static boolean isNumber(String number) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher matcher = pattern.matcher(number);
		if (matcher.matches()) {
			return true; // 是数字
		}
		return false;
	}

	/**
	 * str是按“|”保存的字符串，但由于太长，只需截取某些
	 * 
	 * @param strs
	 * @return List
	 * 
	 *         <pre>
	 *  eg:  strs = a|b|c|d|d|d;
	 *       num = 3
	 *    返回：String = a|b|c
	 *         </pre>
	 */
	public static String subStringforReg(String str, Integer num) {
		if (StringTool.isEmpty(str) || num == null) {
			return null;
		}
		String[] strs = str.split("\\|");
		List<String> list = new ArrayList<>();

		for (int i = 0; i < strs.length; i++) {
			if (i < num) {
				list.add(strs[i]);
			}
		}
		String result = null;
		if (CollectionTool.isNotEmpty(list)) {
			result = list.stream().filter(l -> StringTool.isNotEmptyByTrim(l)).collect(Collectors.joining("|"));
		}
		return result;
	}

	/**
	 * 
	 * 过滤特殊字符并截取length个字符,空格用"-"替换
	 * 
	 * @param text
	 * @param length
	 * @return
	 * 
	 * 
	 * 		<pre>
	 *  eg:  text = `1235678~!@#asd gfhgfasf das$%^&4567QWERTYUIOP@##$%^&*DFFGHJ KK TYU  ;
	 *       length = 3
	 *  返回：  String = 1235678asd-gfhgfasf-das
	 *         </pre>
	 */
	public static String interceptionText(String text, Integer length) {
		if (isEmptyByTrim(text)) {
			return "";
		}
		String regex = "[^a-zA-Z0-9\\s]";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(text.substring(0, text.length() > length ? length : text.length()));
		String str = m.replaceAll("").trim().replaceAll("\\s+", "-").toLowerCase();
		return str;
	}

	public static String interceptionText(String text) {
		return interceptionText(text, 30);
	}

	/**
	 * url字符串转码
	 * 
	 * @param text
	 * @return
	 * @throws UnsupportedEncodingException
	 * 
	 *             <pre>
	 *  eg:  text = `1235678~!@ ;
	 *  返回：  String = %601235678%7E%21%40
	 *             </pre>
	 */
	public static String urlTextEncode(String text) throws UnsupportedEncodingException {
		if (text != null) {
			return URLEncoder.encode(text.trim(), "utf-8");
		}
		return "";
	}

	/**
	 * url字符串解密
	 * 
	 * @param text
	 * @return
	 * @throws UnsupportedEncodingException
	 * 
	 *             <pre>
	 *  eg:  text = %601235678%7E%21%40;
	 *  返回：  String = `1235678~!@
	 *             </pre>
	 */
	public static String urlTextDecode(String text) throws UnsupportedEncodingException {
		if (text != null) {
			return URLDecoder.decode(text.trim(), "utf-8");
		}
		return "";
	}
	
	/**
	 * 币种转换符号
	 * 
	 * @param currency
	 * @return
	 */
	public static String currencyChange(String currency){
		if(StringTool.isEmptyByTrim(currency)){
			return null;
		}
		String v = currency;
		if("USD".equals(currency)){
			v = "$";
		}
		if("CNY".equals(currency)){
			v = "¥";
		}
		return v;
	}
	
	/**
	 * 将邮箱中间部分变为***
	 * <pre>
	 *      1234567@qq.com   ->  1***7@qq.com
	 * </pre>
	 * 
	 * @param email
	 * @return
	 */
	public static String encrypEmail(String email) {
		if(StringTool.isEmptyByTrim(email)){
			return null;
		}
		StringBuffer result = new StringBuffer();
		String first = null;
		String last = null;
		if(email.contains("@")){
			first = email.substring(0, email.indexOf("@"));
			last = email.substring(email.indexOf("@"), email.length());			
		}else{
			first = email;
		}
		result.append(first.substring(0, 1)).append("***");
		if(first.length() > 2){
		    result.append(first.substring(first.length()-1, first.length()));
		}
		if(last != null) {
			result.append(last);
		}
		return result.toString();
	}

	/**
	 * Integer数组用par分割
	 *
	 *  <pre>
	 *  	eg: iArray = {1,2,3};
	 *  		par=",";
	 *  	返回：String = 1,2,3
	 *  </pre>
	 *  
	 * @param iArray
	 * @return
	 */
	public static String intArrayToStr(Integer[] iArray,String par) {
		return Arrays.asList(iArray).stream().map(i->i.toString()).collect(Collectors.joining(par));
	}
	
	public static void main(String[] args) throws Exception {
		
		Integer[] i={1,2,3};
		System.out.println(StringTool.intArrayToStr(i, "|"));

//		List<String> list = new ArrayList<>();
//		list.add("11111");
//		list.add("11111");
//		list.add("11111");
//		System.out.println(list2Str(list, "$"));
//		System.err.println("sdfsd|f,.$".replaceAll("\\,", ""));
		
		
//		System.out.println(StringTool.encrypEmail("123456@qq.com"));
		
		// String str = "`1235678~!@ ";
		// System.out.println(urlTextEncode(str));
		// String str2 = "%601235678%7E%21%40";
		// System.out.println(urlTextDecode(str2));
		// String str = "a|b|c|d|d|d";
		// System.out.println("str="+subStringforReg(str, 10));
		// String[] blogWords = {"liangzhicong","xiehuimin","congmin"};
		// System.out.println(String.join(" ", blogWords));
		// System.out.println("----------------------------------------------------------");
		// List<String> list = Arrays.asList("20","30","40");
		// System.out.println(list);
		// System.out.println(list.stream().map(l ->
		// l.toString()).collect(Collectors.joining(" ", "(", ")")));
		// System.out.println(list.stream().map(l ->
		// l.toString()).collect(Collectors.joining("% : ","","%")));
		//
		// System.out.println(Arrays.asList(blogWords));
		//
		// System.out.println(changePeriods("20|23|40|50"));
		//
		// System.out.println(changeAddress("广东省|广州市|海珠区|琶洲"));
		//
		// System.out.println(changeName("liang|zhicong"));
		//
		// System.out.println(changePhone("086|12322334434"));
		//
		// System.out.println(verticalToSpace("aaa|bbb|ccc"));
		//
		// System.out.println(commaToVertical("adf,adf,af,,,,,,"));
		//
		// String[] strs = {"aaa","",null,"212121"};
		// System.out.println(arrayToVertical(strs));
		//
		// System.out.println(underlineToSpace("lla_sdf_sdf_dfef_dfefe"));

		// String str = "[\"red\",\"20\",\"30\",\"20\",\"70\"]";
		// System.out.println(convertItemJson2(str));
		// Gson json = new Gson();
		// System.out.println(json.toJson(convertItemJson(convertItemJson2(str))));
		// List<String> list = new ArrayList<>();
		// list.add("abc");
		// list.add("bcd");
		// list.add("cde");
		// String reg = "|";
		//
		// System.out.println(StringTool.list2Str(list, reg));
		System.out.println(isEmptyByTrim("  "));

	}


}
