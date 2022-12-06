package com.guzi.sherly.quartz;

import com.guzi.sherly.modules.quartz.model.SherlyJob;

/**
 * @author 谷子毅
 * @date 2022/12/6
 */
public class TestJob extends SherlyJob {

    public void test() {
        System.out.println("芜湖，我是一个测试定时任务！");
    }

}
