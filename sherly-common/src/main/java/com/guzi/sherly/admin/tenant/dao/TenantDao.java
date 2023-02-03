package com.guzi.sherly.admin.tenant.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guzi.sherly.admin.tenant.dto.TenantPageDTO;
import com.guzi.sherly.admin.tenant.mapper.TenantMapper;
import com.guzi.sherly.admin.tenant.model.TenantDO;
import com.guzi.sherly.modules.mybatisplus.service.SherlyServiceImpl;
import com.guzi.sherly.modules.mybatisplus.wrapper.SherlyLambdaQueryWrapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author 谷子毅
 * @date 2022/3/25
 */
@Service
public class TenantDao extends SherlyServiceImpl<TenantMapper, TenantDO> {

    /**
     * 租户查重
     * @param tenantName
     * @param tenantCode
     * @return
     */
    public TenantDO getByTenantNameOrTenantCode(String tenantName, String tenantCode) {
        LambdaQueryWrapper<TenantDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TenantDO::getTenantName, tenantName)
                .or()
                .eq(TenantDO::getTenantCode, tenantCode);
        return this.getOne(wrapper, false);
    }

    /**
     * 租户条件分页
     *
     * @param dto
     * @return
     */
    public IPage<TenantDO> listPage(TenantPageDTO dto) {
        SherlyLambdaQueryWrapper<TenantDO> wrapper = new SherlyLambdaQueryWrapper<>();
        wrapper
                .likeIfExist(TenantDO::getTenantName, dto.getTenantName())
                .likeIfExist(TenantDO::getTenantCode, dto.getTenantCode())
                .likeIfExist(TenantDO::getContactUser, dto.getContactUser())
                .likeIfExist(TenantDO::getContactPhone, dto.getContactPhone())
                .betweenIfExist(TenantDO::getExpireTime, dto.getBeginExpireTime(), dto.getEndExpireTime())
                .betweenIfExist(TenantDO::getUserLimit, dto.getBeginUserLimit(), dto.getEndUserLimit())
                .betweenIfExist(TenantDO::getCreateTime, dto.getBeginTime(), dto.getEndTime())
                .orderByDesc(TenantDO::getTenantId);
        return this.page(new Page<>(dto.getCurrent(), dto.getSize()), wrapper);
    }

    /**
     * 根据租户code查询租户信息
     * @param tenantCode
     * @return
     */
    public TenantDO getByTenantCode(String tenantCode) {
        SherlyLambdaQueryWrapper<TenantDO> wrapper = new SherlyLambdaQueryWrapper<>();
        wrapper.eq(TenantDO::getTenantCode, tenantCode);
        return this.getOne(wrapper, false);
    }

    /**
     * 根据租户codes查询未到期的租户数据
     * @param tenantCodes
     * @return
     */
    public List<TenantDO> listAvailableByTenantCodes(List<String> tenantCodes) {
        SherlyLambdaQueryWrapper<TenantDO> wrapper = new SherlyLambdaQueryWrapper<>();
        wrapper.inIfExist(TenantDO::getTenantCode, tenantCodes)
                .geIfExist(TenantDO::getExpireTime, new Date());
        return this.list(wrapper);
    }
}
