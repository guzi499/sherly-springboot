package com.guzi.sherly.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.guzi.sherly.dao.TenantPackageDao;
import com.guzi.sherly.dao.TenantPackageMenuDao;
import com.guzi.sherly.model.PageResult;
import com.guzi.sherly.model.admin.TenantPackage;
import com.guzi.sherly.model.admin.TenantPackageMenu;
import com.guzi.sherly.model.dto.*;
import com.guzi.sherly.model.vo.TenantPackagePageVO;
import com.guzi.sherly.model.vo.TenantPackageSelectVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static com.guzi.sherly.model.contants.CommonConstants.ENABLE;

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
        IPage<TenantPackage> page = tenantPackageDao.listPage(dto);

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
        TenantPackage tenantPackage = new TenantPackage();
        BeanUtils.copyProperties(dto, tenantPackage);
        tenantPackage.setEnable(ENABLE);
        tenantPackageDao.save(tenantPackage);
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
        TenantPackage tenantPackage = new TenantPackage();
        BeanUtils.copyProperties(dto, tenantPackage);
        tenantPackageDao.updateById(tenantPackage);
    }

    /**
     * 租户套餐菜单列表
     * @param tenantPackageId
     * @return
     */
    public List<Long> listMenu(Long tenantPackageId) {
        List<TenantPackageMenu> tenantPackageMenus = tenantPackageMenuDao.getByTenantPackageId(tenantPackageId);
        return tenantPackageMenus.stream().map(TenantPackageMenu::getMenuId).collect(Collectors.toList());
    }

    /**
     * 租户套餐菜单更新
     * @param dto
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateMenu(TenantPackageMenuUpdateDTO dto) {
        Long tenantPackageId = dto.getTenantPackageId();
        tenantPackageDao.removeById(tenantPackageId);
        List<TenantPackageMenu> tenantPackageMenus = dto.getMenuIds().stream().map(e -> {
            TenantPackageMenu tenantPackageMenu = new TenantPackageMenu();
            tenantPackageMenu.setTenantPackageId(tenantPackageId);
            tenantPackageMenu.setMenuId(e);
            return tenantPackageMenu;
        }).collect(Collectors.toList());
        tenantPackageMenuDao.saveBatch(tenantPackageMenus);
    }

    /**
     * 租户套餐查询
     * @param dto
     * @return
     */
    public List<TenantPackageSelectVO> listAll(TenantPackageSelectDTO dto) {
        List<TenantPackage> tenantPackages = tenantPackageDao.listAll(dto);

        return tenantPackages.stream().map(e -> {
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
