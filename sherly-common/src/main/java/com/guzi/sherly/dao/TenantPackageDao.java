package com.guzi.sherly.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guzi.sherly.mapper.TenantPackageMapper;
import com.guzi.sherly.model.admin.TenantPackage;
import com.guzi.sherly.model.dto.TenantPackagePageDTO;
import com.guzi.sherly.modules.mybatisplus.service.SherlyServiceImpl;
import com.guzi.sherly.modules.mybatisplus.wrapper.SherlyLambdaQueryWrapper;
import org.springframework.stereotype.Service;

/**
 * @author 谷子毅
 * @date 2022/11/15
 */
@Service
public class TenantPackageDao extends SherlyServiceImpl<TenantPackageMapper, TenantPackage> {

    public IPage<TenantPackage> listPage(TenantPackagePageDTO dto) {
        SherlyLambdaQueryWrapper<TenantPackage> wrapper = new SherlyLambdaQueryWrapper<>();
        return this.page(new Page<>(dto.getCurrent(), dto.getSize()), wrapper);
    }
}
