package com.guzi.upr.service;

import com.guzi.upr.enums.ResultAdminEnum;
import com.guzi.upr.exception.BizException;
import com.guzi.upr.manager.DepartmentManager;
import com.guzi.upr.manager.UserManager;
import com.guzi.upr.model.admin.Department;
import com.guzi.upr.model.dto.DepartmentInsertDTO;
import com.guzi.upr.model.dto.DepartmentUpdateDTO;
import com.guzi.upr.model.vo.DepartmentVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private UserManager userManager;

    /**
     * 查询部门树
     * @return
     */
    public List<DepartmentVO> listTree() {
        List<Department> list = departmentManager.list();
        // 对象转换成vo类型
        List<DepartmentVO> all = list.stream().map(e -> {
            DepartmentVO vo = new DepartmentVO();
            BeanUtils.copyProperties(e, vo);
            return vo;
        }).collect(Collectors.toList());

        // 拼装子结点并返回
        return all.stream()
                .filter(e -> e.getParentId() == 0)
                .peek(e -> e.setChildren(getChildren(e, all)))
                .collect(Collectors.toList());
    }

    /**
     * 递归拼装子结点
     * @param parent
     * @param all
     * @return
     */
    private List<DepartmentVO> getChildren(DepartmentVO parent, List<DepartmentVO> all) {
        return all.stream()
                .filter(e -> e.getParentId().equals(parent.getDeptId()))
                .peek(e -> e.setChildren(getChildren(e, all)))
                .collect(Collectors.toList());
    }

    /**
     * 部门新增
     * @param dto
     */
    public void saveOne(DepartmentInsertDTO dto) {
        // 查重
        Department one = departmentManager.getByDepartmentName(dto.getDeptName());
        if (one != null) {
            throw new BizException(ResultAdminEnum.DEPARTMENT_REPEAT);
        }

        Department department = new Department();
        BeanUtils.copyProperties(dto, department);
        departmentManager.save(department);

    }

    /**
     * 部门更新
     * @param dto
     */
    public void updateOne(DepartmentUpdateDTO dto) {
        // 查重 排除自身
        Department one = departmentManager.getByDepartmentName(dto.getDeptName());
        if (one != null && !one.getDeptId().equals(dto.getDeptId())) {
            throw new BizException(ResultAdminEnum.DEPARTMENT_REPEAT);
        }

        Department department = new Department();
        BeanUtils.copyProperties(dto, department);
        departmentManager.updateById(department);
    }

    /**
     * 部门删除
     * @param departmentId
     */
    public void removeOne(Long departmentId) {
        departmentManager.removeById(departmentId);
        userManager.updateDepartmentId(departmentId);
    }
}
