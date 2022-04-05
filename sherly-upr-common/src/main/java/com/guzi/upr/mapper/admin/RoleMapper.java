package com.guzi.upr.mapper.admin;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guzi.upr.model.admin.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 谷子毅
 * @email guzyc@digitalchina.com
 * @date 2022/3/22
 */
@Repository
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> listByUserId(Long userId);
}
