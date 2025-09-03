package com.iurac.recruit.util;

import cn.hutool.core.util.RandomUtil;

public class SaltUtil {
    public static String getSalt(int n){
        char[] chars="qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM<>!@#$%^&*():?}{".toCharArray(); //转换为字符数组
        StringBuilder stringBuilder = new StringBuilder("");
        for (int i = 0; i < n; i++) {
            stringBuilder.append(chars[RandomUtil.randomInt(chars.length)]);
        }

        return stringBuilder.toString();
    }
}
