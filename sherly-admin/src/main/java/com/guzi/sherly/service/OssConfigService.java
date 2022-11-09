package com.guzi.sherly.service;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guzi.sherly.manager.OssConfigManager;
import com.guzi.sherly.model.PageResult;
import com.guzi.sherly.model.admin.OssConfig;
import com.guzi.sherly.model.dto.OssConfigInsertDTO;
import com.guzi.sherly.model.dto.OssConfigPageDTO;
import com.guzi.sherly.model.dto.OssConfigUpdateDTO;
import com.guzi.sherly.model.vo.OssConfigPageVO;
import com.guzi.sherly.model.vo.OssConfigVO;
import com.guzi.sherly.modules.security.util.SecurityUtil;
import com.guzi.sherly.modules.storage.OssClientFactory;
import com.guzi.sherly.modules.storage.enums.OssTypeEnum;
import com.guzi.sherly.modules.storage.model.OssClientConfig;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.guzi.sherly.model.contants.CommonConstants.DISABLE;
import static com.guzi.sherly.model.contants.CommonConstants.ENABLE;

/**
 * @author 谷子毅
 * @date 2022/6/24
 */
@Service
public class OssConfigService {

    @Resource
    private OssConfigManager ossConfigManager;

    @Resource
    private OssClientFactory ossClientFactory;

    /**
     * 对象存储配置分页
     * @param dto
     * @return
     */
    public PageResult<OssConfigPageVO> listPage(OssConfigPageDTO dto) {
        IPage<OssConfig> page = ossConfigManager.page(new Page<>(dto.getCurrent(), dto.getSize()));

        List<OssConfigPageVO> result = page.getRecords().stream().map(e -> {
            OssConfigPageVO vo = new OssConfigPageVO();
            BeanUtils.copyProperties(e, vo);
            return vo;
        }).collect(Collectors.toList());

        return PageResult.build(result, page.getTotal());
    }

    /**
     * 对象存储配置详情
     * @param configId
     * @return
     */
    public OssConfigVO getOne(Long configId) throws Exception {
        OssConfig ossConfig = ossConfigManager.getById(configId);
        OssClientConfig config = ossConfig.getConfig();
        String configStr = JSONUtil.toJsonStr(config);
        Map<String, Object> map = JSONUtil.toBean(configStr, Map.class);

        OssConfigVO vo = new OssConfigVO();
        BeanUtils.copyProperties(ossConfig, vo);
        vo.setConfig(map);

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
        ossConfig.setEnable(DISABLE);
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
        if (Objects.equals(ossConfig.getEnable(), ENABLE)) {
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
        String configStr = JSONUtil.toJsonStr(config);
        return (OssClientConfig) JSONUtil.toBean(configStr, configClass);
    }

    /**
     * 对象存储配置删除
     * @param configId
     */
    public void removeOne(Long configId) {
        OssConfig config = ossConfigManager.getById(configId);
        ossConfigManager.removeById(configId);
        if (Objects.equals(config.getEnable(), ENABLE)) {
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
            e.setEnable(DISABLE);
            if (Objects.equals(configId, e.getConfigId())) {
                e.setEnable(ENABLE);
            }
        }).collect(Collectors.toList());

        ossConfigManager.updateBatchById(list);

        // 从clients容器中删除该租户的client
        ossClientFactory.removeOssClient(SecurityUtil.getTenantCode());
    }
}
