package com.guzi.sherly.modules.oss.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.guzi.sherly.modules.mybatisplus.service.SherlyServiceImpl;
import com.guzi.sherly.modules.oss.mapper.OssConfigMapper;
import com.guzi.sherly.modules.oss.model.OssConfig;
import org.springframework.stereotype.Service;

import static com.guzi.sherly.common.contants.CommonConstants.ENABLE;

/**
 * @author 谷子毅
 * @date 2022/6/24
 */
@Service
public class OssConfigDao extends SherlyServiceImpl<OssConfigMapper, OssConfig> {

    /**
     * 获取当前正启用的配置
     * @return
     */
    public OssConfig getEnable() {
        LambdaQueryWrapper<OssConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OssConfig::getEnable, ENABLE);
        return this.getOne(wrapper, false);
    }
}
