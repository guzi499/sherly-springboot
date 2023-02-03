package com.guzi.sherly.admin.role.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guzi.sherly.admin.role.dto.RolePageDTO;
import com.guzi.sherly.admin.role.dto.RoleSelectDTO;
import com.guzi.sherly.admin.role.mapper.RoleMapper;
import com.guzi.sherly.admin.role.model.RoleDO;
import com.guzi.sherly.modules.mybatisplus.service.SherlyServiceImpl;
import com.guzi.sherly.modules.mybatisplus.wrapper.SherlyLambdaQueryWrapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 谷子毅
 * @date 2022/3/25
 */
@Service
public class RoleDao extends SherlyServiceImpl<RoleMapper, RoleDO> {

    /**
     * 角色分页
     * @param dto
     * @return
     */
    public IPage<RoleDO> listPage(RolePageDTO dto) {
        SherlyLambdaQueryWrapper<RoleDO> wrapper = new SherlyLambdaQueryWrapper<>();
        wrapper
                .likeIfExist(RoleDO::getRoleName, dto.getRoleName());
        return this.page(new Page<>(dto.getCurrent(), dto.getSize()), wrapper);
    }

    /**
     * 根据角色名称查询角色数据
     * @param roleName
     * @return
     */
    public RoleDO getByRoleName(String roleName) {
        LambdaQueryWrapper<RoleDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleDO::getRoleName, roleName);
        return this.getOne(wrapper, false);
    }

    /**
     * 角色查询
     * @param dto
     * @return
     */
    public List<RoleDO> listAll(RoleSelectDTO dto) {
        LambdaQueryWrapper<RoleDO> wrapper = new LambdaQueryWrapper<>();
        return this.list(wrapper);
    }
}
