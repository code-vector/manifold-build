package com.lanyine.manifold.tools;

import java.util.regex.Pattern;

/**
 * 正则表达式工具类，处理常见的邮箱，电话，IP等正则校验
 *
 * @author shdow
 */
public class RegularTool {
    private static final Pattern PhonePattern = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9])|(14[0-9]))\\d{8}$");
    private static final Pattern ChinesePattern = Pattern.compile("[\\u4e00-\\u9fa5]+");
    private static final Pattern CharacterPattern = Pattern.compile("^[A-Za-z]+$");
    private static final Pattern EmailPattern = Pattern.compile("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$");
    private static final Pattern NumberPattern = Pattern.compile("^[0-9]+$");

    /**
     * 校验是否是邮箱
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (StringTool.isEmptyByTrim(email))
            return false;
        return EmailPattern.matcher(email).matches();
    }

    /**
     * 检验是否是电话号码
     *
     * @param phone
     * @return
     */
    public static boolean isMobileNo(String phone) {
        return PhonePattern.matcher(phone).matches();
    }

    /**
     * 是否是中文
     *
     * @param chinese
     * @return
     */
    public static boolean hasChinese(String chinese) {
        return ChinesePattern.matcher(chinese).matches();
    }

    /**
     * 是否是英文(包含大小写)
     *
     * @param character
     * @return
     */
    public static boolean isCharacter(String character) {
        return CharacterPattern.matcher(character).matches();
    }

    /**
     * 是否是整数
     *
     * @param character
     * @return
     */
    public static boolean isNumber(String character) {
        return NumberPattern.matcher(character).matches();
    }

}
