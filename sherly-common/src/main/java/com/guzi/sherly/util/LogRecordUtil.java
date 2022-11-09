package com.guzi.sherly.util;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.guzi.sherly.modules.log.manager.LoginLogManager;
import com.guzi.sherly.modules.log.model.LoginLog;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author 谷子毅
 * @date 2022/7/15
 */
@Component
public class LogRecordUtil {

    @Resource
    private LoginLogManager loginLogManager;

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void recordLoginLog(HttpServletRequest request, String username, Integer result, Integer type) {
        LoginLog loginLog = new LoginLog();

        String userAgent = request.getHeader("User-Agent");
        if (userAgent != null) {
            UserAgent agent = UserAgentUtil.parse(userAgent);
            loginLog.setOs(agent.getOs().toString());
            loginLog.setBrowser(agent.getBrowser().toString());
        }

        String ip = IpUtil.getIp(request);
        String address = IpUtil.getAddress(ip);

        loginLog.setIp(ip);
        loginLog.setAddress(address);
        loginLog.setUsername(username);
        loginLog.setType(type);
        loginLog.setResult(result);
        loginLog.setCreateTime(new Date());
        this.saveOne(loginLog);
    }

    @Async
    public void saveOne(LoginLog loginLog) {
        loginLogManager.save(loginLog);
    }
}
