package com.guzi.sherly.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.guzi.sherly.admin.role.dao.RoleDao;
import com.guzi.sherly.admin.role.dao.RoleMenuDao;
import com.guzi.sherly.admin.role.dto.RoleInsertDTO;
import com.guzi.sherly.admin.role.dto.RolePageDTO;
import com.guzi.sherly.admin.role.dto.RoleSelectDTO;
import com.guzi.sherly.admin.role.dto.RoleUpdateDTO;
import com.guzi.sherly.admin.role.model.Role;
import com.guzi.sherly.admin.role.model.RoleMenu;
import com.guzi.sherly.admin.role.vo.RolePageVO;
import com.guzi.sherly.admin.role.vo.RoleSelectVO;
import com.guzi.sherly.admin.role.vo.RoleVO;
import com.guzi.sherly.admin.user.dao.UserRoleDao;
import com.guzi.sherly.common.exception.BizException;
import com.guzi.sherly.common.model.PageResult;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.guzi.sherly.common.exception.enums.AdminErrorEnum.*;

/**
 * @author 谷子毅
 * @date 2022/3/24
 */
@Service
public class RoleService {
    @Resource
    private RoleDao roleDao;

    @Resource
    private RoleMenuDao roleMenuDao;

    @Resource
    private UserRoleDao userRoleDao;

    /**
     * 角色分页
     * @param dto
     * @return
     */
    public PageResult<RolePageVO> listPage(RolePageDTO dto) {
        // 分页查询
        IPage<Role> page = roleDao.listPage(dto);

        // 对象转换成vo类型
        List<RolePageVO> result = page.getRecords().stream().map(e -> {
            RolePageVO vo = new RolePageVO();
            BeanUtils.copyProperties(e, vo);
            return vo;
        }).collect(Collectors.toList());

        return PageResult.build(result, page.getTotal());
    }

    /**
     * 角色详情
     * @param roleId
     * @return
     */
    public RoleVO getOne(Long roleId) {
        Role role = roleDao.getById(roleId);

        // 查询菜单
        List<RoleMenu> roleMenus = roleMenuDao.listByRoleId(roleId);
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
        Role one = roleDao.getByRoleName(dto.getRoleName());
        if (one != null) {
            throw new BizException(ROLE_REPEAT);
        }

        Role role = new Role();
        BeanUtils.copyProperties(dto, role);
        roleDao.save(role);
    }

    /**
     * 角色更新
     * @param dto
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateOne(RoleUpdateDTO dto) {
        // 去重
        Role one = roleDao.getByRoleName(dto.getRoleName());
        // 如果待修改名称已存在且不为自身
        if (one != null && !Objects.equals(one.getRoleId(), dto.getRoleId())) {
            throw new BizException(ROLE_REPEAT);
        }

        Role role = new Role();
        BeanUtils.copyProperties(dto, role);
        roleDao.updateById(role);

        // 先全部删除角色菜单数据
        roleMenuDao.removeByRoleId(dto.getRoleId());

        // 再保存角色菜单数据
        if (!CollectionUtils.isEmpty(dto.getMenuIds())) {
            roleMenuDao.saveRoleMenu(dto.getRoleId(), dto.getMenuIds());
        }
    }

    /**
     * 角色删除
     * @param roleId
     */
    @Transactional(rollbackFor = Exception.class)
    public void removeOne(Long roleId) {
        if (Objects.equals(roleId, 1L)) {
            throw new BizException(DELETE_ROLE_ERROR);
        }
        if (userRoleDao.countByRoleId(roleId) > 0) {
            throw new BizException(ROLE_BOUND_USER);
        }

        roleDao.removeById(roleId);

        // 删除角色菜单、用户角色数据
        roleMenuDao.removeByRoleId(roleId);
        userRoleDao.removeUserRoleByRoleId(roleId);
    }

    /**
     * 角色查询
     * @param dto
     * @return
     */
    public List<RoleSelectVO> listAll(RoleSelectDTO dto) {
        List<Role> roles = roleDao.listAll(dto);

        return roles.stream().map(e -> {
            RoleSelectVO vo = new RoleSelectVO();
            BeanUtils.copyProperties(e, vo);
            return vo;
        }).collect(Collectors.toList());
    }
}
