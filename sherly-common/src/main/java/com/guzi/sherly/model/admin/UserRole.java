package com.guzi.sherly.model.admin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.guzi.sherly.model.BaseModel;
import lombok.Data;

/**
 * @author 谷子毅
 * @date 2022/3/17
 */

@Data
@TableName("sys_user_role")
public class UserRole extends BaseModel {
    /** 编号 */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 用户编号 */
    private Long userId;

    /** 角色编号 */
    private Long roleId;
}
