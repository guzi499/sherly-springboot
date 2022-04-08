package com.guzi.upr.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.guzi.upr.enums.ResultAdminEnum;
import com.guzi.upr.exception.BizException;
import com.guzi.upr.manager.UserManager;
import com.guzi.upr.model.PageResult;
import com.guzi.upr.model.admin.User;
import com.guzi.upr.model.dto.UserInsertDTO;
import com.guzi.upr.model.dto.UserPageDTO;
import com.guzi.upr.model.dto.UserUpdateDTO;
import com.guzi.upr.model.vo.UserInfoVo;
import com.guzi.upr.model.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 谷子毅
 * @email guzyc@digitalchina.com
 * @date 2022/3/24
 */
@Service
public class UserService {
    @Autowired
    private UserManager userManager;

    /**
     * 新增用户
     *
     * @param dto
     */
    public void saveOne(UserInsertDTO dto) {
        // 重复检查

        if (userManager.count(new LambdaQueryWrapper<User>().eq(User::getPhone, dto.getPhone())) > 0) {
            throw new BizException(ResultAdminEnum.USER_REPEAT);
        }
        userManager.saveOne(dto);
    }

    /**
     * 修改用户信息
     *
     * @param dto
     */
    public void updateOne(UserUpdateDTO dto) {

        userManager.updateOne(dto);
    }

    /**
     * 移除用户
     *
     * @param userId
     */
    public void removeOne(Long userId) {
        userManager.removeById(userId);
    }

    /**
     * @param dto
     * @return com.guzi.upr.model.PageResult<com.guzi.upr.model.vo.UserVo>
     * @author 付东辉
     * 分页查询用户信息
     * @date 2022/4/9 1:09
     */
    public PageResult<UserVo> page(UserPageDTO dto) {
        IPage<User> page = userManager.page(dto.getPage());
        List<UserVo> record = page.getRecords().stream().map(e -> {
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(e, userVo);
            return userVo;
        }).collect(Collectors.toList());

        return PageResult.build(record, page.getCurrent(), page.getSize(), page.getTotal());
    }

    /**
     * @param id
     * @return com.guzi.upr.model.vo.UserInfoVo
     * @author 付东辉
     * 获取用户信息
     * @date 2022/4/9 1:10
     */
    public UserInfoVo getById(Integer id) {

        return userManager.getUserInfo(id);
    }

    /**
     * @param userId
     * @return void
     * @author 付东辉
     * 禁用用户
     * @date 2022/4/9 1:10
     */
    public void banUserById(Integer userId) {
        userManager.banUserById(userId);

    }
}
