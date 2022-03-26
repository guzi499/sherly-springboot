package com.guzi.upr.service;

import com.guzi.upr.enums.ResultAdminEnum;
import com.guzi.upr.exception.BizException;
import com.guzi.upr.manager.PermissionManager;
import com.guzi.upr.model.admin.Permission;
import com.guzi.upr.model.dto.PermissionInsertDTO;
import com.guzi.upr.model.dto.PermissionUpdateDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 谷子毅
 * @email guzyc@digitalchina.com
 * @date 2022/3/24
 */
@Service
public class PermissionService {

    @Autowired
    private PermissionManager permissionManager;

    /**
     * 权限新增
     * @param dto
     */
    public void saveOne(PermissionInsertDTO dto) {
        // 查重
        Permission one = permissionManager.getByPermissionName(dto.getPermissionName(), dto.getParentId());
        if (one != null) {
            throw new BizException(ResultAdminEnum.PERMISSION_REPEAT);
        }

        Permission permission = new Permission();
        BeanUtils.copyProperties(dto, permission);
        permissionManager.save(permission);
    }

    /**
     * 权限更新
     * @param dto
     */
    public void updateOne(PermissionUpdateDTO dto) {
        // 查重 排除自身
        Permission one = permissionManager.getByPermissionName(dto.getPermissionName(), dto.getParentId());
        if (one != null && !one.getPermissionId().equals(dto.getPermissionId())) {
            throw new BizException(ResultAdminEnum.PERMISSION_REPEAT);
        }

        Permission permission = new Permission();
        BeanUtils.copyProperties(dto, permission);
        permissionManager.updateById(permission);
    }

    /**
     * 权限删除
     * @param id
     */
    public void removeOne(Long id) {
        permissionManager.removeById(id);
    }
}
