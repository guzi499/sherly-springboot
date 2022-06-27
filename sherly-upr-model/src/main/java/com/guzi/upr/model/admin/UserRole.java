package com.guzi.upr.model.admin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.guzi.upr.model.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 谷子毅
 * @date 2022/3/17
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("sys_user_role")
public class UserRole extends BaseModel {
    /** id */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 用户id */
    private Long userId;

    /** 角色id */
    private Long roleId;
}
