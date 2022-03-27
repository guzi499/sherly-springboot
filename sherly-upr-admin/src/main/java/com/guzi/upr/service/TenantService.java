package com.guzi.upr.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.guzi.upr.enums.ResultAdminEnum;
import com.guzi.upr.exception.BizException;
import com.guzi.upr.manager.TenantManager;
import com.guzi.upr.model.PageResult;
import com.guzi.upr.model.admin.Tenant;
import com.guzi.upr.model.dto.TenantInsertDTO;
import com.guzi.upr.model.dto.TenantPageDTO;
import com.guzi.upr.model.dto.TenantUpdateDTO;
import com.guzi.upr.model.vo.TenantPageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 谷子毅
 * @email guzyc@digitalchina.com
 * @date 2022/3/24
 */
@Service
public class TenantService {

    @Autowired
    private TenantManager tenantManager;

    /**
     * 租户条件分页
     * @param dto
     * @return
     */
    public PageResult listPage(TenantPageDTO dto) {

        IPage page = tenantManager.listPage(dto);

        List<TenantPageVO> result = new ArrayList<>();
        page.getRecords().forEach(e -> {
            TenantPageVO vo = new TenantPageVO();
            BeanUtils.copyProperties(e, vo);
            result.add(vo);
        });

        return PageResult.build(result, page.getCurrent(), page.getSize(), page.getTotal());
    }

    /**
     * 租户新增
     * @param dto
     */
    public void saveOne(TenantInsertDTO dto) {
        // 查重
        Tenant one = tenantManager.getByTenantNameOrTenantCode(dto.getTenantName(), dto.getTenantCode());
        if (one != null) {
            throw new BizException(ResultAdminEnum.TENANT_REPEAT);
        }

        Tenant tenant = new Tenant();
        BeanUtils.copyProperties(dto, tenant);
        tenantManager.save(tenant);
    }

    /**
     * 租户更新
     * @param dto
     */
    public void updateOne(TenantUpdateDTO dto) {
        Tenant tenant = new Tenant();
        BeanUtils.copyProperties(dto, tenant);
        tenantManager.updateById(tenant);
    }

    /**
     * 租户删除
     * @param id
     */
    public void removeOne(Long id) {
        tenantManager.removeById(id);
    }
}
