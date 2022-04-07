package com.guzi.upr.mapper.admin;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guzi.upr.model.admin.RoleMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 谷子毅
 * @email guzyc@digitalchina.com
 * @date 2022/3/22
 */
@Repository
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

    /**
     * 保存角色菜单数据
     * @param roleId
     * @param menuIds
     */
    void saveRoleMenu(@Param("roleId") Long roleId, @Param("menuIds") List<Long> menuIds);
}
