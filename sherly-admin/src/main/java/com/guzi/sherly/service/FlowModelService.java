package com.guzi.sherly.service;

import cn.hutool.core.util.StrUtil;
import com.guzi.sherly.model.dto.FlowModelInsertDTO;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

/**
 * @author 谷子毅
 * @date 2022/8/31
 */
@Service
public class FlowModelService {

    @Autowired
    private RepositoryService repositoryService;

    public void saveOne(FlowModelInsertDTO dto) {
        Model model = repositoryService.newModel();
        model.setName(dto.getName());
        model.setKey(dto.getKey());
        repositoryService.saveModel(model);
        if (StrUtil.isNotBlank(dto.getModelXml())) {
            repositoryService.addModelEditorSource(model.getId(), dto.getModelXml().getBytes(StandardCharsets.UTF_8));
        }
    }
}
