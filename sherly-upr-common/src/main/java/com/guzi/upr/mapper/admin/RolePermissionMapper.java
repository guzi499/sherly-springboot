package com.guzi.upr.mapper.admin;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guzi.upr.model.admin.RolePermission;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 谷子毅
 * @email guzyc@digitalchina.com
 * @date 2022/3/22
 */
@Repository
public interface RolePermissionMapper extends BaseMapper<RolePermission> {
    void saveRolePermission(Long roleId, List<Long> permissionsIds);
}
