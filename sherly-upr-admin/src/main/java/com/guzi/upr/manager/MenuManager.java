package com.guzi.upr.manager;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guzi.upr.mapper.admin.MenuMapper;
import com.guzi.upr.model.admin.Menu;
import org.springframework.stereotype.Service;

/**
 * @author 谷子毅
 * @date 2022/3/25
 */
@Service
public class MenuManager extends ServiceImpl<MenuMapper, Menu> {
}
