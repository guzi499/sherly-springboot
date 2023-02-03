package com.guzi.sherly.service;

import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.guzi.sherly.admin.accountuser.dao.AccountUserDao;
import com.guzi.sherly.admin.accountuser.model.AccountUserDO;
import com.guzi.sherly.admin.department.dao.DepartmentDao;
import com.guzi.sherly.admin.department.model.DepartmentDO;
import com.guzi.sherly.admin.tenant.dao.TenantDao;
import com.guzi.sherly.admin.tenant.model.TenantDO;
import com.guzi.sherly.admin.user.dao.UserDao;
import com.guzi.sherly.admin.user.dao.UserRoleDao;
import com.guzi.sherly.admin.user.dto.UserInsertDTO;
import com.guzi.sherly.admin.user.dto.UserPageDTO;
import com.guzi.sherly.admin.user.dto.UserSelectDTO;
import com.guzi.sherly.admin.user.dto.UserUpdateDTO;
import com.guzi.sherly.admin.user.eo.UserEO;
import com.guzi.sherly.admin.user.model.UserDO;
import com.guzi.sherly.admin.user.model.UserRoleDO;
import com.guzi.sherly.admin.user.vo.UserPageVo;
import com.guzi.sherly.admin.user.vo.UserSelectVO;
import com.guzi.sherly.admin.user.vo.UserVo;
import com.guzi.sherly.common.exception.BizException;
import com.guzi.sherly.common.model.PageResult;
import com.guzi.sherly.common.util.GlobalPropertiesUtil;
import com.guzi.sherly.modules.security.util.SecurityUtil;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.guzi.sherly.admin.user.enums.GenderEnum.MALE;
import static com.guzi.sherly.common.contants.CommonConstants.ENABLE;
import static com.guzi.sherly.common.exception.enums.AdminErrorEnum.*;

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
        IPage<UserDO> page = userDao.listPage(dto);
        List<DepartmentDO> departmentDOList = departmentDao.list();

        // 对象转换成vo类型
        List<UserPageVo> result = page.getRecords().stream().map(e -> {
            UserPageVo userPageVo = new UserPageVo();
            BeanUtils.copyProperties(e, userPageVo);
            userPageVo.setDepartmentName(departmentDOList.stream().filter(x -> Objects.equals(x.getDepartmentId(), e.getDepartmentId())).map(DepartmentDO::getDepartmentName).collect(Collectors.joining(",")));
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
        List<UserDO> userDOList = userDao.list();
        List<DepartmentDO> departmentDOList = departmentDao.list();
        Map<Long, String> departmentIdMapName = departmentDOList.stream().collect(Collectors.toMap(DepartmentDO::getDepartmentId, DepartmentDO::getDepartmentName));

        List<UserEO> result = userDOList.stream().map(e -> {
            UserEO userEO = new UserEO();
            BeanUtils.copyProperties(e, userEO);
            userEO.setEnable(Objects.equals(e.getEnable(), ENABLE) ? "启用" : "禁用");
            userEO.setDepartmentName(departmentIdMapName.get(e.getDepartmentId()));
            userEO.setGender(Objects.equals(e.getGender(), MALE.getGender()) ? "男" : "女");
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

        UserDO userDO = userDao.getById(userId);

        // 查询角色
        List<UserRoleDO> userRoleDOs = userRoleDao.listByUserId(userId);
        List<Long> roleIds = userRoleDOs.stream().map(UserRoleDO::getRoleId).collect(Collectors.toList());

        // 组装vo
        UserVo vo = new UserVo();
        BeanUtils.copyProperties(userDO, vo);
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
        UserDO one = userDao.getByPhone(phone);
        if (one != null) {
            throw new BizException(USER_REPEAT);
        }

        long userNum = userDao.count();

        // 注册用户上限校验
        TenantDO tenantDO = tenantDao.getByTenantCode(SecurityUtil.getTenantCode());
        if (tenantDO.getUserLimit() <= userNum) {
            throw new BizException(USER_LIMIT);
        }

        // 同步用户账户信息
        AccountUserDO accountUserDO = accountUserDao.getByPhone(phone);
        if (accountUserDO == null) {
            accountUserDO = new AccountUserDO();
            accountUserDO.setPhone(phone);
            accountUserDO.setPassword(passwordEncoder.encode(GlobalPropertiesUtil.SHERLY_PROPERTIES.getDefaultPassword()));
            accountUserDO.setTenantData(SecurityUtil.getTenantCode());
            accountUserDO.setLastLoginTenantCode(SecurityUtil.getTenantCode());
            accountUserDao.save(accountUserDO);
        } else {
            List<String> split = StrUtil.split(accountUserDO.getTenantData(), ",");
            split.add(SecurityUtil.getTenantCode());
            split = split.stream().filter(StrUtil::isNotBlank).collect(Collectors.toList());
            String tenantData = String.join(",", split);
            accountUserDO.setTenantData(tenantData);
            accountUserDao.updateById(accountUserDO);
        }

        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(dto, userDO);
        userDO.setEnable(ENABLE);
        userDO.setAccountUserId(accountUserDO.getAccountUserId());
        userDO.setLastLoginTime(new Date());
        userDao.save(userDO);

        // 保存用户角色数据
        userRoleDao.saveUserRole(userDO.getUserId(), dto.getRoleIds());
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
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(dto, userDO);
        userDao.updateById(userDO);

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

        UserDO userDO = userDao.getById(userId);
        AccountUserDO accountUserDO = accountUserDao.getByPhone(userDO.getPhone());
        List<String> split = StrUtil.split(accountUserDO.getTenantData(), ",");
        split.remove(SecurityUtil.getTenantCode());
        String tenantCode = String.join(",", split);
        accountUserDO.setTenantData(tenantCode);
        accountUserDao.updateById(accountUserDO);

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

        List<UserRoleDO> userRoleDOs = userRoleDao.listByRoleIds(dto.getRoleIds());
        List<Long> userIds = userRoleDOs.stream().map(UserRoleDO::getUserId).collect(Collectors.toList());
        dto.setUserIds(userIds);

        List<UserDO> userDOs = userDao.listAll(dto);

        return userDOs.stream().map(e -> {
            UserSelectVO vo = new UserSelectVO();
            BeanUtils.copyProperties(e, vo);
            return vo;
        }).collect(Collectors.toList());
    }
}
