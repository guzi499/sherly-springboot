package com.guzi.upr.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guzi.upr.mapper.admin.DepartmentMapper;
import com.guzi.upr.model.admin.Department;
import org.springframework.stereotype.Service;

/**
 * @ClassName: DepartmentManager
 * @author: 冰焰
 * @date: 2022/3/30
 * @Version: V1.0
 **/
@Service
public class DepartmentManager extends ServiceImpl<DepartmentMapper, Department> {

    /**
     * 部门名查重
     *
     * @param departmentName
     * @return
     */
    public Department getByDepartmentName(String departmentName) {
        LambdaQueryWrapper<Department> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Department::getDeptName, departmentName);
        return this.getOne(wrapper);
    }
}
