package com.guzi.upr.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

/**
 * Java.Jwt工具类
 * @author 谷子毅
 * @date 2022/3/23
 */
public class JwtUtil {

    public static final String SIGN = "sherlyspringboot";

    /**
     * 生成token
     * @param data 自定义荷载
     * @return 生成的token
     */
    public static String generateToken(String data){

        return JWT.create()
                .withClaim("data", data)
                .sign(Algorithm.HMAC256(SIGN));
    }

    /**
     * 解析token
     * @param token 待解析token
     * @return 解析出的自定义荷载
     */
    public static String parseToken(String token) {
        return JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token).getClaim("data").asString();
    }
}
