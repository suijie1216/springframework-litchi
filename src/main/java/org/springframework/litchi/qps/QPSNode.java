package org.springframework.litchi.qps;

import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author: suijie
 * @date: 2018/5/30 15:46
 * @description:
 */
@Data
public class QPSNode {
    /**
     * 接口地址
     */
    private String path;
    /**
     * 时间秒值
     */
    private Long time;
    /**
     * qps计数器
     */
    private AtomicInteger qps = new AtomicInteger(0);
    /**
     * rt总时间
     */
    private AtomicLong rt = new AtomicLong(0);

    public QPSNode(String path, Long time) {
        this.path = path;
        this.time = time;
    }
}
