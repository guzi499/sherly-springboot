package com.guzi.sherly.admin.accountuser.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.guzi.sherly.admin.accountuser.mapper.AccountUserMapper;
import com.guzi.sherly.admin.accountuser.model.AccountUserDO;
import com.guzi.sherly.modules.mybatisplus.service.SherlyServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author 谷子毅
 * @date 2022/4/27
 */
@Service
public class AccountUserDao extends SherlyServiceImpl<AccountUserMapper, AccountUserDO> {

    /**
     * 根据手机号查询账户用户数据
     * @param phone
     * @return
     */
    public AccountUserDO getByPhone(String phone) {
        LambdaQueryWrapper<AccountUserDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AccountUserDO::getPhone, phone);
        return this.getOne(wrapper, false);
    }
}
