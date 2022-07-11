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

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.guzi.upr.model.contants.CommonConstants.ROOT_PARENT_ID;

/**
 * @author 周孟凡
 * @date 2022/3/30
 */
@Service
public class DepartmentService {
    @Autowired
    private DepartmentManager departmentManager;

    @Autowired
    private UserManager userManager;

    /**
     * 查询部门树
     *
     * @return
     */
    public List<DepartmentVO> listTree() {
        List<Department> list = departmentManager.list();

        // 对象转换成vo类型
        List<DepartmentVO> all = list.stream()
                .sorted(Comparator.comparing(Department::getSort))
                .map(e -> {
                    DepartmentVO vo = new DepartmentVO();
                    BeanUtils.copyProperties(e, vo);
                    return vo;
                }).collect(Collectors.toList());

        // 拼装子结点并返回
        return all.stream()
                .filter(e -> Objects.equals(e.getParentId(), ROOT_PARENT_ID))
                .peek(e -> e.setChildren(getChildren(e, all)))
                .collect(Collectors.toList());
    }

    /**
     * 递归拼装子结点
     *
     * @param parent
     * @param all
     * @return
     */
    private List<DepartmentVO> getChildren(DepartmentVO parent, List<DepartmentVO> all) {
        return all.stream()
                .filter(e -> Objects.equals(e.getParentId(), parent.getDepartmentId()))
                .peek(e -> e.setChildren(getChildren(e, all)))
                .collect(Collectors.toList());
    }

    /**
     * 部门新增
     *
     * @param dto
     */
    public void saveOne(DepartmentInsertDTO dto) {
        // 查重
        Department one = departmentManager.getByDepartmentName(dto.getDepartmentName());
        if (one != null) {
            throw new BizException(ResultAdminEnum.DEPARTMENT_REPEAT);
        }

        Department department = new Department();
        BeanUtils.copyProperties(dto, department);
        departmentManager.save(department);

    }

    /**
     * 部门更新
     *
     * @param dto
     */
    public void updateOne(DepartmentUpdateDTO dto) {
        // 查重 排除自身
        Department one = departmentManager.getByDepartmentName(dto.getDepartmentName());
        // 如果待修改名称已存在且不为自身
        if (one != null && !Objects.equals(one.getDepartmentId(), dto.getDepartmentId())) {
            throw new BizException(ResultAdminEnum.DEPARTMENT_REPEAT);
        }

        Department department = new Department();
        BeanUtils.copyProperties(dto, department);
        departmentManager.updateById(department);
    }

    /**
     * 部门删除
     *
     * @param departmentId
     */
    public void removeOne(Long departmentId) {
        departmentManager.removeById(departmentId);
        userManager.updateDepartmentId(departmentId);
    }
}
