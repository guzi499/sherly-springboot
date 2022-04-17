package com.guzi.upr.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guzi.upr.mapper.admin.UserMapper;
import com.guzi.upr.model.admin.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 谷子毅
 * @date 2022/3/25
 */
@Service
public class UserManager extends ServiceImpl<UserMapper, User> {

    @Autowired
    private UserMapper userMapper;

    /**
     * 根据手机号和密码查询用户
     *
     * @param phone
     * @param password
     * @return
     */
    public User getOneByPhoneAndPwd(String phone, String password) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getPhone, phone)
                .eq(User::getPassword, password);

        return userMapper.selectOne(wrapper);
    }

    /**
     * 更新用户部门
     *
     * @param departmentId
     */
    public void updateDepartmentId(Long departmentId) {
        LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(User::getDepartmentId, null)
                .eq(User::getDepartmentId, departmentId);
        this.update(wrapper);
    }

    /**
     * 用户禁用/启用
     *
     * @param userId
     * @param enable
     */
    public void banOne(Long userId, Integer enable) {
        LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(User::getEnable, enable)
                .eq(User::getUserId, userId);
        this.update(wrapper);
    }

    /**
     * 根据手机号获取用户
     * @param phone
     * @return
     */
    public User getByPhone(String phone) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getPhone, phone);
        return this.getOne(wrapper);
    }
}
