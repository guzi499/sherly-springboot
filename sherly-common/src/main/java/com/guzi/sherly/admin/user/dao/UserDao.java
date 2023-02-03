package com.guzi.sherly.admin.user.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guzi.sherly.admin.user.dto.UserPageDTO;
import com.guzi.sherly.admin.user.dto.UserSelectDTO;
import com.guzi.sherly.admin.user.mapper.UserMapper;
import com.guzi.sherly.admin.user.model.UserDO;
import com.guzi.sherly.modules.mybatisplus.service.SherlyServiceImpl;
import com.guzi.sherly.modules.mybatisplus.wrapper.SherlyLambdaQueryWrapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 谷子毅
 * @date 2022/3/25
 */
@Service
public class UserDao extends SherlyServiceImpl<UserMapper, UserDO> {

    /**
     * 用户分页
     * @param dto
     * @return
     */
    public IPage<UserDO> listPage(UserPageDTO dto) {
        SherlyLambdaQueryWrapper<UserDO> wrapper = new SherlyLambdaQueryWrapper<>();
        wrapper.likeIfExist(UserDO::getPhone, dto.getPhone())
                .likeIfExist(UserDO::getRealName, dto.getRealName())
                .likeIfExist(UserDO::getNickname, dto.getNickname())
                .likeIfExist(UserDO::getEmail, dto.getEmail())
                .inIfExist(UserDO::getDepartmentId, dto.getDepartmentIds())
                .eqIfExist(UserDO::getEnable, dto.getEnable())
                .betweenIfExist(UserDO::getCreateTime, dto.getBeginTime(), dto.getEndTime())
                .orderByDesc(UserDO::getUserId);
        return this.page(new Page<>(dto.getCurrent(), dto.getSize()), wrapper);
    }

    /**
     * 用户部门更新
     * @param departmentId
     */
    public void updateDepartmentId(Long departmentId) {
        LambdaUpdateWrapper<UserDO> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(UserDO::getDepartmentId, 1L)
                .eq(UserDO::getDepartmentId, departmentId);
        this.update(wrapper);
    }

    /**
     * 用户禁用/启用
     * @param userId
     * @param enable
     */
    public void banOne(Long userId, Integer enable) {
        LambdaUpdateWrapper<UserDO> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(UserDO::getEnable, enable)
                .eq(UserDO::getUserId, userId);
        this.update(wrapper);
    }

    /**
     * 根据手机号查询用户数据
     * @param phone
     * @return
     */
    public UserDO getByPhone(String phone) {
        LambdaQueryWrapper<UserDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserDO::getPhone, phone);
        return this.getOne(wrapper, false);
    }

    /**
     * 用户条件查询
     * @param dto
     * @return
     */
    public List<UserDO> listAll(UserSelectDTO dto) {
        SherlyLambdaQueryWrapper<UserDO> wrapper = new SherlyLambdaQueryWrapper<>();
        wrapper
                .likeIfExist(UserDO::getNickname, dto.getNickname())
                .likeIfExist(UserDO::getPhone, dto.getPhone())
                .likeIfExist(UserDO::getEmail, dto.getEmail())
                .inIfExist(UserDO::getDepartmentId, dto.getDepartmentIds())
                .likeIfExist(UserDO::getRealName, dto.getRealName())
                .eqIfExist(UserDO::getEnable, dto.getEnable())
                .inIfExist(UserDO::getUserId, dto.getUserIds());
        return this.list(wrapper);
    }
}
