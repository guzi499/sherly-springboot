package com.guzi.upr.model.vo;

import lombok.Data;

import java.util.List;

/**
 * @author 谷子毅
 * @date 2022/4/5
 */
@Data
public class BasicInfoVO {
    /** 用户信息 */
    private BasicUserInfoVO basicUserInfoVO;

    /** 角色信息 */
    private List<BasicRoleInfoVO> basicRoleInfoVO;

    /** 菜单信息 */
    private List<BasicMenuInfoVO> basicMenuInfoVO;
}
