package com.guzi.upr.util;

import com.baomidou.mybatisplus.core.toolkit.AES;

/**
 * @author 谷子毅
 * @date 2022/5/5
 */
public class SecretUtil {

    public static void encodePassword() {
        String password = "";
        String key = "";
        String result = AES.encrypt(password, key);
        System.out.println(result);
    }









    public static void main(String[] args) {
        encodePassword();
    }
}
