package com.guzi.upr.util;

import com.guzi.upr.model.ThreadLocalModel;

/**
 * @author 谷子毅
 * @date 2022/3/24
 */
public class ThreadLocalUtil {

    private static ThreadLocal<ThreadLocalModel> paramModel = new ThreadLocal<>();

    public static void set(ThreadLocalModel data) {
        paramModel.set(data);
    }

    public static ThreadLocalModel get() {
        return paramModel.get();
    }

    /** 防止内存泄漏 */
    public static void remove() {
        paramModel.remove();
    }
}
