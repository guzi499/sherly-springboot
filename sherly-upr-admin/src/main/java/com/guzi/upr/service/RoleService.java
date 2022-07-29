package com.guzi.upr.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guzi.upr.manager.RoleManager;
import com.guzi.upr.manager.RoleMenuManager;
import com.guzi.upr.manager.UserRoleManager;
import com.guzi.upr.model.PageResult;
import com.guzi.upr.model.admin.Role;
import com.guzi.upr.model.admin.RoleMenu;
import com.guzi.upr.model.dto.RoleInsertDTO;
import com.guzi.upr.model.dto.RolePageDTO;
import com.guzi.upr.model.dto.RoleSelectDTO;
import com.guzi.upr.model.dto.RoleUpdateDTO;
import com.guzi.upr.model.exception.BizException;
import com.guzi.upr.model.vo.RolePageVO;
import com.guzi.upr.model.vo.RoleSelectVO;
import com.guzi.upr.model.vo.RoleVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.guzi.upr.model.exception.enums.AdminErrorEnum.ROLE_BOUND_USER;
import static com.guzi.upr.model.exception.enums.AdminErrorEnum.ROLE_REPEAT;

/**
 * @author 谷子毅
 * @date 2022/3/24
 */
@Service
public class RoleService {
    @Autowired
    private RoleManager roleManager;

    @Autowired
    private RoleMenuManager roleMenuManager;

    @Autowired
    private UserRoleManager userRoleManager;

    /**
     * 角色分页
     * @param dto
     * @return
     */
    public PageResult listPage(RolePageDTO dto) {
        // 分页查询
        IPage<Role> page = roleManager.listPage(new Page<>(dto.getCurrent(), dto.getSize()), dto.getRoleName());

        // 对象转换成vo类型
        List<RolePageVO> result = page.getRecords().stream().map(e -> {
            RolePageVO vo = new RolePageVO();
            BeanUtils.copyProperties(e, vo);
            return vo;
        }).collect(Collectors.toList());

        return PageResult.build(result, page.getCurrent(), page.getSize(), page.getTotal());
    }

    /**
     * 角色详情
     * @param roleId
     * @return
     */
    public RoleVO getOne(Long roleId) {
        Role role = roleManager.getById(roleId);

        // 查询菜单
        List<RoleMenu> roleMenus = roleMenuManager.listByRoleId(roleId);
        List<Long> menuIds = roleMenus.stream().map(RoleMenu::getMenuId).collect(Collectors.toList());

        // 组装vo
        RoleVO vo = new RoleVO();
        BeanUtils.copyProperties(role, vo);
        vo.setMenuIds(menuIds);

        return vo;
    }

    /**
     * 角色新增
     * @param dto
     */
    public void saveOne(RoleInsertDTO dto) {
        // 去重
        Role one = roleManager.getByRoleName(dto.getRoleName());
        if (one != null) {
            throw new BizException(ROLE_REPEAT);
        }

        Role role = new Role();
        BeanUtils.copyProperties(dto, role);
        roleManager.save(role);
    }

    /**
     * 角色更新
     * @param dto
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateOne(RoleUpdateDTO dto) {
        // 去重
        Role one = roleManager.getByRoleName(dto.getRoleName());
        // 如果待修改名称已存在且不为自身
        if (one != null && !Objects.equals(one.getRoleId(), dto.getRoleId())) {
            throw new BizException(ROLE_REPEAT);
        }

        Role role = new Role();
        BeanUtils.copyProperties(dto, role);
        roleManager.updateById(role);

        // 先全部删除角色菜单数据
        roleMenuManager.removeRoleMenuByRoleId(dto.getRoleId());

        // 再保存角色菜单数据
        if (!CollectionUtils.isEmpty(dto.getMenuIds())) {
            roleMenuManager.saveRoleMenu(dto.getRoleId(), dto.getMenuIds());
        }
    }

    /**
     * 角色删除
     * @param roleId
     */
    @Transactional(rollbackFor = Exception.class)
    public void removeOne(Long roleId) {
        if (userRoleManager.countByRoleId(roleId) > 0) {
            throw new BizException(ROLE_BOUND_USER);
        }

        roleManager.removeById(roleId);

        // 删除角色菜单、用户角色数据
        roleMenuManager.removeRoleMenuByRoleId(roleId);
        userRoleManager.removeUserRoleByRoleId(roleId);
    }

    /**
     * 角色查询
     * @param dto
     * @return
     */
    public List<RoleSelectVO> listAll(RoleSelectDTO dto) {
        List<Role> roles = roleManager.listAll(dto);

        return roles.stream().map(e -> {
            RoleSelectVO vo = new RoleSelectVO();
            BeanUtils.copyProperties(e, vo);
            return vo;
        }).collect(Collectors.toList());
    }
}
