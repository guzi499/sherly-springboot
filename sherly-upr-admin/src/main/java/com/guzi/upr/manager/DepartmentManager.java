package com.guzi.upr.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guzi.upr.mapper.admin.DepartmentMapper;
import com.guzi.upr.model.admin.Department;
import com.guzi.upr.model.admin.Permission;
import com.guzi.upr.model.admin.User;
import com.guzi.upr.model.dto.DepartmentInsertDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * @ClassName: DepartmentManager
 * @author: 冰焰
 * @date: 2022/3/30
 * @Version: V1.0
 **/
@Service
public class DepartmentManager extends ServiceImpl<DepartmentMapper, Department> {
    public void saveOne(DepartmentInsertDTO dto) {
        Department department = new Department();
        BeanUtils.copyProperties(dto, department);
        save(department);
    }

    public void updateOne(DepartmentInsertDTO dto) {
        Department department = new Department();
        BeanUtils.copyProperties(dto, department);
        updateById(department);
    }

    public Department getByDeptName(String deptName, Long parentId) {
        LambdaQueryWrapper<Department> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Department::getDeptName, deptName)
                .eq(Department::getParentId, parentId);
        return this.getOne(wrapper);
    }
}
