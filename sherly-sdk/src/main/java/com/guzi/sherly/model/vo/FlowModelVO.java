package com.guzi.sherly.model.vo;

import lombok.Data;

/**
 * @author 谷子毅
 * @date 2022/9/1
 */
@Data
public class FlowModelVO {
    /** 模型编号 */
    private String id;

    /** 模型名称 */
    private String name;

    /** 模型key */
    private String key;

    /** 分类 */
    private String category;

    /** 模型元数据 */
    private String metaInfo;

    /** 模型xml */
    private String modelXml;
}
