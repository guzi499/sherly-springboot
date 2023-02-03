package com.guzi.sherly.admin.module.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.guzi.sherly.admin.module.mapper.ModuleMapper;
import com.guzi.sherly.admin.module.model.ModuleDO;
import com.guzi.sherly.modules.mybatisplus.service.SherlyServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author 李仁杰
 * @date 2022/9/1
 */
@Service
public class ModuleDao extends SherlyServiceImpl<ModuleMapper, ModuleDO> {

    /**
     * 根据父模块id、模块名称或模块代码查询模块数据
     * @param moduleName
     * @param moduleCode
     * @param parentId
     * @return
     */
    public ModuleDO getByParentIdAndModuleNameOrModuleCode(Integer parentId, String moduleName, String moduleCode) {
        LambdaQueryWrapper<ModuleDO> wrapper = new LambdaQueryWrapper<>();
        wrapper
                .eq(ModuleDO::getParentId,parentId)
                .eq(ModuleDO::getModuleName, moduleName)
                .or()
                .eq(ModuleDO::getModuleCode,moduleCode);
        return this.getOne(wrapper, false);
    }

    /**
     * 根据父模块id查询模块数据数量
     * @param moduleId
     * @return
     */
    public Long countByParentId(Integer moduleId) {
        LambdaQueryWrapper<ModuleDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ModuleDO::getParentId, moduleId);
        return this.count(wrapper);
    }
}
