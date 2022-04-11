package com.guzi.upr.mapper.admin;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guzi.upr.model.admin.UserRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 谷子毅
 * @date 2022/3/22
 */
@Repository
public interface UserRoleMapper extends BaseMapper<UserRole> {

    /**
     * 保存用户角色数据
     *
     * @param userId
     * @param roleIds
     */
    void saveUserRole(@Param("userId") Long userId, @Param("roleIds") List<Long> roleIds);
}
