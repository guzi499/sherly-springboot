package com.guzi.upr.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.guzi.upr.enums.ResultAdminEnum;
import com.guzi.upr.exception.BizException;
import com.guzi.upr.manager.UserManager;
import com.guzi.upr.model.PageQuery;
import com.guzi.upr.model.PageResult;
import com.guzi.upr.model.admin.User;
import com.guzi.upr.model.dto.UserInsertDTO;
import com.guzi.upr.model.dto.UserUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public PageResult<User> page(PageQuery pageQuery) {
        IPage<User> page = userManager.page(pageQuery.getPage());

        // return new PageResult<User>(page.getRecords(), page.getCurrent(), page.getSize(), page.getTotal());
        return null;
    }
}
