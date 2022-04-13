package com.guzi.upr.mapper.admin;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guzi.upr.model.admin.Menu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 谷子毅
 * @date 2022/3/22
 */
@Repository
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 根据角色ids获取菜单列表
     *
     * @param roleIds
     * @return
     */
    List<Menu> listByRoleIds(@Param("roleIds") List<Long> roleIds);
}
