package org.springframework.litchi.profile.qps;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author suijie
 * 每个实例对应一个url的qps计数器
 */
public class QPSCounter {
    /**
     * key:秒值
     * value:qps
     */
    private LoadingCache<Long, QPSNode> realCounter;
    /**
     * 秒值节点队列
     */
    private LinkedBlockingQueue<QPSNode> queue;

    public QPSCounter(String path, LinkedBlockingQueue<QPSNode> queue){
        this.queue = queue;
        CacheBuilder cacheBuilder = CacheBuilder.newBuilder();
        cacheBuilder.expireAfterAccess(2, TimeUnit.SECONDS);
        cacheBuilder.removalListener(
            (RemovalListener<Long, QPSNode>)removalNotification -> QPSCounter.this.queue.offer(removalNotification.getValue()));
        cacheBuilder.maximumSize(3);
        realCounter = cacheBuilder.build(new CacheLoader<Long, QPSNode>() {
            @Override
            public QPSNode load(Long time){
                return new QPSNode(path, time);
            }
        });
    }

    /**
     * 记录qps并统计响应时间
     */
    public void incrQPS(Long rt) {
        try {
            QPSNode node = realCounter.get(getTime());
            node.getQps().incrementAndGet();
            node.getRt().addAndGet(rt);
        } catch (ExecutionException e) {

        }
    }

    /**
     * 获取当前时间秒值
     */
    private long getTime() {
        return System.currentTimeMillis() / 1000 * 1000;
    }

}
