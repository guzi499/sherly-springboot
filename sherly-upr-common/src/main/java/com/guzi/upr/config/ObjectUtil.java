package com.guzi.upr.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author fudon
 * @version 1.0
 * @date 2022/3/25 10:16
 */
@Deprecated
public class ObjectUtil {
    /**
     * CPU 核心数量
     */
    public static int CPU_CORE = 4;
    public static ConcurrentLinkedQueue<ObjectMapper> ObjectMapperQueue = null;
    public static List<ObjectMapper> containers = null;

    static {
        CPU_CORE = Runtime.getRuntime().availableProcessors();
        containers = new ArrayList<>(CPU_CORE);
        initObjectMapperPool();
    }


    private static void initObjectMapperPool() {

        for (int i = 0; i < CPU_CORE; i++) {
            containers.add(new ObjectMapper());
        }
        ObjectMapperQueue = new ConcurrentLinkedQueue<>(containers);
    }

    public synchronized static ObjectMapper getObjectMapper() {
        ObjectMapper poll = ObjectMapperQueue.poll();
        ObjectMapperQueue.offer(poll);
        return poll;
    }


}
