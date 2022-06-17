package com.rongmei.util;


public class ThreadLocalUtil {

    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    /**
     * 设置token
     * @param token
     */
    public static void setToken(String token){
        threadLocal.set(token);
    }

    /**
     * 获取token
     * @return token
     */
    public static String getToken() {
        String token = threadLocal.get();
        return token;
    }
}
