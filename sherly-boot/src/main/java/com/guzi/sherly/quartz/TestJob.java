package com.guzi.sherly.quartz;

import com.guzi.sherly.modules.quartz.util.ScheduleTaskUtil;

/**
 * @author 谷子毅
 * @date 2022/12/6
 */
public class TestJob {

    public void test() {
        ScheduleTaskUtil.log("开始打印简单测试");
        ScheduleTaskUtil.log("芜湖，我是一个简单测试打印");
        ScheduleTaskUtil.log("结束打印简单测试");
    }

    public void testWithParams(String s, Boolean b, Double d, Long l, Integer i) {
        ScheduleTaskUtil.log("开始打印参数");
        ScheduleTaskUtil.log("String:" + s);
        ScheduleTaskUtil.log("Boolean:" + b);
        ScheduleTaskUtil.log("Double:" + d);
        ScheduleTaskUtil.log("Long:" + l);
        ScheduleTaskUtil.log("Integer:" + i);
        ScheduleTaskUtil.log("结束打印参数");
    }

    public void testWithError() {
        ScheduleTaskUtil.log("准备产生错误");
        int a = 1/0;
        ScheduleTaskUtil.log("结束产生错误");
    }

}
