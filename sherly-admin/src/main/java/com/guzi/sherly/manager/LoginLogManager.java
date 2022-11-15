package com.guzi.sherly.manager;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guzi.sherly.model.PageResult;
import com.guzi.sherly.model.dto.LoginLogPageDTO;
import com.guzi.sherly.model.vo.LoginLogPageVO;
import com.guzi.sherly.modules.log.dao.LoginLogDao;
import com.guzi.sherly.modules.log.model.LoginLog;
import com.guzi.sherly.util.IpUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 谷子毅
 * @date 2022/7/14
 */
@Service
public class LoginLogManager {

    @Resource
    private LoginLogDao loginLogDao;

    /**
     * 日志分页
     * @param dto
     * @return
     */
    public PageResult<LoginLogPageVO> listPage(LoginLogPageDTO dto) {
        Page<LoginLog> page = loginLogDao.listPage(dto);

        List<LoginLogPageVO> result = page.getRecords().stream().map(e -> {
            LoginLogPageVO vo = new LoginLogPageVO();
            BeanUtils.copyProperties(e, vo);
            return vo;
        }).collect(Collectors.toList());

        return PageResult.build(result, page.getTotal());
    }

    /**
     * 日志清空
     */
    public void removeAll() {
        loginLogDao.removeAll();
    }

    /**
     * 日志记录
     * @param request
     * @param username
     * @param result
     * @param type
     */
    @Async
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void saveOne(HttpServletRequest request, String username, Integer result, Integer type) {
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

        loginLogDao.save(loginLog);
    }
}
