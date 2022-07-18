package com.guzi.upr.util;

import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.guzi.upr.log.model.LoginLog;
import com.guzi.upr.manager.LoginLogManager;
import net.dreamlu.mica.core.log.LogPrintStream;
import net.dreamlu.mica.ip2region.core.Ip2regionSearcher;
import net.dreamlu.mica.ip2region.utils.Ip2regionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.imageio.IIOParam;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

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

    public void recordLoginLog(String username, Integer result, Integer type) {
        LoginLog loginLog = new LoginLog();

        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
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
