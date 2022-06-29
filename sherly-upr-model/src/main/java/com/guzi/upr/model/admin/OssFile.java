package com.guzi.upr.model.admin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author 谷子毅
 * @date 2022/6/26
 */
@Data
@TableName("sys_oss_file")
public class OssFile {
    /** 文件id */
    @TableId(type = IdType.AUTO)
    private Long fileId;

    /** 配置id */
    private Long configId;

    /** 文件相对路径 */
    private String path;

    /** 访问url */
    private String url;

    /** 文件类型 */
    private String fileType;

    /** 文件大小 */
    private Integer size;

}
