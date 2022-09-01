package com.guzi.sherly.service;

import com.guzi.sherly.manager.ErrorCodeManager;
import com.guzi.sherly.manager.ModuleManager;
import com.guzi.sherly.model.admin.Module;
import com.guzi.sherly.model.dto.ModuleInsertDTO;
import com.guzi.sherly.model.dto.ModuleUpdateDTO;
import com.guzi.sherly.model.exception.BizException;
import com.guzi.sherly.model.vo.ModuleVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.guzi.sherly.model.contants.CommonConstants.ROOT_PARENT_ID;
import static com.guzi.sherly.model.exception.enums.AdminErrorEnum.*;

@Service
public class ModuleService {

    @Autowired
    private ModuleManager moduleManager;

    @Autowired
    private ErrorCodeManager errorCodeManager;

    /**
     * 查询模块树
     * @return
     */
    public List<ModuleVO> listTree() {
        List<Module> list = moduleManager.list();

        //对象转换为vo类型
        List<ModuleVO> all = list.stream()
                .sorted(Comparator.comparing(Module::getSort))
                .map(e -> {
                    ModuleVO vo = new ModuleVO();
                    BeanUtils.copyProperties(e, vo);
                    return vo;
                }).collect(Collectors.toList());

        //拼装子节点并返回
        return all.stream()
                .filter(e -> Objects.equals(e.getParentId(), ROOT_PARENT_ID.intValue()))
                .peek(e -> e.setChildren(getChildren(e, all)))
                .collect(Collectors.toList());
    }

    /**
     * 递归拼装子结点
     * @param parent
     * @param all
     * @return
     */
    private List<ModuleVO> getChildren(ModuleVO parent, List<ModuleVO> all) {
        return all.stream()
                .filter(e -> Objects.equals(e.getParentId(), parent.getModuleId()))
                .peek(e -> e.setChildren(getChildren(e, all)))
                .collect(Collectors.toList());
    }

    /**
     * 模块新增
     * @param dto
     */
    public void saveOne(ModuleInsertDTO dto) {

        Module one = moduleManager.getByModuleName(dto.getModuleName(), dto.getModuleCode(),dto.getParentId());
        // 查重 同级禁止新增相同模块
        if (one != null && Objects.equals(dto.getParentId(), one.getParentId())) {
            throw new BizException(MODULE_REPEAT);
        }
        Module module = new Module();
        BeanUtils.copyProperties(dto, module);
        moduleManager.save(module);
    }

    /**
     * 模块更新
     * @param dto
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateOne(ModuleUpdateDTO dto) {

        Module one = moduleManager.getByModuleName(dto.getModuleName(), dto.getModuleCode(),dto.getParentId());

        if (one != null) {
            List<Module> list = moduleManager.getAll(dto.getModuleId());
            //存在子模块的父模块和不为自身的模块禁止修改
            if ((Objects.equals(dto.getParentId(), ROOT_PARENT_ID.intValue()) && list != null) || !Objects.equals(dto.getModuleId(), one.getModuleId())) {
                throw new BizException(UPDATE_MODULE_ERROR);
            }
            //修改子模块同时更新错误列表
            if (!Objects.equals(dto.getParentId(), ROOT_PARENT_ID.intValue()) && Objects.equals(dto.getParentId(), one.getParentId())) {
                ErrorCodeService errorCodeService = new ErrorCodeService();
                errorCodeService.updateModuleCode(dto.getModuleId(), dto.getModuleCode());
            }
        }
        Module module = new Module();
        BeanUtils.copyProperties(dto, module);
        moduleManager.updateById(module);
    }

    /**
     * 模块删除
     * @param moduleId
     */
    public void removeOne(Integer moduleId) {
        //是否存在子模块
        List<Module> list = moduleManager.getAll(moduleId);
        if (list.size() > 0) {
            throw new BizException(DELETE_MODULE_ERROR);
        }
        moduleManager.removeById(moduleId);
    }
}
