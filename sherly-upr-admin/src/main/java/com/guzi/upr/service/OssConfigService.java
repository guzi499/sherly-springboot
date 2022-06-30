package com.guzi.upr.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guzi.upr.manager.OssConfigManager;
import com.guzi.upr.model.PageResult;
import com.guzi.upr.model.admin.OssClientConfig;
import com.guzi.upr.model.admin.OssConfig;
import com.guzi.upr.model.dto.OssConfigInsertDTO;
import com.guzi.upr.model.dto.OssConfigPageDTO;
import com.guzi.upr.model.dto.OssConfigUpdateDTO;
import com.guzi.upr.model.vo.OssConfigPageVO;
import com.guzi.upr.model.vo.OssConfigVO;
import com.guzi.upr.security.util.SecurityUtil;
import com.guzi.upr.storage.OssClientFactory;
import com.guzi.upr.storage.enums.OssTypeEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
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

    @Autowired
    private OssClientFactory ossClientFactory;

    /**
     * 对象存储配置分页
     * @param dto
     * @return
     */
    public PageResult<OssConfigPageVO> listPage(OssConfigPageDTO dto) {
        IPage<OssConfig> page = ossConfigManager.page(dto.pageInfo());

        List<OssConfigPageVO> result = page.getRecords().stream().map(e -> {
            OssConfigPageVO vo = new OssConfigPageVO();
            BeanUtils.copyProperties(e, vo);
            return vo;
        }).collect(Collectors.toList());

        return PageResult.build(result, page.getCurrent(), page.getSize(), page.getTotal());
    }

    /**
     * 对象存储配置详情
     * @param configId
     * @return
     */
    public OssConfigVO getOne(Long configId) {
        OssConfig ossConfig = ossConfigManager.getById(configId);
        OssConfigVO vo = new OssConfigVO();
        BeanUtils.copyProperties(ossConfig, vo);
        return vo;
    }

    /**
     * 对象存储配置新增
     * @param dto
     * @throws Exception
     */
    public void saveOne(OssConfigInsertDTO dto) throws Exception {
        OssConfig ossConfig = new OssConfig();
        BeanUtils.copyProperties(dto, ossConfig);
        ossConfig.setEnable(0);
        ossConfig.setConfig(parseConfig(dto.getType(), dto.getConfig()));
        ossConfigManager.save(ossConfig);
    }

    /**
     * 对象存储配置更新
     * @param dto
     * @throws Exception
     */
    public void updateOne(OssConfigUpdateDTO dto) throws Exception {
        OssConfig ossConfig = new OssConfig();
        BeanUtils.copyProperties(dto, ossConfig);
        ossConfig.setConfig(parseConfig(dto.getType(), dto.getConfig()));
        ossConfigManager.updateById(ossConfig);

        // 如果是激活状态那么需要从clients容器中删除该租户的client
        if (ossConfig.getEnable() == 1) {
            ossClientFactory.removeOssClient(SecurityUtil.getTenantCode());
        }
    }

    /**
     * 解析map转换成object
     * @param type
     * @param config
     * @return
     * @throws Exception
     */
    private OssClientConfig parseConfig(Integer type, Map<String, Object> config) throws Exception {
        Class<?> configClass = OssTypeEnum.getByType(type).getOssConfigClass();
        String configStr = OBJECTMAPPER.writeValueAsString(config);
        return (OssClientConfig) OBJECTMAPPER.readValue(configStr, configClass);
    }

    /**
     * 对象存储配置删除
     * @param configId
     */
    public void removeOne(Long configId) {
        OssConfig config = ossConfigManager.getById(configId);
        ossConfigManager.removeById(configId);
        if (config.getEnable() == 1) {
            // 从clients容器中删除该租户的client
            ossClientFactory.removeOssClient(SecurityUtil.getTenantCode());
        }
    }

    /**
     * 对象存储配置激活
     * @param configId
     */
    public void enableOne(Long configId) {
        List<OssConfig> list = ossConfigManager.list();
        list = list.stream().peek(e -> {
            e.setEnable(0);
            if (Objects.equals(configId, e.getConfigId())) {
                e.setEnable(1);
            }
        }).collect(Collectors.toList());

        ossConfigManager.updateBatchById(list);

        // 从clients容器中删除该租户的client
        ossClientFactory.removeOssClient(SecurityUtil.getTenantCode());
    }
}
