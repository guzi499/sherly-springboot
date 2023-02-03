package com.guzi.sherly.manager;

import cn.hutool.extra.mail.Mail;
import cn.hutool.extra.mail.MailAccount;
import com.guzi.sherly.common.exception.BizException;
import com.guzi.sherly.modules.email.dao.EmailConfigDao;
import com.guzi.sherly.modules.email.dto.EmailConfigDTO;
import com.guzi.sherly.modules.email.dto.EmailSendDTO;
import com.guzi.sherly.modules.email.model.EmailConfigDO;
import com.guzi.sherly.modules.email.vo.EmailConfigVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.guzi.sherly.common.exception.enums.AdminErrorEnum.NO_EMAIL_CONFIG;

/**
 * @author 谷子毅
 * @date 2022/6/8
 */
@Service
public class EmailManager {

    @Resource
    private EmailConfigDao emailConfigDao;

    /**
     * 邮箱配置查询
     * @return
     */
    public EmailConfigVO getOne() {
        EmailConfigDO emailConfigDO = emailConfigDao.getOne(null);
        EmailConfigVO emailConfigVO = new EmailConfigVO();
        if (emailConfigDO == null) {
            return emailConfigVO;
        }
        BeanUtils.copyProperties(emailConfigDO, emailConfigVO);
        return emailConfigVO;
    }

    /**
     * 邮箱配置保存或修改
     * @param dto
     */
    public void saveOrUpdate(EmailConfigDTO dto) {
        EmailConfigDO emailConfigDO = new EmailConfigDO();
        BeanUtils.copyProperties(dto, emailConfigDO);
        emailConfigDao.saveOrUpdate(emailConfigDO);
    }

    /**
     * 发送邮件
     * @param dto
     */
    public void send(EmailSendDTO dto) {
        EmailConfigDO emailConfigDO = emailConfigDao.getOne(null);
        if (emailConfigDO == null) {
            throw new BizException(NO_EMAIL_CONFIG);
        }
        // 封装
        MailAccount account = new MailAccount();
        // 设置用户
        String user = emailConfigDO.getSenderEmail().split("@")[0];
        account.setUser(user);
        account.setHost(emailConfigDO.getHost());
        account.setPort(Integer.parseInt(emailConfigDO.getPort()));
        account.setAuth(true);
        account.setPass(emailConfigDO.getPassword());
        account.setFrom(emailConfigDO.getSenderUser() + "<" + emailConfigDO.getSenderEmail() + ">");
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
