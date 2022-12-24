package com.guzi.sherly.quartz;

import com.guzi.sherly.modules.quartz.util.ScheduleTaskUtil;

/**
 * @author 谷子毅
 * @date 2022/12/6
 */
public class TestJob {

    public void test() {
        ScheduleTaskUtil.log("gagagaga666");
        System.out.println("芜湖，我是一个测试定时任务！");
        ScheduleTaskUtil.log("gagagaga999");
    }

}
