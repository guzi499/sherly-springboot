package com.guzi.sherly.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.guzi.sherly.mapper.TenantPackageMenuMapper;
import com.guzi.sherly.model.admin.TenantPackageMenu;
import com.guzi.sherly.modules.mybatisplus.service.SherlyServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 谷子毅
 * @date 2022/11/15
 */
@Service
public class TenantPackageMenuDao extends SherlyServiceImpl<TenantPackageMenuMapper, TenantPackageMenu> {
    /**
     * 根据租户套餐id删除租户套餐菜单
     * @param tenantPackageId
     */
    public void removeByMenuId(Long tenantPackageId) {
        LambdaQueryWrapper<TenantPackageMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TenantPackageMenu::getTenantPackageId, tenantPackageId);
        this.remove(wrapper);
    }

    /**
     * 根据租户套餐id查询租户套餐菜单
     * @param tenantPackageId
     * @return
     */
    public List<TenantPackageMenu> getByTenantPackageId(Long tenantPackageId) {
        LambdaQueryWrapper<TenantPackageMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TenantPackageMenu::getTenantPackageId, tenantPackageId);
        return this.list(wrapper);
    }
}
