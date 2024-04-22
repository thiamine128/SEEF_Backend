package com.software.utils;

import java.util.Map;

public class BaseContext {

    public static ThreadLocal<Map<String,Object>> threadLocal = new ThreadLocal<>();

    public static void setCurrentUser(Map<String,Object> currentUser) {
        threadLocal.set(currentUser);
    }

    public static Map<String,Object> getCurrentUser() {
        return threadLocal.get();
    }

    public static void removeCurrentId() {
        threadLocal.remove();
    }

}
