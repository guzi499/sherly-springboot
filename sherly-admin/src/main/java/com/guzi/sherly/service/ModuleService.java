package com.guzi.sherly.service;

import com.guzi.sherly.dao.ModuleDao;
import com.guzi.sherly.model.admin.Module;
import com.guzi.sherly.model.dto.ModuleInsertDTO;
import com.guzi.sherly.model.dto.ModuleUpdateDTO;
import com.guzi.sherly.model.exception.BizException;
import com.guzi.sherly.model.vo.ModuleVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.guzi.sherly.model.contants.CommonConstants.ROOT_PARENT_ID;
import static com.guzi.sherly.model.exception.enums.AdminErrorEnum.MODULE_HAS_CHILDREN;
import static com.guzi.sherly.model.exception.enums.AdminErrorEnum.MODULE_REPEAT;

/**
 * @author 李仁杰
 * @date 2022/9/1
 */
@Service
public class ModuleService {

    @Resource
    private ModuleDao moduleDao;

    /**
     * 查询模块树
     * @return
     */
    public List<ModuleVO> listTree() {
        List<Module> list = moduleDao.list();

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
        // 查重
        Module one = moduleDao.getByParentIdAndModuleNameOrModuleCode(dto.getParentId(), dto.getModuleName(), dto.getModuleCode());
        if (one != null) {
            throw new BizException(MODULE_REPEAT);
        }

        Module module = new Module();
        BeanUtils.copyProperties(dto, module);
        moduleDao.save(module);
    }

    /**
     * 模块更新
     * @param dto
     */
    public void updateOne(ModuleUpdateDTO dto) {
        // 查重
        Module one = moduleDao.getByParentIdAndModuleNameOrModuleCode(dto.getParentId(), dto.getModuleName(), dto.getModuleCode());
        // 如果待修改名称已存在且不为自身
        if (one != null && !Objects.equals(one.getModuleId(), dto.getModuleId())) {
            throw new BizException(MODULE_REPEAT);
        }

        Module module = new Module();
        BeanUtils.copyProperties(dto, module);
        moduleDao.updateById(module);
    }

    /**
     * 模块删除
     * @param moduleId
     */
    public void removeOne(Integer moduleId) {
        //是否存在子模块
        Long count = moduleDao.countByParentId(moduleId);
        if (count > 0) {
            throw new BizException(MODULE_HAS_CHILDREN);
        }
        moduleDao.removeById(moduleId);
    }
}
