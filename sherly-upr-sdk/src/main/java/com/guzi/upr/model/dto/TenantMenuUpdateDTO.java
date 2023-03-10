package com.guzi.upr.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author 谷子毅
 * @date 2022/7/20
 */
@Data
public class TenantMenuUpdateDTO {

    /** 租户id */
    @NotNull
    private Integer tenantId;

    /** 菜单ids */
    private List<Long> menuIds;
}
