package com.guzi.sherly.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guzi.sherly.mapper.admin.AccountUserMapper;
import com.guzi.sherly.model.admin.AccountUser;
import org.springframework.stereotype.Service;

/**
 * @author 谷子毅
 * @date 2022/4/27
 */
@Service
public class AccountUserManager extends ServiceImpl<AccountUserMapper, AccountUser> {

    /**
     * 根据手机号查询账户用户数据
     * @param phone
     * @return
     */
    public AccountUser getByPhone(String phone) {
        LambdaQueryWrapper<AccountUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AccountUser::getPhone, phone);
        return this.getOne(wrapper, false);
    }
}
