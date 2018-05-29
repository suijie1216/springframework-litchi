package org.springframework.litchi.trace;

import org.springframework.litchi.common.util.HttpUtils;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: suijie
 * @date: 2018/5/27 10:34
 * @description:
 */
public class TraceIdGenerator {
    private static AtomicInteger count = new AtomicInteger(0);
    private static final String IP_HEX = getIpHex();

    private static ThreadLocal<Random> random = new ThreadLocal<Random>() {
        @Override
        protected Random initialValue() {
            return new Random();
        }
    };
    /**
     * 全局原子计数器
     */
    private static int getCount() {
        int current;
        int next;
        do {
            current = count.get();
            next = current >= 0xFFF ? 0x0 : current + 1;
        } while (!count.compareAndSet(current, next));
        return next;
    }

    private static String getCountHex() {
        String s = Integer.toHexString(getCount());
        return fillHexString(s,3);
    }
    /**
     * 随机防碰撞
     */
    private static String getRandomIndex() {
        String s = Integer.toHexString(random.get().nextInt(0xFFF));
        return fillHexString(s,3);
    }

    private static String fillHexString(String s, int len) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len - s.length(); i++) {
            sb.append(0);
        }
        sb.append(s);
        return sb.toString();
    }

    /**
     * 获取ip地址的16进制
     * @return
     */
    private static String getIpHex() {
        String ip = HttpUtils.getLocalIp();
        String[] split = ip.split("\\.");
        StringBuilder sb = new StringBuilder();
        for (String num : split) {
            String s = Integer.toHexString(Integer.valueOf(num));
            if (s.length() == 1) {
                sb.append(0);
            }
            sb.append(s);
        }
        return sb.toString();
    }

    public static String generate() {
        StringBuilder traceId = new StringBuilder();
        String time = Long.toHexString(System.currentTimeMillis());
        String countHex = getCountHex();
        String randomIndex = getRandomIndex();
        traceId.append(IP_HEX).append(time).append(countHex).append(randomIndex);
        return traceId.toString();
    }
}
