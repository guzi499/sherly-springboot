package com.guzi.sherly.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guzi.sherly.log.manager.OperationLogManager;
import com.guzi.sherly.log.model.OperationLog;
import com.guzi.sherly.manager.*;
import com.guzi.sherly.model.PageResult;
import com.guzi.sherly.model.admin.*;
import com.guzi.sherly.model.dto.OperationLogPageDTO;
import com.guzi.sherly.model.dto.OperationLogSelfPageDTO;
import com.guzi.sherly.model.dto.UserSelfUpdateDTO;
import com.guzi.sherly.model.dto.UserUpdatePasswordDTO;
import com.guzi.sherly.model.exception.BizException;
import com.guzi.sherly.model.vo.OperationLogPageVO;
import com.guzi.sherly.model.vo.UserSelfVO;
import com.guzi.sherly.security.util.SecurityUtil;
import com.guzi.sherly.util.OssUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.guzi.sherly.model.contants.CommonConstants.MALE;
import static com.guzi.sherly.model.exception.enums.AdminErrorEnum.USER_PASSWORD_ERROR;
import static com.guzi.sherly.model.exception.enums.AdminErrorEnum.USER_PASSWORD_REPEAT;

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
    private AccountUserManager accountUserManager;

    @Autowired
    private OperationLogManager operationLogManager;

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
        if (Objects.equals(dto.getNewPassword(), dto.getOldPassword())) {
            // 新旧密码相同
            throw new BizException(USER_PASSWORD_REPEAT);
        }

        String phone = SecurityUtil.getPhone();
        AccountUser accountUser = accountUserManager.getByPhone(phone);

        boolean match = passwordEncoder.matches(dto.getOldPassword(), accountUser.getPassword());
        if (!match) {
            // 旧密码不正确
            throw new BizException(USER_PASSWORD_ERROR);
        }
        accountUser.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        accountUserManager.updateById(accountUser);
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

    /**
     * 个人中心操作日志列表
     * @param dto
     * @return
     */
    public PageResult<OperationLogPageVO> operationLogListPage(OperationLogSelfPageDTO dto) {
        OperationLogPageDTO operationLogPageDTO = new OperationLogPageDTO();
        BeanUtils.copyProperties(dto, operationLogPageDTO);
        operationLogPageDTO.setUserIds(Collections.singletonList(SecurityUtil.getUserId()));
        Page<OperationLog> page = operationLogManager.listPage(operationLogPageDTO);
        List<OperationLogPageVO> result = page.getRecords().stream().map(e -> {
            OperationLogPageVO vo = new OperationLogPageVO();
            BeanUtils.copyProperties(e, vo);
            return vo;
        }).collect(Collectors.toList());

        return PageResult.build(result, page.getCurrent(), page.getSize(), page.getTotal());
    }
}
