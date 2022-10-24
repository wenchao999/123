package com.reggie.common;

/**
 * 基于ThreadLocal封装的工具类，用来保存和获取当前用户的id
 * 一次请求是一个线程，这样我们就能获取信息
 *
 * @author NewAdmin
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /**
     * 获取值
     *
     * @return
     */
    public static Long getCurrentId() {
        return threadLocal.get();
    }

    /**
     * 存储值
     *
     * @param id
     */
    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }
}
