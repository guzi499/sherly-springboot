package com.guzi.upr.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.guzi.upr.manager.TenantManager;
import com.guzi.upr.manager.UserManager;
import com.guzi.upr.model.PageResult;
import com.guzi.upr.model.admin.Tenant;
import com.guzi.upr.model.dto.TenantInsertDTO;
import com.guzi.upr.model.dto.TenantPageDTO;
import com.guzi.upr.model.dto.TenantUpdateDTO;
import com.guzi.upr.model.vo.TenantPageVO;
import com.guzi.upr.util.ExecSqlUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


/**
 * @author 谷子毅
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
     *
     * @param dto
     * @return
     */
    public PageResult listPage(TenantPageDTO dto) {

        IPage<Tenant> page = tenantManager.listPage(dto.pageInfo(), dto.getTenantName(), dto.getTenantCode());

        List<TenantPageVO> result = page.getRecords().stream().map(e -> {
            TenantPageVO vo = new TenantPageVO();
            BeanUtils.copyProperties(e, vo);
            return vo;
        }).collect(Collectors.toList());

        return PageResult.build(result, page.getCurrent(), page.getSize(), page.getTotal());
    }

    /**
     * 租户新增
     *
     * @param dto
     */
    public void saveOne(TenantInsertDTO dto) {
        // 查重
        //Tenant one = tenantManager.getByTenantNameOrTenantCode(dto.getTenantName(), dto.getTenantCode());
        //if (one != null) {
        //    throw new BizException(ResultAdminEnum.TENANT_REPEAT);
        //}
        //
        //Tenant tenant = new Tenant();
        //BeanUtils.copyProperties(dto, tenant);
        //tenantManager.save(tenant);

        // 执行sql语句创建新租户的数据库表
        //execSqlUtil.execSql(SqlStatement.CREATE_TENANT, Collections.singletonMap(SqlParam.DATABASE, dto.getTenantCode()));
        //
        //// 设置要操作的租户数据库
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //ThreadLocalModel threadLocalModel = (ThreadLocalModel) authentication.getPrincipal();
        //threadLocalModel.setOperateTenantCode(dto.getTenantCode());
        //SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(threadLocalModel, null, authentication.getAuthorities()));
        //
        //// 添加租户数据
        //User user = new User();
        //user.setPhone(dto.get);
        //user.setPassword("123456");
        //userManager.save(user);
    }

    /**
     * 租户更新
     *
     * @param dto
     */
    public void updateOne(TenantUpdateDTO dto) {
        Tenant tenant = new Tenant();
        BeanUtils.copyProperties(dto, tenant);
        tenantManager.updateById(tenant);
    }

    /**
     * 租户删除
     *
     * @param id
     */
    public void removeOne(Long id) {
        tenantManager.removeById(id);
    }
}
