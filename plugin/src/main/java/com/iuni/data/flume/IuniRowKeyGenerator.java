package com.iuni.data.flume;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.flume.sink.hbase.SimpleRowKeyGenerator;

public class IuniRowKeyGenerator extends SimpleRowKeyGenerator {

    public static AtomicLong atl = new AtomicLong();

    /**
     * 先将当前时间戳转换为16进制，再将其反转，加上输入参数，加上自增数值
     *
     * @param timestamp
     * @return
     */
    public static byte[] getReverseTimestampKey(String timestamp) {
        return new StringBuilder(
                Long.toHexString(System.nanoTime())).reverse()
                .append("-").append(timestamp).append("-")
                .append(String.valueOf(atl.incrementAndGet())).toString().getBytes();
    }

    public static byte[] getUUIDKey() {
        return UUID.randomUUID().toString().getBytes();
    }

}
