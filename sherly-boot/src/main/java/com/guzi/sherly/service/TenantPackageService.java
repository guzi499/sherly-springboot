package com.guzi.sherly.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.guzi.sherly.admin.tenant.dao.TenantPackageDao;
import com.guzi.sherly.admin.tenant.dao.TenantPackageMenuDao;
import com.guzi.sherly.admin.tenant.dto.*;
import com.guzi.sherly.admin.tenant.model.TenantPackageDO;
import com.guzi.sherly.admin.tenant.model.TenantPackageMenuDO;
import com.guzi.sherly.admin.tenant.vo.TenantPackagePageVO;
import com.guzi.sherly.admin.tenant.vo.TenantPackageSelectVO;
import com.guzi.sherly.common.model.PageResult;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static com.guzi.sherly.common.contants.CommonConstants.ENABLE;

/**
 * @author 谷子毅
 * @date 2022/11/15
 */
@Service
public class TenantPackageService {

    @Resource
    private TenantPackageDao tenantPackageDao;

    @Resource
    private TenantPackageMenuDao tenantPackageMenuDao;

    /**
     * 租户套餐列表
     * @param dto
     * @return
     */
    public PageResult<TenantPackagePageVO> listPage(TenantPackagePageDTO dto) {
        IPage<TenantPackageDO> page = tenantPackageDao.listPage(dto);

        List<TenantPackagePageVO> result = page.getRecords().stream().map(e -> {
            TenantPackagePageVO tenantPackagePageVO = new TenantPackagePageVO();
            BeanUtils.copyProperties(e, tenantPackagePageVO);
            return tenantPackagePageVO;
        }).collect(Collectors.toList());

        return PageResult.build(result, page.getTotal());
    }

    /**
     * 租户套餐新增
     * @param dto
     */
    public void saveOne(TenantPackageInsertDTO dto) {
        TenantPackageDO tenantPackageDO = new TenantPackageDO();
        BeanUtils.copyProperties(dto, tenantPackageDO);
        tenantPackageDO.setEnable(ENABLE);
        tenantPackageDao.save(tenantPackageDO);
    }

    /**
     * 租户套餐删除
     * @param tenantPackageId
     */
    @Transactional(rollbackFor = Exception.class)
    public void removeOne(Long tenantPackageId) {
        tenantPackageMenuDao.removeByMenuId(tenantPackageId);
        tenantPackageDao.removeById(tenantPackageId);
    }

    /**
     * 租户套餐更新
     * @param dto
     */
    public void updateOne(TenantPackageUpdateDTO dto) {
        TenantPackageDO tenantPackageDO = new TenantPackageDO();
        BeanUtils.copyProperties(dto, tenantPackageDO);
        tenantPackageDao.updateById(tenantPackageDO);
    }

    /**
     * 租户套餐菜单列表
     * @param tenantPackageId
     * @return
     */
    public List<Long> listMenu(Long tenantPackageId) {
        List<TenantPackageMenuDO> tenantPackageMenuDOs = tenantPackageMenuDao.getByTenantPackageId(tenantPackageId);
        return tenantPackageMenuDOs.stream().map(TenantPackageMenuDO::getMenuId).collect(Collectors.toList());
    }

    /**
     * 租户套餐菜单更新
     * @param dto
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateMenu(TenantPackageMenuUpdateDTO dto) {
        Long tenantPackageId = dto.getTenantPackageId();
        tenantPackageDao.removeById(tenantPackageId);
        List<TenantPackageMenuDO> tenantPackageMenuDOs = dto.getMenuIds().stream().map(e -> {
            TenantPackageMenuDO tenantPackageMenuDO = new TenantPackageMenuDO();
            tenantPackageMenuDO.setTenantPackageId(tenantPackageId);
            tenantPackageMenuDO.setMenuId(e);
            return tenantPackageMenuDO;
        }).collect(Collectors.toList());
        tenantPackageMenuDao.saveBatch(tenantPackageMenuDOs);
    }

    /**
     * 租户套餐查询
     * @param dto
     * @return
     */
    public List<TenantPackageSelectVO> listAll(TenantPackageSelectDTO dto) {
        List<TenantPackageDO> tenantPackageDOs = tenantPackageDao.listAll(dto);

        return tenantPackageDOs.stream().map(e -> {
            TenantPackageSelectVO tenantPackageSelectVO = new TenantPackageSelectVO();
            BeanUtils.copyProperties(e, tenantPackageSelectVO);
            return tenantPackageSelectVO;
        }).collect(Collectors.toList());
    }

    /**
     * 租户套餐禁用/启用
     * @param tenantPackageId
     * @param enable
     */
    public void banOne(Long tenantPackageId, Integer enable) {
        tenantPackageDao.banOne(tenantPackageId, enable);
    }
}
