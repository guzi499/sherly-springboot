package com.guzi.upr.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guzi.upr.mapper.admin.UserMapper;
import com.guzi.upr.model.admin.User;
import com.guzi.upr.model.dto.UserPageDTO;
import com.guzi.upr.model.dto.UserSelectDTO;
import com.guzi.upr.util.SherlyLambdaQueryWrapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 谷子毅
 * @date 2022/3/25
 */
@Service
public class UserManager extends ServiceImpl<UserMapper, User> {

    /**
     * 用户分页
     * @param dto
     * @return
     */
    public IPage<User> page(UserPageDTO dto) {
        SherlyLambdaQueryWrapper<User> wrapper = new SherlyLambdaQueryWrapper<>();
        wrapper.likeIfExist(User::getPhone, dto.getPhone())
                .likeIfExist(User::getRealName, dto.getRealName())
                .likeIfExist(User::getNickname, dto.getNickname())
                .likeIfExist(User::getEmail, dto.getEmail())
                .inIfExist(User::getDepartmentId, dto.getDepartmentIds())
                .eqIfExist(User::getEnable, dto.getEnable())
                .betweenIfExist(User::getCreateTime, dto.getBeginTime(), dto.getEndTime())
                .orderByDesc(User::getUserId);
        return this.page(new Page<>(dto.getCurrent(), dto.getSize()), wrapper);
    }

    /**
     * 根据手机号和密码查询用户数据
     * @param phone
     * @param password
     * @return
     */
    public User getOneByPhoneAndPwd(String phone, String password) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getPhone, phone)
                .eq(User::getPassword, password);

        return this.getOne(wrapper, false);
    }

    /**
     * 用户部门更新
     * @param departmentId
     */
    public void updateDepartmentId(Long departmentId) {
        LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(User::getDepartmentId, null)
                .eq(User::getDepartmentId, departmentId);
        this.update(wrapper);
    }

    /**
     * 用户禁用/启用
     * @param userId
     * @param enable
     */
    public void banOne(Long userId, Integer enable) {
        LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(User::getEnable, enable)
                .eq(User::getUserId, userId);
        this.update(wrapper);
    }

    /**
     * 根据手机号查询用户数据
     * @param phone
     * @return
     */
    public User getByPhone(String phone) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getPhone, phone);
        return this.getOne(wrapper, false);
    }

    /**
     * 用户条件查询
     * @param dto
     * @return
     */
    public List<User> listAll(UserSelectDTO dto) {
        SherlyLambdaQueryWrapper<User> wrapper = new SherlyLambdaQueryWrapper<>();
        wrapper.inIfExist(User::getDepartmentId, dto.getDepartmentIds())
                .eqIfExist(User::getEnable, dto.getEnable())
                .inIfExist(User::getUserId, dto.getUserIds());
        return this.list(wrapper);
    }
}
