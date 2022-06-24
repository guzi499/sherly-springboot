package com.guzi.upr.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guzi.upr.manager.OssConfigManager;
import com.guzi.upr.model.PageResult;
import com.guzi.upr.model.admin.OssClientConfig;
import com.guzi.upr.model.admin.OssConfig;
import com.guzi.upr.model.dto.OssConfigInsertDTO;
import com.guzi.upr.model.dto.OssConfigPageDTO;
import com.guzi.upr.model.vo.OssConfigPageVO;
import com.guzi.upr.model.vo.OssConfigVO;
import com.guzi.upr.storage.enums.OssTypeEnum;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 谷子毅
 * @date 2022/6/24
 */
@Service
public class OssConfigService {

    private static final ObjectMapper OBJECTMAPPER = new ObjectMapper();

    @Autowired
    private OssConfigManager ossConfigManager;

    public PageResult<OssConfigPageVO> listPage(OssConfigPageDTO dto) {
        IPage<OssConfig> page = ossConfigManager.page(dto.pageInfo());
        List<OssConfigPageVO> result = page.getRecords().stream().map(e -> {
            OssConfigPageVO vo = new OssConfigPageVO();
            BeanUtils.copyProperties(e, vo);
            return vo;
        }).collect(Collectors.toList());

        return PageResult.build(result, page.getCurrent(), page.getSize(), page.getTotal());
    }

    public OssConfigVO getOne(Long configId) {
        OssConfig ossConfig = ossConfigManager.getById(configId);
        OssConfigVO vo = new OssConfigVO();
        BeanUtils.copyProperties(ossConfig, vo);
        return vo;
    }

    public void saveOne(OssConfigInsertDTO dto) {
        OssConfig ossConfig = new OssConfig();
        BeanUtils.copyProperties(dto, ossConfig);
        ossConfig.setEnable(0);
        ossConfig.setConfig(parseConfig(dto.getType(), dto.getConfig()));
        ossConfigManager.save(ossConfig);
    }

    @SneakyThrows
    private OssClientConfig parseConfig(Integer type, Map<String, Object> config) {
        Class<?> configClass = OssTypeEnum.getByType(type).getOssConfigClass();
        String configStr = OBJECTMAPPER.writeValueAsString(config);
        return (OssClientConfig) OBJECTMAPPER.readValue(configStr, configClass);
    }

    public void removeOne(Long configId) {
        ossConfigManager.removeById(configId);
    }
}
