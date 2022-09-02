package com.guzi.sherly.service;

import cn.hutool.core.util.StrUtil;
import com.guzi.sherly.model.PageResult;
import com.guzi.sherly.model.dto.FlowModelDeployDTO;
import com.guzi.sherly.model.dto.FlowModelInsertDTO;
import com.guzi.sherly.model.dto.FlowModelPageDTO;
import com.guzi.sherly.model.dto.FlowModelUpdateDTO;
import com.guzi.sherly.model.vo.FlowModelPageVO;
import com.guzi.sherly.model.vo.FlowModelVO;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Model;
import org.flowable.engine.repository.ModelQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    /**
     * 流程模型新增
     * @param dto
     */
    public void saveOne(FlowModelInsertDTO dto) {
        Model model = repositoryService.newModel();
        BeanUtils.copyProperties(dto, model);
        repositoryService.saveModel(model);
        repositoryService.addModelEditorSource(model.getId(), StrUtil.utf8Bytes(dto.getModelXml()));
    }

    /**
     * 流程模型分页
     * @param dto
     * @return
     */
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

    /**
     * 流程模型删除
     * @param id
     */
    public void removeOne(String id) {
        repositoryService.deleteModel(id);
    }

    /**
     * 流程模型更新
     * @param dto
     */
    public void updateOne(FlowModelUpdateDTO dto) {
        Model model = repositoryService.getModel(dto.getId());
        BeanUtils.copyProperties(dto, model);
        repositoryService.saveModel(model);
        repositoryService.addModelEditorSource(model.getId(), StrUtil.utf8Bytes(dto.getModelXml()));
    }

    /**
     * 流程模型详情
     * @param id
     * @return
     */
    public FlowModelVO getOne(String id) {
        FlowModelVO vo = new FlowModelVO();
        Model model = repositoryService.getModel(id);
        BeanUtils.copyProperties(model, vo);
        byte[] modelXmlBytes = repositoryService.getModelEditorSource(id);
        vo.setModelXml(StrUtil.utf8Str(modelXmlBytes));
        return vo;
    }

    /**
     * 流程模型部署
     * @param dto
     * @return
     */
    public void deploy(FlowModelDeployDTO dto) {
        Model model = repositoryService.getModel(dto.getId());

        byte[] modelXmlBytes = repositoryService.getModelEditorSource(dto.getId());

        repositoryService.createDeployment()
                .name(model.getName())
                .key(model.getKey())
                .category(model.getCategory())
                .addBytes(model.getName(), modelXmlBytes)
                .deploy();
    }
}
