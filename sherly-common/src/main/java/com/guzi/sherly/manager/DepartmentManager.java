package com.guzi.sherly.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guzi.sherly.mapper.DepartmentMapper;
import com.guzi.sherly.model.admin.Department;
import com.guzi.sherly.modules.mybatisplus.service.SherlyServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author 周孟凡
 * @date 2022/3/30
 */
@Service
public class DepartmentManager extends SherlyServiceImpl<DepartmentMapper, Department> {

    /**
     * 根据部门名称查询部门数据
     * @param departmentName
     * @return
     */
    public Department getByDepartmentName(String departmentName) {
        LambdaQueryWrapper<Department> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Department::getDepartmentName, departmentName);
        return this.getOne(wrapper, false);
    }
}
