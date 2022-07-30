package com.guzi.upr.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guzi.upr.mapper.admin.TenantMapper;
import com.guzi.upr.model.admin.Tenant;
import com.guzi.upr.model.dto.TenantPageDTO;
import com.guzi.upr.util.SherlyLambdaQueryWrapper;
import org.springframework.stereotype.Service;

/**
 * @author 谷子毅
 * @date 2022/3/25
 */
@Service
public class TenantManager extends ServiceImpl<TenantMapper, Tenant> {

    /**
     * 租户查重
     * @param tenantName
     * @param tenantCode
     * @return
     */
    public Tenant getByTenantNameOrTenantCode(String tenantName, String tenantCode) {
        LambdaQueryWrapper<Tenant> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Tenant::getTenantName, tenantName)
                .or()
                .eq(Tenant::getTenantCode, tenantCode);
        return this.getOne(wrapper, false);
    }

    /**
     * 租户条件分页
     * @param page
     * @param dto
     * @return
     */
    public IPage<Tenant> listPage(IPage page, TenantPageDTO dto) {
        SherlyLambdaQueryWrapper<Tenant> wrapper = new SherlyLambdaQueryWrapper<>();
        wrapper.eqIfExist(Tenant::getTenantName, dto.getTenantName())
                .eqIfExist(Tenant::getTenantCode, dto.getTenantCode())
                .orderByDesc(Tenant::getTenantId);
        return this.page(page, wrapper);
    }

    /**
     * 根据租户code查询租户信息
     * @param tenantCode
     * @return
     */
    public Tenant getByTenantCode(String tenantCode) {
        SherlyLambdaQueryWrapper<Tenant> wrapper = new SherlyLambdaQueryWrapper<>();
        wrapper.eq(Tenant::getTenantCode, tenantCode);
        return this.getOne(wrapper, false);
    }
}
