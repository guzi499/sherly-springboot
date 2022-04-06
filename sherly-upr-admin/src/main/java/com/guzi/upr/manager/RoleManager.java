package com.guzi.upr.manager;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guzi.upr.mapper.admin.RoleMapper;
import com.guzi.upr.model.admin.Role;
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
     * @param userId
     * @return
     */
    public List<Role> listByUserId(Long userId) {
        return roleMapper.listByUserId(userId);
    }
}
