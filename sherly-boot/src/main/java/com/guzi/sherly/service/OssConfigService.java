package com.guzi.sherly.service;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guzi.sherly.common.model.PageResult;
import com.guzi.sherly.modules.oss.OssClientFactory;
import com.guzi.sherly.modules.oss.dao.OssConfigDao;
import com.guzi.sherly.modules.oss.dto.OssConfigInsertDTO;
import com.guzi.sherly.modules.oss.dto.OssConfigPageDTO;
import com.guzi.sherly.modules.oss.dto.OssConfigUpdateDTO;
import com.guzi.sherly.modules.oss.enums.OssTypeEnum;
import com.guzi.sherly.modules.oss.model.OssClientConfig;
import com.guzi.sherly.modules.oss.model.OssConfigDO;
import com.guzi.sherly.modules.oss.vo.OssConfigPageVO;
import com.guzi.sherly.modules.oss.vo.OssConfigVO;
import com.guzi.sherly.modules.security.util.SecurityUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.guzi.sherly.common.enums.UsableEnum.DISABLE;
import static com.guzi.sherly.common.enums.UsableEnum.ENABLE;

/**
 * @author 谷子毅
 * @date 2022/6/24
 */
@Service
public class OssConfigService {

    @Resource
    private OssConfigDao ossConfigDao;

    @Resource
    private OssClientFactory ossClientFactory;

    /**
     * 对象存储配置分页
     * @param dto
     * @return
     */
    public PageResult<OssConfigPageVO> listPage(OssConfigPageDTO dto) {
        IPage<OssConfigDO> page = ossConfigDao.page(new Page<>(dto.getCurrent(), dto.getSize()));

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
    public OssConfigVO getOne(Long configId) {
        OssConfigDO ossConfigDO = ossConfigDao.getById(configId);
        OssClientConfig config = ossConfigDO.getConfig();
        String configStr = JSONUtil.toJsonStr(config);
        Map<String, Object> map = JSONUtil.toBean(configStr, Map.class);

        OssConfigVO vo = new OssConfigVO();
        BeanUtils.copyProperties(ossConfigDO, vo);
        vo.setConfig(map);

        return vo;
    }

    /**
     * 对象存储配置新增
     * @param dto
     */
    public void saveOne(OssConfigInsertDTO dto) {
        OssConfigDO ossConfigDO = new OssConfigDO();
        BeanUtils.copyProperties(dto, ossConfigDO);
        ossConfigDO.setEnable(DISABLE);
        ossConfigDO.setConfig(parseConfig(dto.getType(), dto.getConfig()));
        ossConfigDao.save(ossConfigDO);
    }

    /**
     * 对象存储配置更新
     * @param dto
     */
    public void updateOne(OssConfigUpdateDTO dto) {
        OssConfigDO ossConfigDO = new OssConfigDO();
        BeanUtils.copyProperties(dto, ossConfigDO);
        ossConfigDO.setConfig(parseConfig(dto.getType(), dto.getConfig()));
        ossConfigDao.updateById(ossConfigDO);

        // 如果是激活状态那么需要从clients容器中删除该租户的client
        if (ossConfigDO.getEnable() == ENABLE) {
            ossClientFactory.removeOssClient(SecurityUtil.getTenantCode());
        }
    }

    /**
     * 解析map转换成object
     * @param type
     * @param config
     * @return
     */
    private OssClientConfig parseConfig(Integer type, Map<String, Object> config) {
        Class<?> configClass = OssTypeEnum.getByType(type).getOssConfigClass();
        String configStr = JSONUtil.toJsonStr(config);
        return (OssClientConfig) JSONUtil.toBean(configStr, configClass);
    }

    /**
     * 对象存储配置删除
     * @param configId
     */
    public void removeOne(Long configId) {
        OssConfigDO config = ossConfigDao.getById(configId);
        ossConfigDao.removeById(configId);
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
        List<OssConfigDO> list = ossConfigDao.list();
        list = list.stream().peek(e -> {
            e.setEnable(DISABLE);
            if (Objects.equals(configId, e.getConfigId())) {
                e.setEnable(ENABLE);
            }
        }).collect(Collectors.toList());

        ossConfigDao.updateBatchById(list);

        // 从clients容器中删除该租户的client
        ossClientFactory.removeOssClient(SecurityUtil.getTenantCode());
    }
}
