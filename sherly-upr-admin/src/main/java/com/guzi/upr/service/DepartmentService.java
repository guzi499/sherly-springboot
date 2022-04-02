package com.guzi.upr.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.guzi.upr.enums.DepartmentEnum;
import com.guzi.upr.enums.ResultAdminEnum;
import com.guzi.upr.exception.BizException;
import com.guzi.upr.manager.DepartmentManager;
import com.guzi.upr.model.PageQuery;
import com.guzi.upr.model.PageResult;
import com.guzi.upr.model.admin.Department;
import com.guzi.upr.model.admin.Permission;
import com.guzi.upr.model.admin.User;
import com.guzi.upr.model.dto.DepartmentInsertDTO;
import com.guzi.upr.model.dto.UserUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: DepartmentService
 * @author: 冰焰
 * @date: 2022/3/30
 * @Version: V1.0
 **/
@Service
public class DepartmentService {
    @Autowired
    private DepartmentManager departmentManager;

    public PageResult<User> page(PageQuery pageQuery) {
        IPage<User> page = departmentManager.page(pageQuery.getPage());

        return new PageResult<User>(page.getRecords(), page.getCurrent(), page.getSize(), page.getTotal());
    }

    public void saveOne(DepartmentInsertDTO dto) {
        // 查重
        Department one = departmentManager.getByDeptName(dto.getDeptName(), dto.getParentId());
        if (one != null) {
            throw new BizException(DepartmentEnum.DEPARTMENT_IS_EXIST);
        }
        departmentManager.saveOne(dto);
    }

    public void updateOne(DepartmentInsertDTO dto) {
        // 检测是否存在
        Department one = departmentManager.getByDeptName(dto.getDeptName(), dto.getParentId());
        if (one == null) {
            throw new BizException(DepartmentEnum.DEPARTMENT_NOT_EXIST);
        }
        departmentManager.updateOne(dto);
    }

    public void removeOne(Long id) {
        departmentManager.removeById(id);
    }
}
