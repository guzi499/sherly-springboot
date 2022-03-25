package com.guzi.upr.manager;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guzi.upr.mapper.admin.RoleMapper;
import com.guzi.upr.model.admin.Role;
import org.springframework.stereotype.Service;

/**
 * @author 谷子毅
 * @date 2022/3/25
 */
@Service
public class RoleManager extends ServiceImpl<RoleMapper, Role> {
}
