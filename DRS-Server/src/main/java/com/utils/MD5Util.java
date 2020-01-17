package com.utils;

import org.springframework.util.DigestUtils;

/**
 * @author monetto
 */
public class MD5Util {
    private static final String SLAT = "";

    public static String get(String str) {
//        String base = str + "/" + SLAT;
        String base = str + SLAT;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    public static void main(String[] args) {
        String str = "This is origin String";
        System.out.print("Origin str: " + str + "\n");
        String md5Str = MD5Util.get(str);
        System.out.print("Trans to MD5: " + md5Str + "\n");
        System.out.print("Trans compare: " + md5Str.equals(MD5Util.get(str)) + "\n");
    }

}
