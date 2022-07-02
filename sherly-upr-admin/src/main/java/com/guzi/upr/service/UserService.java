package com.guzi.upr.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.guzi.upr.exception.BizException;
import com.guzi.upr.manager.DepartmentManager;
import com.guzi.upr.manager.RoleManager;
import com.guzi.upr.manager.UserManager;
import com.guzi.upr.manager.UserRoleManager;
import com.guzi.upr.model.PageResult;
import com.guzi.upr.model.admin.Department;
import com.guzi.upr.model.admin.Role;
import com.guzi.upr.model.admin.User;
import com.guzi.upr.model.dto.*;
import com.guzi.upr.model.eo.UserEO;
import com.guzi.upr.model.vo.UserPageVo;
import com.guzi.upr.model.vo.UserSelfVO;
import com.guzi.upr.model.vo.UserVo;
import com.guzi.upr.security.util.SecurityUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.guzi.upr.enums.ResultAdminEnum.*;

/**
 * @author 谷子毅
 * @date 2022/3/24
 */
@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserManager userManager;

    @Autowired
    private RoleManager roleManager;

    @Autowired
    private UserRoleManager userRoleManager;

    @Autowired
    private DepartmentManager departmentManager;

    /**
     * 用户分页
     *
     * @param dto
     * @return
     */
    public PageResult listPage(UserPageDTO dto) {
        // 分页查询
        IPage<User> page = userManager.page(dto);
        List<Department> departmentList = departmentManager.list();

        // 对象转换成vo类型
        List<UserPageVo> result = page.getRecords().stream().map(e -> {
            UserPageVo userPageVo = new UserPageVo();
            BeanUtils.copyProperties(e, userPageVo);
            userPageVo.setDepartmentName(departmentList.stream().filter(x -> Objects.equals(x.getDepartmentId(), e.getDepartmentId())).map(Department::getDepartmentName).collect(Collectors.joining(",")));
            return userPageVo;
        }).collect(Collectors.toList());

        return PageResult.build(result, page.getCurrent(), page.getSize(), page.getTotal());
    }

    /**
     * 用户导出
     *
     * @param response
     */
    public void listExport(HttpServletResponse response) throws IOException {
        List<User> list = userManager.list();
        List<Department> departmentList = departmentManager.list();

        List<UserEO> result = list.stream().map(e -> {
            UserEO userEO = new UserEO();
            BeanUtils.copyProperties(e, userEO);
            userEO.setEnable(e.getEnable() == 1 ? "启用" : "禁用");
            userEO.setDepartmentName(departmentList.stream().filter(x -> Objects.equals(x.getDepartmentId(), e.getDepartmentId())).map(Department::getDepartmentName).collect(Collectors.joining(",")));
            userEO.setGender(e.getGender() == 1 ? "男" : "女");
            return userEO;
        }).collect(Collectors.toList());

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("用户列表", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

        EasyExcel.write(response.getOutputStream(), UserEO.class)
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .sheet("用户列表")
                .doWrite(result);
    }

    /**
     * 用户详情
     *
     * @param userId
     * @return
     */
    public UserVo getOne(Long userId) {

        User user = userManager.getById(userId);

        // 查询角色
        List<Role> roles = roleManager.listByUserId(userId);
        List<Long> roleIds = roles.stream().map(Role::getRoleId).collect(Collectors.toList());

        // 组装vo
        UserVo vo = new UserVo();
        BeanUtils.copyProperties(user, vo);
        vo.setRoleIds(roleIds);

        return vo;
    }

    /**
     * 新增用户
     *
     * @param dto
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveOne(UserInsertDTO dto) {
        // 去重
        User one = userManager.getByPhone(dto.getPhone());
        if (one != null) {
            throw new BizException(USER_REPEAT);
        }

        User user = new User();
        BeanUtils.copyProperties(dto, user);
        user.setEnable(1);
        user.setPassword(passwordEncoder.encode("123456"));
        user.setAvatar("avatar/hello499.jpg");
        userManager.save(user);

        // 保存用户角色数据
        userRoleManager.saveUserRole(user.getUserId(), dto.getRoleIds());
    }

    /**
     * 用户更新
     *
     * @param dto
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateOne(UserUpdateDTO dto) {
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        userManager.updateById(user);

        userRoleManager.removeUserRoleByUserId(dto.getUserId());
        userRoleManager.saveUserRole(dto.getUserId(), dto.getRoleIds());
    }

    /**
     * 用户删除
     *
     * @param userId
     */
    public void removeOne(Long userId) {
        userManager.removeById(userId);
    }

    /**
     * 用户禁用/启用
     *
     * @param userId
     */
    public void banOne(Long userId, Integer enable) {
        userManager.banOne(userId, enable);
    }

    /**
     * 用户个人中心
     *
     * @return
     */
    public UserSelfVO getSelf(Long userId) {
        User user = userManager.getById(userId);

        // 查询角色
        List<Role> roles = roleManager.listByUserId(userId);
        List<Long> roleIds = roles.stream().map(Role::getRoleId).collect(Collectors.toList());
        List<String> roleNames = roles.stream().map(Role::getRoleName).collect(Collectors.toList());

        // 查询部门
        List<Department> departmentList = departmentManager.list();
        List<Long> departmentIds = departmentList.stream().map(Department::getDepartmentId).collect(Collectors.toList());

        // 组装vo
        UserSelfVO vo = new UserSelfVO();
        BeanUtils.copyProperties(user, vo);
        vo.setRoleIds(roleIds);
        vo.setRoleNames(roleNames);
        vo.setGenderStr(vo.getGender() == 1 ? "男" : "女");
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
     * @param dto
     */
    public void updateAvatar(UserUpdateAvatarDTO dto) {
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        userManager.updateById(user);
    }
}
