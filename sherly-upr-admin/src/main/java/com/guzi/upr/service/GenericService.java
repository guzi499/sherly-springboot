package com.guzi.upr.service;

import com.guzi.upr.interceptor.ThreadLocalModel;
import com.guzi.upr.manager.DepartmentManager;
import com.guzi.upr.manager.MenuManager;
import com.guzi.upr.manager.RoleManager;
import com.guzi.upr.manager.UserManager;
import com.guzi.upr.model.admin.Department;
import com.guzi.upr.model.admin.Menu;
import com.guzi.upr.model.admin.Role;
import com.guzi.upr.model.admin.User;
import com.guzi.upr.model.vo.*;
import com.guzi.upr.util.SherlyBeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 谷子毅
 * @date 2022/4/5
 */
@Service
public class GenericService {

    @Autowired
    private UserManager userManager;

    @Autowired
    private RoleManager roleManager;

    @Autowired
    private MenuManager menuManager;

    @Autowired
    private DepartmentManager departmentManager;

    /**
     * 获取登录基本信息
     *
     * @return
     */
    public BasicInfoVO getBasicData() {
        ThreadLocalModel threadLocalModel = (ThreadLocalModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = threadLocalModel.getUserId();

        // 用户信息收集
        User user = userManager.getById(userId);
        BasicUserInfoVO userVO = new BasicUserInfoVO();
        BeanUtils.copyProperties(user, userVO);

        // 角色信息收集
        List<Role> roles = roleManager.listByUserId(userId);
        List<BasicRoleInfoVO> roleVOList = roles.stream().map(e -> {
            BasicRoleInfoVO basicRoleInfoVO = new BasicRoleInfoVO();
            BeanUtils.copyProperties(e, basicRoleInfoVO);
            return basicRoleInfoVO;
        }).collect(Collectors.toList());

        // 菜单信息收集
        List<Long> roleIds = roles.stream().map(Role::getRoleId).collect(Collectors.toList());
        List<Menu> menus = menuManager.listByRoleIds(roleIds);
        List<BasicMenuInfoVO> menuVOList = menus.stream().filter(e -> e.getParentId() == 0).map(e -> {
            BasicMenuInfoVO basicMenuInfoVO = new BasicMenuInfoVO();
            BeanUtils.copyProperties(e, basicMenuInfoVO);
            basicMenuInfoVO.setChildren(getChildren(e, menus));
            return basicMenuInfoVO;
        }).collect(Collectors.toList());

        // 组装结果
        BasicInfoVO basicInfoVO = new BasicInfoVO();
        basicInfoVO.setBasicUserInfoVO(userVO);
        basicInfoVO.setBasicRoleInfoVO(roleVOList);
        basicInfoVO.setBasicMenuInfoVO(menuVOList);

        return basicInfoVO;
    }

    /**
     * 递归拼装子结点
     *
     * @param parent
     * @param all
     * @return
     */
    private List<BasicMenuInfoVO> getChildren(Menu parent, List<Menu> all) {
        return all.stream()
                .filter(e -> e.getParentId().equals(parent.getMenuId()))
                .map(e -> {
                    BasicMenuInfoVO basicMenuInfoVO = new BasicMenuInfoVO();
                    BeanUtils.copyProperties(e, basicMenuInfoVO);
                    basicMenuInfoVO.setChildren(getChildren(e, all));
                    return basicMenuInfoVO;
                }).collect(Collectors.toList());
    }

    /**
     * 菜单下拉框
     *
     * @return
     */
    public List<MenuSelectVO> getBasicMenu() {
        List<Menu> list = menuManager.list();

        // 对象转换成vo类型
        List<MenuSelectVO> all = list.stream().map(e -> {
            MenuSelectVO vo = new MenuSelectVO();
            BeanUtils.copyProperties(e, vo);
            return vo;
        }).collect(Collectors.toList());

        return SherlyBeanUtil.convert(all);
    }

    /**
     * 角色下拉框
     *
     * @return
     */
    public List<RoleSelectVO> getBasicRole() {
        List<Role> list = roleManager.list();

        // 对象转换成vo类型
        return list.stream().map(e -> {
            RoleSelectVO vo = new RoleSelectVO();
            BeanUtils.copyProperties(e, vo);
            return vo;
        }).collect(Collectors.toList());
    }

    /**
     * 部门下拉框
     *
     * @return
     */
    public List<DepartmentSelectVO> getBasicDepartment() {
        List<Department> list = departmentManager.list();

        // 对象转换成vo类型
        List<DepartmentSelectVO> all = list.stream().map(e -> {
            DepartmentSelectVO vo = new DepartmentSelectVO();
            BeanUtils.copyProperties(e, vo);
            return vo;
        }).collect(Collectors.toList());

        return SherlyBeanUtil.convert(all);
    }
}
