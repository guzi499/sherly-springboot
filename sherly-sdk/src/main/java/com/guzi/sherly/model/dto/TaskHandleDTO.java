package com.guzi.sherly.model.dto;

import lombok.Data;

import java.util.Map;

/**
 * @author 谷子毅
 * @date 2022/10/21
 */
@Data
public class TaskHandleDTO {

    private String taskId;

    private String instanceId;

    private String message;

    private Map<String, Object> variables;
}
