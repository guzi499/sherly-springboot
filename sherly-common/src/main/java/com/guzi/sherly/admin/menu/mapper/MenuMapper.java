package com.guzi.sherly.admin.menu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guzi.sherly.admin.menu.model.MenuDO;
import org.apache.ibatis.annotations.Delete;
import org.springframework.stereotype.Repository;

/**
 * @author 谷子毅
 * @date 2022/3/22
 */
@Repository
public interface MenuMapper extends BaseMapper<MenuDO> {

    /**
     * 菜单清空
     */
    @Delete("truncate table sys_menu")
    void removeAll();
}
