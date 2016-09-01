package com.lanyine.manifold.tools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 
 * @author shadow
 * 
 */
public class CollectionTool {

	/**
	 * 将List<T> 转成 List<String>,主要利用T的toString()方法获取String
	 *
	 * @param list
	 *            需要转换的集合
	 * @return 返回转换后的List<String>
	 */
	public static List<String> convertStrList(Collection<?> list) {
		/*
		 * List<String> out = new ArrayList<>(); if (null == list) return null;
		 * for (Object o : list) { out.add(o.toString()); } return out;
		 */
		if (isEmpty(list)) {
			return null;
		}
		return list.stream().map(l -> l.toString()).collect(Collectors.toList());
	}

	/**
	 * 判断集合是否为null & isEmpty
	 *
	 * @param list
	 *            list implements Collection
	 * @return ture:empty or size =0 | false:not empty
	 */
	public static boolean isEmpty(Collection<?> list) {
		return list == null || list.isEmpty();
	}

	/**
	 * 判断集合是否为null & isEmpty
	 *
	 * @param list
	 *            list implements Collection
	 * @return ture:not empty | false:empty or size =0
	 */
	public static boolean isNotEmpty(Collection<?> list) {
		return !isEmpty(list);
	}

	/**
	 * strA、strB 按照regex分割成集合,返回计算集合A-集合B的差集 (用于计算字符串B比字符串A减少的部分)
	 *
	 * @param strA
	 *            字符串A
	 * @param strB
	 *            字符串B
	 * @param regex
	 *            (分割的正则表达式)
	 * @return
	 * @see java.lang.String#split (regex)
	 */
	public static Set<String> subtraction(String strA, String strB, String regex) {
		strA = StringTool.trimNotEmpty(strA);
		strB = StringTool.trimNotEmpty(strB);

		Set<String> set1 = new HashSet<>();
		Set<String> set2 = new HashSet<>();

		Collections.addAll(set1, strA.split(regex));
		Collections.addAll(set2, strB.split(regex));

		set1.removeAll(set2);
		return set1;
	}

	/**
	 *
	 * @param strA
	 * @param strB
     * @return
     */
	public static Set<String> subtraction(String strA, String strB) {
		return subtraction(strA, strB, ",");
	}

	/**
	 * 将strs分割转换成List，默认按“,”分割
	 * 
	 * @param strs
	 * @return List
	 * 
	 *         <pre>
	 *  eg:  strs = a,b,c,d,d,d;
	 *    返回： list = {a,b,c,d}
	 *         </pre>
	 */
	public static List<String> splitStrs2List(String strs) {
		return splitStrs2List(strs, ",");
	}

	/**
	 * 将strs分割转换成List，按照传入的字符分割
	 * 
	 * @param strs
	 * @return List
	 * 
	 *         <pre>
	 *  eg:  strs = a,b,c,d,d,d;
	 *       reg = ","
	 *    返回： list = {a,b,c,d}
	 *         </pre>
	 */
	public static List<String> splitStrs2List(String strs, String reg) {
		Set<String> set = new HashSet<>();
		if (StringTool.isNotEmptyByTrim(strs)) {
			for (String str : strs.split(reg)) {
				if (StringTool.isNotEmptyByTrim(str))
					set.add(str);
			}
		}
		// 去重后，以list返回
		List<String> list = new ArrayList<>();
		list.addAll(set);
		return list;
	}
	
	/**
	 * 原生分割
	 * 按照原本顺序分割
	 * @param strs
	 * @param reg
	 * @return
	 */
	public static List<String> splitStrs2List2(String strs, String reg){
		List<String> list =new ArrayList<>();
		if(StringTool.isNotEmptyByTrim(strs)){
			for(String str : strs.split(reg)){
				if(StringTool.isNotEmptyByTrim(str)){
					list.add(str);
				}
			}
		}
		return list;
	}
	
	
	/**
	 * 将字符串按照正则(",")分割后，转化成List<Integer> 此方法会去重
	 * 
	 * @param strs
	 * @return List
	 * 
	 *         <pre>
	 *  eg:  strs = 1,2,3,4,4,4;
	 *    返回： list = {1,2,3,4}
	 *         </pre>
	 */
	public static List<Integer> splitStrs2Int(String strs) {
		return splitStrs2Int(strs, ",");
	}

	/**
	 * 将字符串按照正则分割后，转化成List<Integer> 此方法会去重
	 * 
	 * @param strs
	 * @param reg
	 * @return List
	 * 
	 *         <pre>
	 *  eg:  strs = 1,2,3,4,4,4;
	 *       reg = ","
	 *    返回： list = {1,2,3,4}
	 *         </pre>
	 */
	public static List<Integer> splitStrs2Int(String strs, String reg) {
		if (StringTool.isEmpty(strs)) {
			return null;
		}

		Set<Integer> set = new HashSet<>();
		if (StringTool.isNotEmptyByTrim(strs)) {
			for (String str : strs.split(reg)) {
				if (StringTool.isNotEmptyByTrim(str))
					set.add(Integer.parseInt(str.trim()));
			}
		}
		// 去重后，以list返回
		List<Integer> list = new ArrayList<Integer>();
		list.addAll(set);

		return list;
	}

	public static void main(String[] args) {
		// System.out.println(splitStrs2Int("1,2,3,"));

//		List<Integer> list = new ArrayList<>();
//		list.add(1);
//		list.add(2);

	}
}
