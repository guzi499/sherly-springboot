package com.guzi.sherly.admin.user.eo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 谷子毅
 * @date 2022/5/11
 */
@Data
public class UserEO {

    /** 用户编号 */
    @ExcelProperty("用户编号")
    private Long userId;

    /** 昵称 */
    @ExcelProperty("昵称")
    private String nickname;

    /** 姓名 */
    @ExcelProperty("姓名")
    private String realName;

    /** 手机号 */
    @ExcelProperty("手机号")
    private String phone;

    /** 用户邮箱 */
    @ExcelProperty("用户邮箱")
    private String email;

    /** 性别 */
    @ExcelProperty("性别")
    private String gender;

    /** 部门名称 */
    @ExcelProperty("部门名称")
    private String departmentName;

    /** 启用禁用 */
    @ExcelProperty("启用禁用")
    private String enable;

    /** 最后登录时间 */
    @ExcelProperty("最后登录时间")
    private Date lastLoginTime;

    /** 最后登录ip */
    @ExcelProperty("最后登录ip")
    private String lastLoginIp;
}
