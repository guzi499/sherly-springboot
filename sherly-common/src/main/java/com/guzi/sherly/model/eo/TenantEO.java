package com.guzi.sherly.model.eo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 李仁杰
 * @date 2022/9/2
 */
@Data
public class TenantEO {

    /** 租户code */
    @ExcelProperty("租户code")
    private String tenantCode;

    /** 租户名称 */
    @ExcelProperty("租户名称")
    private String tenantName;

    /** 联系人 */
    @ExcelProperty("联系人")
    private String contactUser;

    /** 联系电话 */
    @ExcelProperty("联系电话")
    private String contactPhone;

    /** 过期时间 */
    @ExcelProperty("过期时间")
    private Date expireTime;

    /** 用户上限 */
    @ExcelProperty("用户上限")
    private Long userLimit;

    /** 创建时间 */
    @ExcelProperty("创建时间")
    private Date createTime;
}
