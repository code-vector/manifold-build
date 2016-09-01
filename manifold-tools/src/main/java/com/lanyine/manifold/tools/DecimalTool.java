package com.lanyine.manifold.tools;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 
 * BigDecimal 工具类
 * 
 * @author shadow
 *
 */
public class DecimalTool {

	/**
	 * double 转 BigDecimal 并保留2位小数
	 * 
	 * @param num
	 * @return
	 */
	public static BigDecimal moneyYuanFormat(double num) {
		return new BigDecimal(num).setScale(2, RoundingMode.HALF_UP);
	}

	/**
	 * double 转 BigDecimal 并保留3位小数
	 * 
	 * @param num
	 * @return
	 */
	public static BigDecimal weightTonFormat(double num) {
		return new BigDecimal(num).setScale(3, RoundingMode.HALF_UP);
	}

	/**
	 * 判断两个BigDecimal是否相等 ，，先保留3位小数，再进行判断
	 * 
	 * @param first
	 * @param other
	 * @return
	 */
	public static boolean equals(BigDecimal first, BigDecimal other) {
		return first.setScale(3, RoundingMode.HALF_UP).equals(other.setScale(3, RoundingMode.HALF_UP));
	}

	/**
	 * 判断两个BigDecimal是否相等 ，，先保留3位小数，再进行判断
	 * 
	 * <pre>
	 *    如果 first > other 返回 1
	 *    如果 first == other 返回 0
	 *    如果 first < other 返回 -1
	 * </pre>
	 * 
	 * @param first
	 * @param other
	 * @return
	 */
	public static int compareTo(BigDecimal first, BigDecimal other) {
		return first.setScale(3, RoundingMode.HALF_UP).compareTo(other.setScale(3, RoundingMode.HALF_UP));
	}

	/**
	 * 乘法
	 * 
	 * @param decimal
	 * @param quantity
	 * @return
	 */
	public static BigDecimal multiply(BigDecimal decimal, int quantity) {
		BigDecimal countDecimal = new BigDecimal(quantity);
		return decimal.multiply(countDecimal);
	}

	public static void main(String[] args) {
		// System.out.println(weightTonFormat(234.5673));
		System.out.println(multiply(new BigDecimal(100),3));
	}
}
