package com.guzi.upr.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guzi.upr.mapper.admin.RoleMapper;
import com.guzi.upr.model.admin.Role;
import com.guzi.upr.model.dto.RolePageDTO;
import com.guzi.upr.model.dto.RoleSelectDTO;
import com.guzi.upr.util.SherlyLambdaQueryWrapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 谷子毅
 * @date 2022/3/25
 */
@Service
public class RoleManager extends ServiceImpl<RoleMapper, Role> {

    /**
     * 角色分页
     * @param dto
     * @return
     */
    public IPage<Role> listPage(RolePageDTO dto) {
        SherlyLambdaQueryWrapper<Role> wrapper = new SherlyLambdaQueryWrapper<>();
        wrapper
                .likeIfExist(Role::getRoleName, dto.getRoleName());
        return this.page(new Page<>(dto.getCurrent(), dto.getSize()), wrapper);
    }

    /**
     * 根据角色名称查询角色数据
     * @param roleName
     * @return
     */
    public Role getByRoleName(String roleName) {
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Role::getRoleName, roleName);
        return this.getOne(wrapper, false);
    }

    /**
     * 角色查询
     * @param dto
     * @return
     */
    public List<Role> listAll(RoleSelectDTO dto) {
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        return this.list(wrapper);
    }
}
