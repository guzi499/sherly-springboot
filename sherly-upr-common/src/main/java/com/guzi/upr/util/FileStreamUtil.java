package com.guzi.upr.util;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
        ByteOutputStream byteOutputStream = new ByteOutputStream(bytes.length);
        IOUtils.write(bytes, byteOutputStream);
        return new BufferedReader(new InputStreamReader(byteOutputStream.newInputStream()));
    }
}
