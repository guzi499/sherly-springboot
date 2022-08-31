package com.guzi.sherly.service;

import cn.hutool.core.util.StrUtil;
import com.guzi.sherly.model.PageResult;
import com.guzi.sherly.model.dto.FlowModelInsertDTO;
import com.guzi.sherly.model.dto.FlowModelPageDTO;
import com.guzi.sherly.model.vo.FlowModelPageVO;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Model;
import org.flowable.engine.repository.ModelQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

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

    public PageResult<FlowModelPageVO> listPage(FlowModelPageDTO dto) {
        ModelQuery query = repositoryService.createModelQuery();
        List<Model> models = query.listPage((dto.getCurrent() - 1) * dto.getSize(), dto.getSize());
        long total = query.count();

        List<FlowModelPageVO> result = models.stream().map(e -> {
            FlowModelPageVO flowModelPageVO = new FlowModelPageVO();
            BeanUtils.copyProperties(e, flowModelPageVO);
            return flowModelPageVO;
        }).collect(Collectors.toList());

        return PageResult.build(result, total);
    }
}
