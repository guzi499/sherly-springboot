package com.guzi.sherly.model.admin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.guzi.sherly.model.BaseModel;
import lombok.Data;

/**
 * @author 谷子毅
 * @date 2022/6/26
 */
@Data
@TableName("sys_oss_file")
public class OssFile extends BaseModel {
    /** 文件编号 */
    @TableId(type = IdType.AUTO)
    private Long fileId;

    /** 文件名称 */
    private String fileName;

    /** 配置编号 */
    private Long configId;

    /** 文件相对路径 */
    private String path;

    /** 文件类型 */
    private String fileType;

    /** 文件大小 */
    private Integer size;

}
