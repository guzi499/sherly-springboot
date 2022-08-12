package com.guzi.sherly.model.vo;

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

    /** 权限信息 */
    private List<String> basicPermissionVO;
}
