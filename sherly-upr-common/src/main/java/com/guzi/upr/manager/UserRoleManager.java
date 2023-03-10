package com.guzi.upr.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guzi.upr.mapper.admin.UserRoleMapper;
import com.guzi.upr.model.admin.UserRole;
import com.guzi.upr.util.SherlyLambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 谷子毅
 * @date 2022/3/25
 */
@Service
public class UserRoleManager extends ServiceImpl<UserRoleMapper, UserRole> {

    @Autowired
    private UserRoleMapper userRoleMapper;

    /**
     * 根据角色id删除用户角色数据
     * @param roleId
     */
    public void removeUserRoleByRoleId(Long roleId) {
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getRoleId, roleId);
        this.remove(wrapper);
    }

    /**
     * 根据用户id删除用户角色数据
     * @param userId
     */
    public void removeUserRoleByUserId(Long userId) {
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId, userId);
        this.remove(wrapper);
    }

    /**
     * 用户角色数据保存
     * @param userId
     * @param roleIds
     */
    public void saveUserRole(Long userId, List<Long> roleIds) {
        List<UserRole> list = new ArrayList<>();
        for (Long roleId : roleIds) {
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            list.add(userRole);
        }
        this.saveBatch(list);
    }

    /**
     * 根据角色id查询用户角色数量
     * @return
     * @param roleId
     */
    public Long countByRoleId(Long roleId) {
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getRoleId, roleId);
        return this.count(wrapper);
    }

    /**
     * 根据角色id查询用户角色数据
     * @param userId
     * @return
     */
    public List<UserRole> listByUserId(Long userId) {
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId, userId);
        return this.list(wrapper);
    }

    /**
     * 根据用户ids查询用户角色数据
     * @param roleIds
     * @return
     */
    public List<UserRole> listByRoleIds(List<Long> roleIds) {
        SherlyLambdaQueryWrapper<UserRole> wrapper = new SherlyLambdaQueryWrapper<>();
        wrapper.inIfExist(UserRole::getRoleId, roleIds);
        return this.list(wrapper);
    }
}
