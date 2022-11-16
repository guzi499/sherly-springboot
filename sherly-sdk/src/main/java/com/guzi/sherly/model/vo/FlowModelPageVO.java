package com.guzi.sherly.model.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author 谷子毅
 * @date 2022/8/31
 */
@Data
public class FlowModelPageVO {
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

    /** 创建时间 */
    private Date createTime;

    /** 最后更新时间 */
    protected Date lastUpdateTime;
}
