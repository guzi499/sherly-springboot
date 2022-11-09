package com.guzi.sherly.model.dto;

import lombok.Data;

import java.util.Map;

/**
 * @author 谷子毅
 * @date 2022/10/21
 */
@Data
public class FlowInstanceStartupDTO {

    private String definitionId;

    private Map<String, Object> variables;

}
