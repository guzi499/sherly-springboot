package com.guzi.sherly.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guzi.sherly.mapper.ModuleMapper;
import com.guzi.sherly.model.admin.Module;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModuleManager extends ServiceImpl<ModuleMapper, Module> {

    /**
     * 根据模块名称查询模块数据
     * @param moduleName
     * @return
     */
    public Module getByModuleName(String moduleName,String moduleCode,Integer parentId) {
        LambdaQueryWrapper<Module> wrapper = new LambdaQueryWrapper<>();
        wrapper
                .eq(Module::getModuleName, moduleName)
                .eq(Module::getParentId,parentId)
                .or()
                .eq(Module::getModuleCode,moduleCode);
        return this.getOne(wrapper, false);
    }

    /**
     * 根据父模块id查询子模块数据
     * @param moduleId
     * @return
     */
    public List<Module> getAll(Integer moduleId) {
        LambdaQueryWrapper<Module> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Module::getParentId, moduleId);
        return this.list(wrapper);
    }
}
