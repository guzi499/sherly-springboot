package com.guzi.upr.service;

import com.guzi.upr.manager.DepartmentManager;
import com.guzi.upr.manager.RoleManager;
import com.guzi.upr.manager.UserManager;
import com.guzi.upr.manager.UserRoleManager;
import com.guzi.upr.model.admin.Department;
import com.guzi.upr.model.admin.Role;
import com.guzi.upr.model.admin.User;
import com.guzi.upr.model.admin.UserRole;
import com.guzi.upr.model.dto.UserSelfUpdateDTO;
import com.guzi.upr.model.dto.UserUpdatePasswordDTO;
import com.guzi.upr.model.exception.BizException;
import com.guzi.upr.model.vo.UserSelfVO;
import com.guzi.upr.security.util.SecurityUtil;
import com.guzi.upr.util.OssUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.guzi.upr.model.contants.CommonConstants.MALE;
import static com.guzi.upr.model.exception.enums.AdminErrorEnum.USER_PASSWORD_ERROR;
import static com.guzi.upr.model.exception.enums.AdminErrorEnum.USER_PASSWORD_REPEAT;

/**
 * @author 谷子毅
 * @date 2022/7/13
 */
@Service
public class UserSelfService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserManager userManager;

    @Autowired
    private UserRoleManager userRoleManager;

    @Autowired
    private RoleManager roleManager;

    @Autowired
    private DepartmentManager departmentManager;

    @Autowired
    private OssUtil ossUtil;

    /**
     * 用户个人中心
     *
     * @return
     */
    public UserSelfVO getSelf() throws Exception {
        User user = userManager.getById(SecurityUtil.getUserId());

        // 查询角色
        List<UserRole> userRoles = userRoleManager.listByUserId(SecurityUtil.getUserId());
        List<Long> roleIds = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        List<Role> roles = roleManager.listByIds(roleIds);
        List<String> roleNames = roles.stream().map(Role::getRoleName).collect(Collectors.toList());

        // 查询部门
        List<Department> departmentList = departmentManager.list();

        // 组装vo
        UserSelfVO vo = new UserSelfVO();
        BeanUtils.copyProperties(user, vo);
        vo.setAvatar(ossUtil.accessUrl(vo.getAvatar()));
        vo.setRoleIds(roleIds);
        vo.setRoleNames(roleNames);
        vo.setGenderStr(Objects.equals(vo.getGender(), MALE) ? "男" : "女");
        vo.setDepartmentName(departmentList.stream().filter(x -> Objects.equals(x.getDepartmentId(), vo.getDepartmentId())).map(Department::getDepartmentName).collect(Collectors.joining(",")));
        vo.setTenantName(SecurityUtil.getTenantCode());

        return vo;
    }

    /**
     * 用户修改密码
     * @param dto
     */
    public void updatePassword(UserUpdatePasswordDTO dto) {
        Long userId = SecurityUtil.getUserId();
        User user = userManager.getById(userId);

        if (Objects.equals(dto.getNewPassword(), dto.getOldPassword())) {
            // 新旧密码相同
            throw new BizException(USER_PASSWORD_REPEAT);
        }
        boolean match = passwordEncoder.matches(dto.getOldPassword(), user.getPassword());
        if (!match) {
            // 旧密码不正确
            throw new BizException(USER_PASSWORD_ERROR);
        }
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userManager.updateById(user);
    }

    /**
     * 用户个人中心更新
     * @param dto
     */
    public void updateSelf(UserSelfUpdateDTO dto) {
        User user = new User();
        user.setUserId(SecurityUtil.getUserId());
        BeanUtils.copyProperties(dto, user);
        userManager.updateById(user);
    }

    /**
     * 用户修改头像
     * @param avatarPath
     */
    public void updateAvatar(String avatarPath) {
        User user = new User();
        user.setUserId(SecurityUtil.getUserId());
        user.setAvatar(avatarPath);
        userManager.updateById(user);
    }
}
