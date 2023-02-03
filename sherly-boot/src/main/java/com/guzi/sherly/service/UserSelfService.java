package com.guzi.sherly.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guzi.sherly.admin.accountuser.dao.AccountUserDao;
import com.guzi.sherly.admin.accountuser.model.AccountUserDO;
import com.guzi.sherly.admin.department.dao.DepartmentDao;
import com.guzi.sherly.admin.department.model.DepartmentDO;
import com.guzi.sherly.admin.role.dao.RoleDao;
import com.guzi.sherly.admin.role.model.RoleDO;
import com.guzi.sherly.admin.user.dao.UserDao;
import com.guzi.sherly.admin.user.dao.UserRoleDao;
import com.guzi.sherly.admin.user.dto.UserUpdatePasswordDTO;
import com.guzi.sherly.admin.user.model.UserDO;
import com.guzi.sherly.admin.user.model.UserRoleDO;
import com.guzi.sherly.admin.useronline.dto.UserSelfUpdateDTO;
import com.guzi.sherly.admin.useronline.vo.UserSelfVO;
import com.guzi.sherly.common.exception.BizException;
import com.guzi.sherly.common.model.PageResult;
import com.guzi.sherly.manager.OssManager;
import com.guzi.sherly.modules.log.dao.OperationLogDao;
import com.guzi.sherly.modules.log.dto.OperationLogPageDTO;
import com.guzi.sherly.modules.log.dto.OperationLogSelfPageDTO;
import com.guzi.sherly.modules.log.model.OperationLogDO;
import com.guzi.sherly.modules.log.vo.OperationLogPageVO;
import com.guzi.sherly.modules.security.util.SecurityUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.guzi.sherly.admin.user.enums.GenderEnum.MALE;
import static com.guzi.sherly.common.exception.enums.AdminErrorEnum.USER_PASSWORD_ERROR;
import static com.guzi.sherly.common.exception.enums.AdminErrorEnum.USER_PASSWORD_REPEAT;

/**
 * @author 谷子毅
 * @date 2022/7/13
 */
@Service
public class UserSelfService {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private UserDao userDao;

    @Resource
    private UserRoleDao userRoleDao;

    @Resource
    private RoleDao roleDao;

    @Resource
    private DepartmentDao departmentDao;

    @Resource
    private AccountUserDao accountUserDao;

    @Resource
    private OperationLogDao operationLogDao;

    @Resource
    private OssManager ossManager;

    /**
     * 用户个人中心
     *
     * @return
     */
    public UserSelfVO getSelf() {
        UserDO userDO = userDao.getById(SecurityUtil.getUserId());

        // 查询角色
        List<UserRoleDO> userRoleDOs = userRoleDao.listByUserId(SecurityUtil.getUserId());
        List<Long> roleIds = userRoleDOs.stream().map(UserRoleDO::getRoleId).collect(Collectors.toList());
        List<RoleDO> roleDOs = roleDao.listByIds(roleIds);
        List<String> roleNames = roleDOs.stream().map(RoleDO::getRoleName).collect(Collectors.toList());

        // 查询部门
        List<DepartmentDO> departmentDOList = departmentDao.list();
        Map<Long, String> departmentIdMapName = departmentDOList.stream().collect(Collectors.toMap(DepartmentDO::getDepartmentId, DepartmentDO::getDepartmentName));

        // 组装vo
        UserSelfVO vo = new UserSelfVO();
        BeanUtils.copyProperties(userDO, vo);
        vo.setAvatar(ossManager.accessUrl(vo.getAvatar()));
        vo.setRoleIds(roleIds);
        vo.setRoleNames(roleNames);
        vo.setGenderStr(Objects.equals(vo.getGender(), MALE.getGender()) ? "男" : "女");
        vo.setDepartmentName(departmentIdMapName.get(vo.getDepartmentId()));
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
        AccountUserDO accountUserDO = accountUserDao.getByPhone(phone);

        // 旧密码正确性验证
        boolean match = passwordEncoder.matches(dto.getOldPassword(), accountUserDO.getPassword());
        if (!match) {
            // 旧密码不正确
            throw new BizException(USER_PASSWORD_ERROR);
        }

        // 密码加密后存储到db
        accountUserDO.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        accountUserDao.updateById(accountUserDO);
    }

    /**
     * 用户个人中心更新
     * @param dto
     */
    public void updateSelf(UserSelfUpdateDTO dto) {
        UserDO userDO = new UserDO();
        userDO.setUserId(SecurityUtil.getUserId());
        BeanUtils.copyProperties(dto, userDO);
        userDao.updateById(userDO);
    }

    /**
     * 用户修改头像
     * @param avatarPath
     */
    public void updateAvatar(String avatarPath) {
        UserDO userDO = new UserDO();
        userDO.setUserId(SecurityUtil.getUserId());
        userDO.setAvatar(avatarPath);
        userDao.updateById(userDO);
    }

    /**
     * 个人中心操作日志列表
     * @param dto
     * @return
     */
    public PageResult<OperationLogPageVO> operationLogListPage(OperationLogSelfPageDTO dto) {
        // 设置日志操作人为当前登录用户
        OperationLogPageDTO operationLogPageDTO = new OperationLogPageDTO();
        BeanUtils.copyProperties(dto, operationLogPageDTO);
        operationLogPageDTO.setUserIds(Collections.singletonList(SecurityUtil.getUserId()));

        // 分页查询操作日志
        Page<OperationLogDO> page = operationLogDao.listPage(operationLogPageDTO);

        List<OperationLogPageVO> result = page.getRecords().stream().map(e -> {
            OperationLogPageVO vo = new OperationLogPageVO();
            BeanUtils.copyProperties(e, vo);
            return vo;
        }).collect(Collectors.toList());

        return PageResult.build(result, page.getTotal());
    }
}
