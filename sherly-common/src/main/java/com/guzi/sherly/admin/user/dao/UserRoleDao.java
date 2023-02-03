package com.guzi.sherly.admin.user.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.guzi.sherly.admin.user.mapper.UserRoleMapper;
import com.guzi.sherly.admin.user.model.UserRoleDO;
import com.guzi.sherly.modules.mybatisplus.service.SherlyServiceImpl;
import com.guzi.sherly.modules.mybatisplus.wrapper.SherlyLambdaQueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 谷子毅
 * @date 2022/3/25
 */
@Service
public class UserRoleDao extends SherlyServiceImpl<UserRoleMapper, UserRoleDO> {

    @Resource
    private UserRoleMapper userRoleMapper;

    /**
     * 根据角色id删除用户角色数据
     * @param roleId
     */
    public void removeUserRoleByRoleId(Long roleId) {
        LambdaQueryWrapper<UserRoleDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRoleDO::getRoleId, roleId);
        this.remove(wrapper);
    }

    /**
     * 根据用户id删除用户角色数据
     * @param userId
     */
    public void removeUserRoleByUserId(Long userId) {
        LambdaQueryWrapper<UserRoleDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRoleDO::getUserId, userId);
        this.remove(wrapper);
    }

    /**
     * 用户角色数据保存
     * @param userId
     * @param roleIds
     */
    public void saveUserRole(Long userId, List<Long> roleIds) {
        List<UserRoleDO> list = new ArrayList<>();
        for (Long roleId : roleIds) {
            UserRoleDO userRoleDO = new UserRoleDO();
            userRoleDO.setUserId(userId);
            userRoleDO.setRoleId(roleId);
            list.add(userRoleDO);
        }
        this.saveBatch(list);
    }

    /**
     * 根据角色id查询用户角色数量
     * @return
     * @param roleId
     */
    public Long countByRoleId(Long roleId) {
        LambdaQueryWrapper<UserRoleDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRoleDO::getRoleId, roleId);
        return this.count(wrapper);
    }

    /**
     * 根据角色id查询用户角色数据
     * @param userId
     * @return
     */
    public List<UserRoleDO> listByUserId(Long userId) {
        LambdaQueryWrapper<UserRoleDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRoleDO::getUserId, userId);
        return this.list(wrapper);
    }

    /**
     * 根据用户ids查询用户角色数据
     * @param roleIds
     * @return
     */
    public List<UserRoleDO> listByRoleIds(List<Long> roleIds) {
        SherlyLambdaQueryWrapper<UserRoleDO> wrapper = new SherlyLambdaQueryWrapper<>();
        wrapper.inIfExist(UserRoleDO::getRoleId, roleIds);
        return this.list(wrapper);
    }
}
