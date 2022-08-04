package com.guzi.upr.service;

import com.guzi.upr.manager.*;
import com.guzi.upr.model.admin.*;
import com.guzi.upr.model.vo.BasicInfoVO;
import com.guzi.upr.model.vo.BasicMenuInfoVO;
import com.guzi.upr.model.vo.BasicRoleInfoVO;
import com.guzi.upr.model.vo.BasicUserInfoVO;
import com.guzi.upr.security.util.SecurityUtil;
import com.guzi.upr.util.OssUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.guzi.upr.model.contants.CommonConstants.*;

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
    private RoleMenuManager roleMenuManager;

    @Autowired
    private UserRoleManager userRoleManager;

    @Autowired
    private TenantManager tenantManager;

    @Autowired
    private OssUtil ossUtil;

    /**
     * 获取登录基本信息
     * @return
     */
    public BasicInfoVO getBasicData() throws Exception {
        Long userId = SecurityUtil.getUserId();
        Tenant tenant = tenantManager.getByTenantCode(SecurityUtil.getTenantCode());

        // 用户信息收集
        User user = userManager.getById(userId);
        BasicUserInfoVO userVO = new BasicUserInfoVO();
        BeanUtils.copyProperties(user, userVO);
        userVO.setAvatar(ossUtil.accessUrl(userVO.getAvatar()));
        userVO.setTenantCode(tenant.getTenantCode());
        userVO.setTenantName(tenant.getTenantName());

        // 角色信息收集
        List<UserRole> userRoles = userRoleManager.listByUserId(userId);
        List<Long> roleIds = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        List<Role> roles = roleManager.listByIds(roleIds);
        List<BasicRoleInfoVO> roleVOList = roles.stream().map(e -> {
            BasicRoleInfoVO basicRoleInfoVO = new BasicRoleInfoVO();
            BeanUtils.copyProperties(e, basicRoleInfoVO);
            return basicRoleInfoVO;
        }).collect(Collectors.toList());

        // 菜单信息收集
        List<RoleMenu> roleMenus = roleMenuManager.listByRoleIds(roleIds);
        List<Long> menuIds = roleMenus.stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(menuIds)) {
            // 组装结果
            BasicInfoVO basicInfoVO = new BasicInfoVO();
            basicInfoVO.setBasicUserInfoVO(userVO);
            basicInfoVO.setBasicRoleInfoVO(roleVOList);
            basicInfoVO.setBasicMenuInfoVO(Collections.emptyList());
            basicInfoVO.setBasicPermissionVO(Collections.emptyList());
            return basicInfoVO;
        }
        List<Menu> menus = menuManager.listByIds(menuIds);

        // 跳转相关
        List<Menu> jumps = menus.stream().filter(e -> !Objects.equals(e.getMenuType(), BUTTON)).sorted(Comparator.comparing(Menu::getSort)).collect(Collectors.toList());
        // 权限相关
        List<Menu> permissions = menus.stream().filter(e -> !Objects.equals(e.getMenuType(), DIR)).collect(Collectors.toList());

        // 跳转相关数据转换成树
        List<BasicMenuInfoVO> menuVOList = jumps.stream().filter(e -> Objects.equals(e.getParentId(), ROOT_PARENT_ID)).map(e -> {
            BasicMenuInfoVO basicMenuInfoVO = new BasicMenuInfoVO();
            BeanUtils.copyProperties(e, basicMenuInfoVO);
            basicMenuInfoVO.setChildren(getChildren(e, jumps));
            return basicMenuInfoVO;
        }).collect(Collectors.toList());

        List<String> permissionVOList = permissions.stream().map(Menu::getPermission).collect(Collectors.toList());

        // 组装结果
        BasicInfoVO basicInfoVO = new BasicInfoVO();
        basicInfoVO.setBasicUserInfoVO(userVO);
        basicInfoVO.setBasicRoleInfoVO(roleVOList);
        basicInfoVO.setBasicMenuInfoVO(menuVOList);
        basicInfoVO.setBasicPermissionVO(permissionVOList);

        return basicInfoVO;
    }

    /**
     * 递归拼装子结点
     * @param parent
     * @param all
     * @return
     */
    private List<BasicMenuInfoVO> getChildren(Menu parent, List<Menu> all) {
        return all.stream()
                .filter(e -> Objects.equals(e.getParentId(), parent.getMenuId()))
                .map(e -> {
                    BasicMenuInfoVO basicMenuInfoVO = new BasicMenuInfoVO();
                    BeanUtils.copyProperties(e, basicMenuInfoVO);
                    basicMenuInfoVO.setChildren(getChildren(e, all));
                    return basicMenuInfoVO;
                }).collect(Collectors.toList());
    }
}
