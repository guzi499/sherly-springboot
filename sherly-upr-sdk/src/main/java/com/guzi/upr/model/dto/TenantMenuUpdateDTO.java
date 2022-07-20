package com.guzi.upr.model.dto;

import lombok.Data;

import java.util.List;

/**
 * @author 谷子毅
 * @date 2022/7/20
 */
@Data
public class TenantMenuUpdateDTO {

    /** 租户id */
    private Integer tenantId;

    /** 菜单ids */
    private List<Long> menuIds;
}
