package com.guzi.sherly.util;

import cn.hutool.extra.servlet.ServletUtil;
import net.dreamlu.mica.ip2region.core.Ip2regionSearcher;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 谷子毅
 * @date 2022/11/9
 */
public class IpUtil {

    private final static Ip2regionSearcher IP2REGION_SEARCHER = SpringContextHolder.getBean(Ip2regionSearcher.class);

    /**
     * 获取客户端请求ip
     * @param request
     * @return
     */
    public static String getIp(HttpServletRequest request) {
        return ServletUtil.getClientIP(request);
    }

    /**
     * 获取客户端请求ip的地址信息
     * @param ip
     * @return
     */
    public static String getAddress(String ip) {
        return IP2REGION_SEARCHER.getAddress(ip);
    }



}
