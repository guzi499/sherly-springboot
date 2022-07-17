package com.guzi.upr.util;

import com.guzi.upr.log.model.LoginLog;
import com.guzi.upr.manager.LoginLogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 谷子毅
 * @date 2022/7/15
 */
@Component
public class LogRecordUtil {

    @Autowired
    private LoginLogManager loginLogManager;

    public void recordLoginLog(String username, Integer result, Integer type) {
        LoginLog loginLog = new LoginLog();
        loginLog.setUsername(username);
        loginLog.setType(type);
        loginLog.setResult(result);
        loginLogManager.save(loginLog);
    }
}
