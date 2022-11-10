package com.guzi.sherly.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.guzi.sherly.mapper.AccountUserMapper;
import com.guzi.sherly.model.admin.AccountUser;
import com.guzi.sherly.modules.mybatisplus.service.SherlyServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author 谷子毅
 * @date 2022/4/27
 */
@Service
public class AccountUserDao extends SherlyServiceImpl<AccountUserMapper, AccountUser> {

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
