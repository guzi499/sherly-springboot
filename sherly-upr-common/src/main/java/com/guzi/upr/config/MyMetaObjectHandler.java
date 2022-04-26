package com.guzi.upr.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * mybatis-plus字段自动填充
 *
 * @author 谷子毅
 * @date 2022/4/2
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        //ThreadLocalModel threadLocalModel = ThreadLocalUtil.get();
        this.strictInsertFill(metaObject, "createTime", Date::new, Date.class);
        //this.strictInsertFill(metaObject, "createUserId", Long.class, threadLocalModel.getUserId());
    }


    @Override
    public void updateFill(MetaObject metaObject) {
        //ThreadLocalModel threadLocalModel = ThreadLocalUtil.get();
        this.strictUpdateFill(metaObject, "updateTime", Date::new, Date.class);
        //this.strictUpdateFill(metaObject, "updateUserId", Long.class, threadLocalModel.getUserId());
    }
}
