package com.guzi.upr.service;

import com.guzi.upr.enums.ResultAdminEnum;
import com.guzi.upr.exception.BizException;
import com.guzi.upr.manager.PermissionManager;
import com.guzi.upr.model.admin.Permission;
import com.guzi.upr.model.dto.PermissionInsertDTO;
import com.guzi.upr.model.dto.PermissionUpdateDTO;
import com.guzi.upr.model.vo.PermissionVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 谷子毅
 * @date 2022/3/24
 */
@Service
public class PermissionService {

    @Autowired
    private PermissionManager permissionManager;


    /**
     * 权限查询
     * @return
     */
    public List<PermissionVO> listTree() {

        List<Permission> list = permissionManager.list();

        // 对象转换成vo类型
        List<PermissionVO> all = list.stream().map(e -> {
            PermissionVO vo = new PermissionVO();
            BeanUtils.copyProperties(e, vo);
            return vo;
        }).collect(Collectors.toList());

        // 拼装子结点并返回
        return all.stream()
                .filter(e -> e.getParentId() == 0)
                .peek(e -> e.setChildren(getChildren(e, all)))
                .collect(Collectors.toList());
    }

    /**
     * 递归拼装子结点
     * @param parent
     * @param all
     * @return
     */
    private List<PermissionVO> getChildren(PermissionVO parent, List<PermissionVO> all) {
        return all.stream()
                .filter(e -> e.getParentId().equals(parent.getPermissionId()))
                .peek(e -> e.setChildren(getChildren(e, all)))
                .collect(Collectors.toList());
    }

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
     * @param permissionId
     */
    public void removeOne(Long permissionId) {
        permissionManager.removeById(permissionId);
    }
}
