package com.guzi.upr.service;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.guzi.upr.enums.ResultAdminEnum;
import com.guzi.upr.exception.BizException;
import com.guzi.upr.manager.RoleManager;
import com.guzi.upr.manager.UserManager;
import com.guzi.upr.manager.UserRoleManager;
import com.guzi.upr.model.PageResult;
import com.guzi.upr.model.admin.Role;
import com.guzi.upr.model.admin.User;
import com.guzi.upr.model.dto.UserInsertDTO;
import com.guzi.upr.model.dto.UserPageDTO;
import com.guzi.upr.model.dto.UserUpdateDTO;
import com.guzi.upr.model.vo.UserPageVo;
import com.guzi.upr.model.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 谷子毅
 * @date 2022/3/24
 */
@Service
public class UserService {

    @Autowired
    private UserManager userManager;

    @Autowired
    private RoleManager roleManager;

    @Autowired
    private UserRoleManager userRoleManager;

    /**
     * 用户分页
     *
     * @param dto
     * @return
     */
    public PageResult listPage(UserPageDTO dto) {
        // 分页查询
        IPage<User> page = userManager.page(dto.getPage());

        // 对象转换成vo类型
        List<UserPageVo> result = page.getRecords().stream().map(e -> {
            UserPageVo userPageVo = new UserPageVo();
            BeanUtils.copyProperties(e, userPageVo);
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
        List<User> list = new ArrayList<>();
        User user = new User();
        user.setPhone("13227500030");
        list.add(user);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("用户列表", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

        EasyExcel.write(response.getOutputStream()).sheet("用户列表").doWrite(list);
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
    public void saveOne(UserInsertDTO dto) {
        // 去重
        User one = userManager.getByPhone(dto.getPhone());
        if (one != null) {
            throw new BizException(ResultAdminEnum.USER_REPEAT);
        }

        User user = new User();
        BeanUtils.copyProperties(dto, user);
        user.setEnable(0);
        user.setPassword("123456");
        userManager.save(user);

        // 保存用户角色数据
        userRoleManager.saveUserRole(user.getUserId(), dto.getRoleIds());
    }

    /**
     * 用户更新
     *
     * @param dto
     */
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
}
