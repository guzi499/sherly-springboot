package com.guzi.upr.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guzi.upr.mapper.admin.RoleMapper;
import com.guzi.upr.model.admin.Role;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 谷子毅
 * @date 2022/3/25
 */
@Service
public class RoleManager extends ServiceImpl<RoleMapper, Role> {

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 根据用户id获取角色列表
     *
     * @param userId
     * @return
     */
    public List<Role> listByUserId(Long userId) {
        return roleMapper.listByUserId(userId);
    }

    /**
     * 角色条件分页
     *
     * @param page
     * @param roleName
     * @return
     */
    public IPage<Role> listPage(IPage page, String roleName) {
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(roleName)) {
            wrapper.like(Role::getRoleName, roleName);
        }
        return this.page(page, wrapper);
    }

    /**
     * 角色查重
     *
     * @param roleName
     * @return
     */
    public Role getByRoleName(String roleName) {
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Role::getRoleName, roleName);
        return this.getOne(wrapper);
    }
}
