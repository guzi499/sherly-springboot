package com.guzi.upr.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Calendar;

/**
 * @author 谷子毅
 * @email guzyc@digitalchina.com
 * @date 2022/3/23
 */
public class JwtUtil {

    public static final String SIGN = "sherly";


    /**
     * 生成token
     * @param data 自定义荷载
     * @return 生成的token
     */
    public static String generateToken(String data){
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND, 1);
        return JWT.create()
                .withClaim("data", data)
                .withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC256(SIGN));
    }

    /**
     * 解析token
     * @param token 待解析token
     * @return 解析出的自定义荷载
     */
    public static String parseToken(String token) {
        String data;
        try {
            data = JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token).getClaim("data").asString();
        }catch (Exception e) {
            // todo 抛自定义异常
            throw new NullPointerException("登录失败，请检查登录参数");
        }
        return data;
    }



    public static void main(String[] args) {
        String a = "a series of data";
        String token = generateToken(a);
        System.out.println(token);
        String data = parseToken(token);
        System.out.println(data);
    }
}
