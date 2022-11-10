package com.guzi.sherly.service;

import com.guzi.sherly.dao.DepartmentDao;
import com.guzi.sherly.dao.UserDao;
import com.guzi.sherly.model.admin.Department;
import com.guzi.sherly.model.dto.DepartmentInsertDTO;
import com.guzi.sherly.model.dto.DepartmentUpdateDTO;
import com.guzi.sherly.model.exception.BizException;
import com.guzi.sherly.model.vo.DepartmentVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.guzi.sherly.model.contants.CommonConstants.ROOT_PARENT_ID;
import static com.guzi.sherly.model.exception.enums.AdminErrorEnum.*;

/**
 * @author 周孟凡
 * @date 2022/3/30
 */
@Service
public class DepartmentService {

    @Resource
    private DepartmentDao departmentDao;

    @Resource
    private UserDao userDao;

    /**
     * 查询部门树
     * @return
     */
    public List<DepartmentVO> listTree() {
        List<Department> list = departmentDao.list();

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
     * @param dto
     */
    public void saveOne(DepartmentInsertDTO dto) {
        // 查重
        Department one = departmentDao.getByDepartmentName(dto.getDepartmentName());
        if (one != null) {
            throw new BizException(DEPARTMENT_REPEAT);
        }

        Department department = new Department();
        BeanUtils.copyProperties(dto, department);
        departmentDao.save(department);

    }

    /**
     * 部门更新
     * @param dto
     */
    public void updateOne(DepartmentUpdateDTO dto) {
        if (Objects.equals(dto.getDepartmentId(), 1L)) {
            throw new BizException(UPDATE_DEPT_ERROR);
        }
        // 查重 排除自身
        Department one = departmentDao.getByDepartmentName(dto.getDepartmentName());
        // 如果待修改名称已存在且不为自身
        if (one != null && !Objects.equals(one.getDepartmentId(), dto.getDepartmentId())) {
            throw new BizException(DEPARTMENT_REPEAT);
        }

        Department department = new Department();
        BeanUtils.copyProperties(dto, department);
        departmentDao.updateById(department);
    }

    /**
     * 部门删除
     * @param departmentId
     */
    @Transactional(rollbackFor = Exception.class)
    public void removeOne(Long departmentId) {
        if (Objects.equals(departmentId, 1L)) {
            throw new BizException(DELETE_DEPT_ERROR);
        }
        departmentDao.removeById(departmentId);
        userDao.updateDepartmentId(departmentId);
    }
}
