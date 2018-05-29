package org.springframework.litchi.common.util;

/**
 * @author: suijie
 * @date: 2018/5/26 10:17
 * @description:
 */
public class StringUtils {

    public static boolean isEmpty(String str) {
        return null == str || "".equals(str);
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
}
