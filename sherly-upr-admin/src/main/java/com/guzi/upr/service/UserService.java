package com.guzi.upr.service;

import com.guzi.upr.manager.UserManager;
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
}
