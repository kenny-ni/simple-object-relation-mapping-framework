package com.jiageng.sorm.utils;

/**
 * utils for String relative process
 */
public class StringUtils {
    public static String firstCharUpper(String original){
        return original.substring(0,1).toUpperCase() + original.substring(1);
    }

}
