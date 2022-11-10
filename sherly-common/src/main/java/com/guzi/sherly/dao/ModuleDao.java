package com.guzi.sherly.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.guzi.sherly.mapper.ModuleMapper;
import com.guzi.sherly.model.admin.Module;
import com.guzi.sherly.modules.mybatisplus.service.SherlyServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author 李仁杰
 * @date 2022/9/1
 */
@Service
public class ModuleDao extends SherlyServiceImpl<ModuleMapper, Module> {

    /**
     * 根据父模块id、模块名称或模块代码查询模块数据
     * @param moduleName
     * @param moduleCode
     * @param parentId
     * @return
     */
    public Module getByParentIdAndModuleNameOrModuleCode(Integer parentId, String moduleName, String moduleCode) {
        LambdaQueryWrapper<Module> wrapper = new LambdaQueryWrapper<>();
        wrapper
                .eq(Module::getParentId,parentId)
                .eq(Module::getModuleName, moduleName)
                .or()
                .eq(Module::getModuleCode,moduleCode);
        return this.getOne(wrapper, false);
    }

    /**
     * 根据父模块id查询模块数据数量
     * @param moduleId
     * @return
     */
    public Long countByParentId(Integer moduleId) {
        LambdaQueryWrapper<Module> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Module::getParentId, moduleId);
        return this.count(wrapper);
    }
}
