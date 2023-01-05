package com.guzi.sherly.admin.tenant.dao;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guzi.sherly.admin.tenant.dto.TenantPackagePageDTO;
import com.guzi.sherly.admin.tenant.dto.TenantPackageSelectDTO;
import com.guzi.sherly.admin.tenant.mapper.TenantPackageMapper;
import com.guzi.sherly.admin.tenant.model.TenantPackage;
import com.guzi.sherly.modules.mybatisplus.service.SherlyServiceImpl;
import com.guzi.sherly.modules.mybatisplus.wrapper.SherlyLambdaQueryWrapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 谷子毅
 * @date 2022/11/15
 */
@Service
public class TenantPackageDao extends SherlyServiceImpl<TenantPackageMapper, TenantPackage> {

    public IPage<TenantPackage> listPage(TenantPackagePageDTO dto) {
        SherlyLambdaQueryWrapper<TenantPackage> wrapper = new SherlyLambdaQueryWrapper<>();
        wrapper.likeIfExist(TenantPackage::getTenantPackageName, dto.getTenantPackageName());
        return this.page(new Page<>(dto.getCurrent(), dto.getSize()), wrapper);
    }

    public List<TenantPackage> listAll(TenantPackageSelectDTO dto) {
        SherlyLambdaQueryWrapper<TenantPackage> wrapper = new SherlyLambdaQueryWrapper<>();
        wrapper.eqIfExist(TenantPackage::getEnable, dto.getEnable());
        return this.list(wrapper);
    }

    public void banOne(Long tenantPackageId, Integer enable) {
        LambdaUpdateWrapper<TenantPackage> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(TenantPackage::getEnable, enable)
                .eq(TenantPackage::getTenantPackageId, tenantPackageId);
        this.update(wrapper);
    }
}
