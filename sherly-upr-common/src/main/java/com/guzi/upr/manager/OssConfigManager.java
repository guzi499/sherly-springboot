package com.guzi.upr.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guzi.upr.mapper.admin.OssConfigMapper;
import com.guzi.upr.model.admin.OssConfig;
import org.springframework.stereotype.Service;

import static com.guzi.upr.model.contants.CommonConstants.ENABLE;

/**
 * @author 谷子毅
 * @date 2022/6/24
 */
@Service
public class OssConfigManager extends ServiceImpl<OssConfigMapper, OssConfig> {

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
