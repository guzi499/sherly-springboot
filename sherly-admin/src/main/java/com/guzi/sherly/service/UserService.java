package com.guzi.sherly.service;

import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.guzi.sherly.dao.*;
import com.guzi.sherly.model.PageResult;
import com.guzi.sherly.model.admin.*;
import com.guzi.sherly.model.dto.UserInsertDTO;
import com.guzi.sherly.model.dto.UserPageDTO;
import com.guzi.sherly.model.dto.UserSelectDTO;
import com.guzi.sherly.model.dto.UserUpdateDTO;
import com.guzi.sherly.model.eo.UserEO;
import com.guzi.sherly.model.exception.BizException;
import com.guzi.sherly.model.vo.UserPageVo;
import com.guzi.sherly.model.vo.UserSelectVO;
import com.guzi.sherly.model.vo.UserVo;
import com.guzi.sherly.modules.security.util.SecurityUtil;
import com.guzi.sherly.util.GlobalPropertiesUtil;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.guzi.sherly.model.contants.CommonConstants.ENABLE;
import static com.guzi.sherly.model.contants.CommonConstants.MALE;
import static com.guzi.sherly.model.exception.enums.AdminErrorEnum.*;

/**
 * @author 谷子毅
 * @date 2022/3/24
 */
@Service
public class UserService {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private UserDao userDao;

    @Resource
    private UserRoleDao userRoleDao;

    @Resource
    private DepartmentDao departmentDao;

    @Resource
    private AccountUserDao accountUserDao;

    @Resource
    private TenantDao tenantDao;

    /**
     * 用户分页
     * @param dto
     * @return
     */
    public PageResult<UserPageVo> listPage(UserPageDTO dto) {
        // 分页查询
        IPage<User> page = userDao.listPage(dto);
        List<Department> departmentList = departmentDao.list();

        // 对象转换成vo类型
        List<UserPageVo> result = page.getRecords().stream().map(e -> {
            UserPageVo userPageVo = new UserPageVo();
            BeanUtils.copyProperties(e, userPageVo);
            userPageVo.setDepartmentName(departmentList.stream().filter(x -> Objects.equals(x.getDepartmentId(), e.getDepartmentId())).map(Department::getDepartmentName).collect(Collectors.joining(",")));
            return userPageVo;
        }).collect(Collectors.toList());

        return PageResult.build(result, page.getTotal());
    }

    /**
     * 用户导出
     * @param response
     */
    @SneakyThrows
    public void listExport(HttpServletResponse response) {
        List<User> userList = userDao.list();
        List<Department> departmentList = departmentDao.list();
        Map<Long, String> departmentIdMapName = departmentList.stream().collect(Collectors.toMap(Department::getDepartmentId, Department::getDepartmentName));

        List<UserEO> result = userList.stream().map(e -> {
            UserEO userEO = new UserEO();
            BeanUtils.copyProperties(e, userEO);
            userEO.setEnable(Objects.equals(e.getEnable(), ENABLE) ? "启用" : "禁用");
            userEO.setDepartmentName(departmentIdMapName.get(e.getDepartmentId()));
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

        User user = userDao.getById(userId);

        // 查询角色
        List<UserRole> userRoles = userRoleDao.listByUserId(userId);
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
        User one = userDao.getByPhone(phone);
        if (one != null) {
            throw new BizException(USER_REPEAT);
        }

        long userNum = userDao.count();

        // 注册用户上限校验
        Tenant tenant = tenantDao.getByTenantCode(SecurityUtil.getTenantCode());
        if (tenant.getUserLimit() <= userNum) {
            throw new BizException(USER_LIMIT);
        }

        // 同步用户账户信息
        AccountUser accountUser = accountUserDao.getByPhone(phone);
        if (accountUser == null) {
            accountUser = new AccountUser();
            accountUser.setPhone(phone);
            accountUser.setPassword(passwordEncoder.encode(GlobalPropertiesUtil.SHERLY_PROPERTIES.getDefaultPassword()));
            accountUser.setTenantData(SecurityUtil.getTenantCode());
            accountUser.setLastLoginTenantCode(SecurityUtil.getTenantCode());
            accountUserDao.save(accountUser);
        } else {
            List<String> split = StrUtil.split(accountUser.getTenantData(), ",");
            split.add(SecurityUtil.getTenantCode());
            split = split.stream().filter(StrUtil::isNotBlank).collect(Collectors.toList());
            String tenantData = String.join(",", split);
            accountUser.setTenantData(tenantData);
            accountUserDao.updateById(accountUser);
        }

        User user = new User();
        BeanUtils.copyProperties(dto, user);
        user.setEnable(ENABLE);
        userDao.save(user);

        // 保存用户角色数据
        userRoleDao.saveUserRole(user.getUserId(), dto.getRoleIds());
    }

    /**
     * 用户更新
     * @param dto
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateOne(UserUpdateDTO dto) {
        if (Objects.equals(dto.getUserId(), 1L)) {
            throw new BizException(UPDATE_USER_ERROR);
        }
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        userDao.updateById(user);

        userRoleDao.removeUserRoleByUserId(dto.getUserId());
        userRoleDao.saveUserRole(dto.getUserId(), dto.getRoleIds());
    }

    /**
     * 用户删除
     * @param userId
     */
    @Transactional(rollbackFor = Exception.class)
    public void removeOne(Long userId) {
        if (Objects.equals(userId, 1L)) {
            throw new BizException(DELETE_USER_ERROR);
        }

        User user = userDao.getById(userId);
        AccountUser accountUser = accountUserDao.getByPhone(user.getPhone());
        List<String> split = StrUtil.split(accountUser.getTenantData(), ",");
        split.remove(SecurityUtil.getTenantCode());
        String tenantCode = String.join(",", split);
        accountUser.setTenantData(tenantCode);
        accountUserDao.updateById(accountUser);

        userDao.removeById(userId);
    }

    /**
     * 用户禁用/启用
     * @param userId
     */
    public void banOne(Long userId, Integer enable) {
        if (Objects.equals(userId, 1L)) {
            throw new BizException(BAN_USER_ERROR);
        }
        userDao.banOne(userId, enable);
    }

    /**
     * 用户查询
     * @param dto
     * @return
     */
    public List<UserSelectVO> listAll(UserSelectDTO dto) {

        List<UserRole> userRoles = userRoleDao.listByRoleIds(dto.getRoleIds());
        List<Long> userIds = userRoles.stream().map(UserRole::getUserId).collect(Collectors.toList());
        dto.setUserIds(userIds);

        List<User> users = userDao.listAll(dto);

        return users.stream().map(e -> {
            UserSelectVO vo = new UserSelectVO();
            BeanUtils.copyProperties(e, vo);
            return vo;
        }).collect(Collectors.toList());
    }
}
