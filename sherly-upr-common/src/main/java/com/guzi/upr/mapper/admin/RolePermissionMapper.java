package com.guzi.upr.mapper.admin;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guzi.upr.model.admin.RolePermission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 谷子毅
 * @date 2022/3/22
 */
@Repository
public interface RolePermissionMapper extends BaseMapper<RolePermission> {

    /**
     * 保存角色权限数据
     *
     * @param roleId
     * @param permissionIds
     */
    void saveRolePermission(@Param("roleId") Long roleId, @Param("permissionIds") List<Long> permissionIds);
}
