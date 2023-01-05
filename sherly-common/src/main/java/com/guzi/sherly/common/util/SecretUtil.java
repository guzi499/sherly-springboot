package com.guzi.sherly.common.util;

import com.baomidou.mybatisplus.core.toolkit.AES;

/**
 * 配置文件加密工具（与项目无关）
 * @author 谷子毅
 * @date 2022/5/5
 */
@Deprecated
public class SecretUtil {

    public static void main(String[] args) {
        // 在这里输入待加密字符串
        String password = "";
        // 在这里输入盐值
        String key = "";
        // 打印加密后的字符串
        System.out.println(AES.encrypt(password, key));
    }
}
