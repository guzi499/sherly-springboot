package com.guzi.upr.model.vo;

import lombok.Data;

import java.util.List;

/**
 * @author 谷子毅
 * @date 2022/4/5
 */
@Data
public class BasicInfoVO {

    private BasicUserInfoVO basicUserInfoVO;

    private List<BasicRoleInfoVO> basicRoleInfoVO;

    private List<BasicMenuInfoVO> basicMenuInfoVO;
}
