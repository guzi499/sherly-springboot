package com.guzi.upr.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guzi.upr.mapper.admin.TenantMapper;
import com.guzi.upr.model.admin.Tenant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 谷子毅
 * @email guzyc@digitalchina.com
 * @date 2022/3/22
 */
@RestController
@RequestMapping("/tenant")
@Api(tags = "租户相关")
public class TenantController {

    @Autowired
    private TenantMapper tenantMapper;

    @ApiOperation("新增")
    @GetMapping("/add")
    public String add() {
        Tenant tenant = new Tenant();
        tenant.setTenantName("第一");
        tenantMapper.insert(tenant);
        return "ok";
    }

    @ApiOperation("更新")
    @GetMapping("/update")
    public String update() {
        Tenant tenant = new Tenant();
        tenant.setId(5);
        tenant.setTenantName("guaguagau");
        tenantMapper.updateById(tenant);
        return "ok";
    }

    @ApiOperation("分页")
    @GetMapping("/list")
    public List<Tenant> list() {
        Page page = new Page(1, 3);
        return tenantMapper.selectPage(page, null).getRecords();

    }
}
