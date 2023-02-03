package com.guzi.sherly.admin.department.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.guzi.sherly.admin.department.mapper.DepartmentMapper;
import com.guzi.sherly.admin.department.model.DepartmentDO;
import com.guzi.sherly.modules.mybatisplus.service.SherlyServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author 周孟凡
 * @date 2022/3/30
 */
@Service
public class DepartmentDao extends SherlyServiceImpl<DepartmentMapper, DepartmentDO> {

    /**
     * 根据部门名称查询部门数据
     * @param departmentName
     * @return
     */
    public DepartmentDO getByDepartmentName(String departmentName) {
        LambdaQueryWrapper<DepartmentDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DepartmentDO::getDepartmentName, departmentName);
        return this.getOne(wrapper, false);
    }
}
