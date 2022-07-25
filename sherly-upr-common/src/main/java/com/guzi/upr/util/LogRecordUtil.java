package com.guzi.upr.util;

import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.guzi.upr.log.model.LoginLog;
import com.guzi.upr.manager.LoginLogManager;
import net.dreamlu.mica.ip2region.core.Ip2regionSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 谷子毅
 * @date 2022/7/15
 */
@Component
public class LogRecordUtil {

    @Autowired
    private LoginLogManager loginLogManager;

    @Autowired
    private Ip2regionSearcher regionSearcher;

    @Async
    public void recordLoginLog(HttpServletRequest request, String username, Integer result, Integer type) {
        LoginLog loginLog = new LoginLog();

        String userAgent = request.getHeader("User-Agent");
        if (userAgent != null) {
            UserAgent agent = UserAgentUtil.parse(userAgent);
            loginLog.setOs(agent.getOs().toString());
            loginLog.setBrowser(agent.getBrowser().toString());
        }
        String ip = ServletUtil.getClientIP(request);

        loginLog.setIp(ip);
        loginLog.setAddress(regionSearcher.getAddress(ip));
        loginLog.setUsername(username);
        loginLog.setType(type);
        loginLog.setResult(result);
        loginLogManager.save(loginLog);
    }
}
