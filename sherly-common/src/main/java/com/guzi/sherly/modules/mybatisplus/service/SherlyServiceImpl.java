package com.guzi.sherly.modules.mybatisplus.service;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author 谷子毅
 * @date 2022/10/20
 */
public class SherlyServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {

    /**
     * 查询（根据ID 批量查询，如果ID 为空返回空列表）
     *
     * @param idList 主键ID列表
     */
    @Override
    public List<T> listByIds(Collection<? extends Serializable> idList) {
        if (CollUtil.isEmpty(idList)) {
            return Collections.emptyList();
        }
        return getBaseMapper().selectBatchIds(idList);
    }
}
