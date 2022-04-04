package com.guzi.upr.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.guzi.upr.constants.SqlParam;
import com.guzi.upr.constants.SqlStatement;
import com.guzi.upr.enums.ResultAdminEnum;
import com.guzi.upr.exception.BizException;
import com.guzi.upr.interceptor.TokenParam;
import com.guzi.upr.manager.TenantManager;
import com.guzi.upr.manager.UserManager;
import com.guzi.upr.model.PageResult;
import com.guzi.upr.model.admin.Tenant;
import com.guzi.upr.model.admin.User;
import com.guzi.upr.model.dto.TenantInsertDTO;
import com.guzi.upr.model.dto.TenantPageDTO;
import com.guzi.upr.model.dto.TenantUpdateDTO;
import com.guzi.upr.model.vo.TenantPageVO;
import com.guzi.upr.util.ExecSqlUtil;
import com.guzi.upr.util.ThreadLocalUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * @author 谷子毅
 * @email guzyc@digitalchina.com
 * @date 2022/3/24
 */
@Service
public class TenantService {

    @Autowired
    private TenantManager tenantManager;

    @Autowired
    private UserManager userManager;

    @Autowired
    private ExecSqlUtil execSqlUtil;

    /**
     * 租户条件分页
     * @param dto
     * @return
     */
    public PageResult listPage(TenantPageDTO dto) {

        IPage page = tenantManager.listPage(dto);

        List<TenantPageVO> result = new ArrayList<>();
        page.getRecords().forEach(e -> {
            TenantPageVO vo = new TenantPageVO();
            BeanUtils.copyProperties(e, vo);
            result.add(vo);
        });

        return PageResult.build(result, page.getCurrent(), page.getSize(), page.getTotal());
    }

    /**
     * 租户新增
     * @param dto
     */
    public void saveOne(TenantInsertDTO dto) {
        // 查重
        Tenant one = tenantManager.getByTenantNameOrTenantCode(dto.getTenantName(), dto.getTenantCode());
        if (one != null) {
            throw new BizException(ResultAdminEnum.TENANT_REPEAT);
        }

        Tenant tenant = new Tenant();
        BeanUtils.copyProperties(dto, tenant);
        tenantManager.save(tenant);

        // 执行sql语句创建新租户的数据库表
        execSqlUtil.execSql(SqlStatement.CREATE_TENANT, Collections.singletonMap(SqlParam.DATABASE, dto.getTenantCode()));

        // 设置要操作的租户数据库
        TokenParam tokenParam = (TokenParam) ThreadLocalUtil.get();
        tokenParam.setOperateTenantCode(dto.getTenantCode());
        ThreadLocalUtil.set(tokenParam);

        // 添加租户数据
        User user = new User();
        user.setPhone(dto.getPhone());
        user.setPassword("123456");
        userManager.save(user);
    }

    /**
     * 租户更新
     * @param dto
     */
    public void updateOne(TenantUpdateDTO dto) {
        Tenant tenant = new Tenant();
        BeanUtils.copyProperties(dto, tenant);
        tenantManager.updateById(tenant);
    }

    /**
     * 租户删除
     * @param id
     */
    public void removeOne(Long id) {
        tenantManager.removeById(id);
    }
}
