package com.guzi.sherly.model.vo;

import lombok.Data;

/**
 * @author 谷子毅
 * @date 2022/10/21
 */
@Data
public class FlowDefinitionPageVO {

    private String id;

    private String category;

    private String name;

    private String key;

    private String description;

    private Integer version;

    private String resourceName;

    private String getDeploymentId;

    private String getDiagramResourceName;

    private Boolean hasStartFormKey;

    private Boolean hasGraphicalNotation;

    private Boolean isSuspended;

    private String getTenantId;

    private String getDerivedFrom;

    private String getDerivedFromRoot;

    private Integer getDerivedVersion;

    private String getEngineVersion;
}
