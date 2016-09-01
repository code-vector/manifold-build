/**
 * 
 */
package com.lanyine.manifold.mybatis.util;

/**
 * 
 * 
 * @author shadow
 * @Date 2015年8月12日 下午9:58:47
 *
 */
public class SqlChecker {
	private static final String escape_character = "\\";
	private static final String escape_character_target = "\\\\";
	private static final String single_quote = "'";
	private static final String single_quote_target = "\\'";
	private static final String double_quote = "\"";
	private static final String double_quote_target = "\\\"";

	public static String paramsSafeFilter(String param) {
		if (param != null) {
			param = param.replace(escape_character, escape_character_target).replace(single_quote, single_quote_target)
					.replace(double_quote, double_quote_target);
		}
		return param;
	}
}
