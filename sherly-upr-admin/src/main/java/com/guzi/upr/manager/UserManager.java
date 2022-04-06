package com.guzi.upr.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guzi.upr.mapper.admin.UserMapper;
import com.guzi.upr.model.admin.User;
import com.guzi.upr.model.admin.UserRole;
import com.guzi.upr.model.dto.UserInsertDTO;
import com.guzi.upr.model.dto.UserUpdateDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 谷子毅
 * @date 2022/3/25
 */
@Service
public class UserManager extends ServiceImpl<UserMapper, User> {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleManager userRoleManager;

    public User getOneByPhoneAndPwd(String phone, String password) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getPhone, phone)
                .eq(User::getPassword, password);

        return userMapper.selectOne(wrapper);
    }


    /**
     * 新增用户及对应角色信息
     *
     * @param dto
     */
    public void saveOne(UserInsertDTO dto) {
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        save(user);
        // 用户角色
        updateUserRole(user, dto.getRoleIds());
    }

    /**
     * 修改用户信息
     *
     * @param dto
     */
    public void updateOne(UserUpdateDTO dto) {
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        updateById(user);
        // 用户角色
        updateUserRole(user, dto.getRoleIds());
    }

    private void updateUserRole(User user, List<Long> roleIds) {
        // 清除角色权限
        userRoleManager.remove(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, user.getUserId()));
        List<UserRole> userRoles = new ArrayList<>();
        for (Long roleId : roleIds) {
            UserRole userRole = new UserRole(null, user.getUserId(), roleId);
            userRoles.add(userRole);
        }
        // 更新用户权限
        userRoleManager.saveBatch(userRoles);
    }

    /**
     * 更新用户部门
     * @param departmentId
     */
    public void updateDepartmentId(Long departmentId) {
        LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(User::getDeptId, null)
                .eq(User::getDeptId, departmentId);
        this.update(wrapper);
    }
}
