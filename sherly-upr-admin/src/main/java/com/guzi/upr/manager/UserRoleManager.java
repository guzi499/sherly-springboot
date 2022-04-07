package com.guzi.upr.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guzi.upr.mapper.admin.UserRoleMapper;
import com.guzi.upr.model.admin.UserRole;
import org.springframework.stereotype.Service;

/**
 * @author 谷子毅
 * @date 2022/3/25
 */
@Service
public class UserRoleManager extends ServiceImpl<UserRoleMapper, UserRole> {


    /**
     * 根据角色id删除用户角色数据
     *
     * @param roleId
     */
    public void removeUserRoleByRoleId(Long roleId) {
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getRoleId, roleId);
        this.remove(wrapper);
    }
}
