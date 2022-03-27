package com.guzi.upr.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guzi.upr.mapper.admin.TenantMapper;
import com.guzi.upr.model.admin.Tenant;
import com.guzi.upr.model.dto.TenantPageDTO;
import org.apache.commons.lang3.StringUtils;
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
        return this.getOne(wrapper);
    }

    /**
     * 租户条件分页
     * @param dto
     * @return
     */
    public IPage listPage(TenantPageDTO dto) {
        LambdaQueryWrapper<Tenant> wrapper = new LambdaQueryWrapper<>();

        if (StringUtils.isNoneBlank(dto.getTenantCode())) {
            wrapper.eq(Tenant::getTenantCode, dto.getTenantCode());
        }

        if (StringUtils.isNoneBlank(dto.getTenantName())) {
            wrapper.eq(Tenant::getTenantName, dto.getTenantName());
        }

        return page(dto.getPage(), wrapper);
    }
}
