package com.guzi.upr.service;

import cn.hutool.extra.mail.Mail;
import cn.hutool.extra.mail.MailAccount;
import com.guzi.upr.manager.EmailConfigManager;
import com.guzi.upr.model.admin.EmailConfig;
import com.guzi.upr.model.dto.EmailConfigDTO;
import com.guzi.upr.model.dto.EmailSendDTO;
import com.guzi.upr.model.exception.BizException;
import com.guzi.upr.model.vo.EmailConfigVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.guzi.upr.model.exception.enums.AdminErrorEnum.NO_EMAIL_CONFIG;

/**
 * @author 谷子毅
 * @date 2022/6/8
 */
@Service
public class EmailService {

    @Autowired
    private EmailConfigManager emailConfigManager;

    /**
     * 邮箱配置查询
     * @return
     */
    public EmailConfigVO getOne() {
        EmailConfig emailConfig = emailConfigManager.getOne(null);
        EmailConfigVO emailConfigVO = new EmailConfigVO();
        if (emailConfig == null) {
            return emailConfigVO;
        }
        BeanUtils.copyProperties(emailConfig, emailConfigVO);
        return emailConfigVO;
    }

    /**
     * 邮箱配置保存或修改
     * @param dto
     */
    public void saveOrUpdate(EmailConfigDTO dto) {
        EmailConfig emailConfig = new EmailConfig();
        BeanUtils.copyProperties(dto, emailConfig);
        emailConfigManager.saveOrUpdate(emailConfig);
    }

    /**
     * 发送邮件
     * @param dto
     */
    public void send(EmailSendDTO dto) {
        EmailConfig emailConfig = emailConfigManager.getOne(null);
        if (emailConfig == null) {
            throw new BizException(NO_EMAIL_CONFIG);
        }
        // 封装
        MailAccount account = new MailAccount();
        // 设置用户
        String user = emailConfig.getSenderEmail().split("@")[0];
        account.setUser(user);
        account.setHost(emailConfig.getHost());
        account.setPort(Integer.parseInt(emailConfig.getPort()));
        account.setAuth(true);
        account.setPass(emailConfig.getPassword());
        account.setFrom(emailConfig.getSenderUser() + "<" + emailConfig.getSenderEmail() + ">");
        // ssl方式发送
        account.setSslEnable(true);
        // 使用STARTTLS安全连接
        account.setStarttlsEnable(true);
        String content = dto.getContent();
        // 发送
        int size = dto.getTos().size();
        Mail.create(account)
                .setTos(dto.getTos().toArray(new String[size]))
                .setTitle(dto.getSubject())
                .setContent(content)
                .setHtml(true)
                //关闭session
                .setUseGlobalSession(false)
                .send();
    }
}
