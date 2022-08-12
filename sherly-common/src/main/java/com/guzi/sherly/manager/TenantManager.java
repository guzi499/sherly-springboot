package com.guzi.sherly.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guzi.sherly.mapper.TenantMapper;
import com.guzi.sherly.model.admin.Tenant;
import com.guzi.sherly.model.dto.TenantPageDTO;
import com.guzi.sherly.util.SherlyLambdaQueryWrapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
     *
     * @param dto
     * @return
     */
    public IPage<Tenant> listPage(TenantPageDTO dto) {
        SherlyLambdaQueryWrapper<Tenant> wrapper = new SherlyLambdaQueryWrapper<>();
        wrapper
                .likeIfExist(Tenant::getTenantName, dto.getTenantName())
                .likeIfExist(Tenant::getTenantCode, dto.getTenantCode())
                .likeIfExist(Tenant::getContactUser, dto.getContactUser())
                .likeIfExist(Tenant::getContactPhone, dto.getContactPhone())
                .betweenIfExist(Tenant::getExpireTime, dto.getBeginExpireTime(), dto.getEndExpireTime())
                .betweenIfExist(Tenant::getUserLimit, dto.getBeginUserLimit(), dto.getEndUserLimit())
                .betweenIfExist(Tenant::getCreateTime, dto.getBeginTime(), dto.getEndTime())
                .orderByDesc(Tenant::getTenantId);
        return this.page(new Page<>(dto.getCurrent(), dto.getSize()), wrapper);
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

    /**
     * 根据租户codes查询未到期的租户数据
     * @param tenantCodes
     * @return
     */
    public List<Tenant> listAvailableByTenantCodes(List<String> tenantCodes) {
        SherlyLambdaQueryWrapper<Tenant> wrapper = new SherlyLambdaQueryWrapper<>();
        wrapper.inIfExist(Tenant::getTenantCode, tenantCodes)
                .geIfExist(Tenant::getExpireTime, new Date());
        return this.list(wrapper);
    }
}
