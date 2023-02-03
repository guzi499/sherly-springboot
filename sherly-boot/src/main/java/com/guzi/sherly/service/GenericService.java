package com.guzi.sherly.service;

import com.guzi.sherly.admin.generic.vo.BasicInfoVO;
import com.guzi.sherly.admin.generic.vo.BasicMenuInfoVO;
import com.guzi.sherly.admin.generic.vo.BasicRoleInfoVO;
import com.guzi.sherly.admin.generic.vo.BasicUserInfoVO;
import com.guzi.sherly.admin.menu.dao.MenuDao;
import com.guzi.sherly.admin.menu.model.MenuDO;
import com.guzi.sherly.admin.role.dao.RoleDao;
import com.guzi.sherly.admin.role.dao.RoleMenuDao;
import com.guzi.sherly.admin.role.model.RoleDO;
import com.guzi.sherly.admin.role.model.RoleMenuDO;
import com.guzi.sherly.admin.tenant.dao.TenantDao;
import com.guzi.sherly.admin.tenant.model.TenantDO;
import com.guzi.sherly.admin.user.dao.UserDao;
import com.guzi.sherly.admin.user.dao.UserRoleDao;
import com.guzi.sherly.admin.user.model.UserDO;
import com.guzi.sherly.admin.user.model.UserRoleDO;
import com.guzi.sherly.manager.OssManager;
import com.guzi.sherly.modules.security.util.SecurityUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.guzi.sherly.admin.menu.enums.MenuTypeEnum.BUTTON;
import static com.guzi.sherly.admin.menu.enums.MenuTypeEnum.DIR;
import static com.guzi.sherly.common.contants.CommonConstants.ROOT_PARENT_ID;

/**
 * @author 谷子毅
 * @date 2022/4/5
 */
@Service
public class GenericService {

    @Resource
    private UserDao userDao;

    @Resource
    private RoleDao roleDao;

    @Resource
    private MenuDao menuDao;

    @Resource
    private RoleMenuDao roleMenuDao;

    @Resource
    private UserRoleDao userRoleDao;

    @Resource
    private TenantDao tenantDao;

    @Resource
    private OssManager ossManager;

    /**
     * 获取登录基本信息
     * @return
     */
    public BasicInfoVO getBasicData() {
        Long userId = SecurityUtil.getUserId();
        TenantDO tenantDO = tenantDao.getByTenantCode(SecurityUtil.getTenantCode());

        // 用户信息收集
        UserDO userDO = userDao.getById(userId);
        BasicUserInfoVO userVO = new BasicUserInfoVO();
        BeanUtils.copyProperties(userDO, userVO);
        userVO.setAvatar(ossManager.accessUrl(userVO.getAvatar()));
        userVO.setTenantCode(tenantDO.getTenantCode());
        userVO.setTenantName(tenantDO.getTenantName());

        // 角色信息收集
        List<UserRoleDO> userRoleDOs = userRoleDao.listByUserId(userId);
        List<Long> roleIds = userRoleDOs.stream().map(UserRoleDO::getRoleId).collect(Collectors.toList());
        List<RoleDO> roleDOs = roleDao.listByIds(roleIds);
        List<BasicRoleInfoVO> roleVOList = roleDOs.stream().map(e -> {
            BasicRoleInfoVO basicRoleInfoVO = new BasicRoleInfoVO();
            BeanUtils.copyProperties(e, basicRoleInfoVO);
            return basicRoleInfoVO;
        }).collect(Collectors.toList());

        // 菜单信息收集
        List<RoleMenuDO> roleMenuDOs = roleMenuDao.listByRoleIds(roleIds);
        List<Long> menuIds = roleMenuDOs.stream().map(RoleMenuDO::getMenuId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(menuIds)) {
            // 组装结果
            BasicInfoVO basicInfoVO = new BasicInfoVO();
            basicInfoVO.setBasicUserInfoVO(userVO);
            basicInfoVO.setBasicRoleInfoVO(roleVOList);
            basicInfoVO.setBasicMenuInfoVO(Collections.emptyList());
            basicInfoVO.setBasicPermissionVO(Collections.emptyList());
            return basicInfoVO;
        }
        List<MenuDO> menuDOs = menuDao.listByIds(menuIds);

        // 跳转相关
        List<MenuDO> jumps = menuDOs.stream().filter(e -> !Objects.equals(e.getMenuType(), BUTTON)).sorted(Comparator.comparing(MenuDO::getSort)).collect(Collectors.toList());
        // 权限相关
        List<MenuDO> permissions = menuDOs.stream().filter(e -> !Objects.equals(e.getMenuType(), DIR)).collect(Collectors.toList());

        // 跳转相关数据转换成树
        List<BasicMenuInfoVO> menuVOList = jumps.stream().filter(e -> Objects.equals(e.getParentId(), ROOT_PARENT_ID)).map(e -> {
            BasicMenuInfoVO basicMenuInfoVO = new BasicMenuInfoVO();
            BeanUtils.copyProperties(e, basicMenuInfoVO);
            basicMenuInfoVO.setChildren(getChildren(e, jumps));
            return basicMenuInfoVO;
        }).collect(Collectors.toList());

        List<String> permissionVOList = permissions.stream().map(MenuDO::getPermission).collect(Collectors.toList());

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
    private List<BasicMenuInfoVO> getChildren(MenuDO parent, List<MenuDO> all) {
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
