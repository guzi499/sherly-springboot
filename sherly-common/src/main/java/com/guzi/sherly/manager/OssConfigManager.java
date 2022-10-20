package com.guzi.sherly.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guzi.sherly.mapper.OssConfigMapper;
import com.guzi.sherly.model.admin.OssConfig;
import com.guzi.sherly.modules.mybatisplus.service.SherlyServiceImpl;
import org.springframework.stereotype.Service;

import static com.guzi.sherly.model.contants.CommonConstants.ENABLE;

/**
 * @author 谷子毅
 * @date 2022/6/24
 */
@Service
public class OssConfigManager extends SherlyServiceImpl<OssConfigMapper, OssConfig> {

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
