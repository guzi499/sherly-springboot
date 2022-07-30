package com.guzi.upr.service;

import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.guzi.upr.manager.*;
import com.guzi.upr.model.PageResult;
import com.guzi.upr.model.admin.*;
import com.guzi.upr.model.dto.UserInsertDTO;
import com.guzi.upr.model.dto.UserPageDTO;
import com.guzi.upr.model.dto.UserSelectDTO;
import com.guzi.upr.model.dto.UserUpdateDTO;
import com.guzi.upr.model.eo.UserEO;
import com.guzi.upr.model.exception.BizException;
import com.guzi.upr.model.vo.UserPageVo;
import com.guzi.upr.model.vo.UserSelectVO;
import com.guzi.upr.model.vo.UserVo;
import com.guzi.upr.security.util.SecurityUtil;
import com.guzi.upr.util.GlobalPropertiesUtil;
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

import static com.guzi.upr.model.contants.CommonConstants.ENABLE;
import static com.guzi.upr.model.contants.CommonConstants.MALE;
import static com.guzi.upr.model.exception.enums.AdminErrorEnum.USER_LIMIT;
import static com.guzi.upr.model.exception.enums.AdminErrorEnum.USER_REPEAT;

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
    private UserRoleManager userRoleManager;

    @Autowired
    private DepartmentManager departmentManager;

    @Autowired
    private AccountUserManager accountUserManager;

    @Autowired
    private TenantManager tenantManager;

    /**
     * 用户分页
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
     * @param response
     */
    public void listExport(HttpServletResponse response) throws IOException {
        List<User> list = userManager.list();
        List<Department> departmentList = departmentManager.list();

        List<UserEO> result = list.stream().map(e -> {
            UserEO userEO = new UserEO();
            BeanUtils.copyProperties(e, userEO);
            userEO.setEnable(Objects.equals(e.getEnable(), ENABLE) ? "启用" : "禁用");
            userEO.setDepartmentName(departmentList.stream().filter(x -> Objects.equals(x.getDepartmentId(), e.getDepartmentId())).map(Department::getDepartmentName).collect(Collectors.joining(",")));
            userEO.setGender(Objects.equals(e.getGender(), MALE) ? "男" : "女");
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
     * @param userId
     * @return
     */
    public UserVo getOne(Long userId) {

        User user = userManager.getById(userId);

        // 查询角色
        List<UserRole> userRoles = userRoleManager.listByUserId(userId);
        List<Long> roleIds = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());

        // 组装vo
        UserVo vo = new UserVo();
        BeanUtils.copyProperties(user, vo);
        vo.setRoleIds(roleIds);

        return vo;
    }

    /**
     * 新增用户
     * @param dto
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveOne(UserInsertDTO dto) {
        // 去重
        String phone = dto.getPhone();
        User one = userManager.getByPhone(phone);
        if (one != null) {
            throw new BizException(USER_REPEAT);
        }

        long userNum = userManager.count();
        SecurityUtil.setOperateTenantCode(GlobalPropertiesUtil.SHERLY_PROPERTIES.getDefaultDb());

        // 注册用户上限校验
        Tenant tenant = tenantManager.getByTenantCode(SecurityUtil.getTenantCode());
        if (tenant.getUserLimit() <= userNum) {
            throw new BizException(USER_LIMIT);
        }

        // 同步用户账户信息
        AccountUser accountUser = accountUserManager.getByPhone(phone);
        if (accountUser == null) {
            accountUser = new AccountUser();
            accountUser.setPhone(phone);
            accountUser.setTenantData(SecurityUtil.getTenantCode());
            accountUser.setLastLoginTenantCode(SecurityUtil.getTenantCode());
            accountUserManager.save(accountUser);
        } else {
            List<String> split = StrUtil.split(accountUser.getTenantData(), ",");
            split.add(SecurityUtil.getTenantCode());
            String tenantData = String.join(",", split);
            accountUser.setTenantData(tenantData);
            accountUserManager.updateById(accountUser);
        }
        SecurityUtil.clearOperateTenantCode();

        User user = new User();
        BeanUtils.copyProperties(dto, user);
        user.setEnable(ENABLE);
        user.setPassword(passwordEncoder.encode(GlobalPropertiesUtil.SHERLY_PROPERTIES.getDefaultPassword()));
        userManager.save(user);

        // 保存用户角色数据
        userRoleManager.saveUserRole(user.getUserId(), dto.getRoleIds());
    }

    /**
     * 用户更新
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
     * @param userId
     */
    @Transactional(rollbackFor = Exception.class)
    public void removeOne(Long userId) {
        User user = userManager.getById(userId);
        AccountUser accountUser = accountUserManager.getByPhone(user.getPhone());
        List<String> split = StrUtil.split(accountUser.getTenantData(), ",");
        split.remove(SecurityUtil.getTenantCode());
        String tenantCode = String.join(",", split);
        accountUser.setTenantData(tenantCode);
        accountUserManager.updateById(accountUser);

        userManager.removeById(userId);
    }

    /**
     * 用户禁用/启用
     * @param userId
     */
    public void banOne(Long userId, Integer enable) {
        userManager.banOne(userId, enable);
    }

    /**
     * 用户查询
     * @param dto
     * @return
     */
    public List<UserSelectVO> listAll(UserSelectDTO dto) {

        List<UserRole> userRoles = userRoleManager.listByRoleIds(dto.getRoleIds());
        List<Long> userIds = userRoles.stream().map(UserRole::getUserId).collect(Collectors.toList());
        dto.setUserIds(userIds);

        List<User> users = userManager.listAll(dto);

        return users.stream().map(e -> {
            UserSelectVO vo = new UserSelectVO();
            BeanUtils.copyProperties(e, vo);
            return vo;
        }).collect(Collectors.toList());
    }
}
