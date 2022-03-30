package com.guzi.upr.util;

import org.apache.commons.io.IOUtils;

import java.io.*;

/**
 * @author fudon
 * @version 1.0
 * @date 2022/3/28 22:33
 */
public class FileStreamUtil {
    /**
     * @param bytes 字节
     * @return BufferedReader Read流
     * @author 付东辉
     * @date 2022/3/28 22:34
     * 传入字节数组获取inputStream
     */
    public static BufferedReader getBufferedReader(byte[] bytes) throws IOException {
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream(bytes.length);
        IOUtils.write(bytes, byteOutputStream);
        return new BufferedReader(new InputStreamReader(new ByteArrayInputStream(byteOutputStream.toByteArray())));
    }
}
