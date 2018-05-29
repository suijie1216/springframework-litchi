package org.springframework.litchi.qps;

import com.google.common.cache.LoadingCache;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author: suijie
 * @date: 2018/5/29 19:29
 * @description:
 */
public class QPSCounter {

    /**
     * url路径
     */
    private String path;

    /**
     * key:秒值
     * value:qps
     */
    private LoadingCache<Long, Long> qps;

    private LinkedBlockingQueue<Node> queue;

    public QPSCounter(String path){

    }

    class Node {
        private AtomicInteger qps = new AtomicInteger(0);
        private AtomicLong rt = new AtomicLong(0);
        private Long time;
        private String name = QPSCounter.this.path;

        public Node(Long time) {
            this.time = time;
        }

        public int getQps() {
            return qps.get();
        }

        public long getRt() {
            return rt.get();
        }

        public Long getTime() {
            return time;
        }

        public String getName() {
            return name;
        }
    }

    public void increment(String name, long rt) {

    }
}
