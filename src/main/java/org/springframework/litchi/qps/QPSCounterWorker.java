package org.springframework.litchi.qps;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author: suijie
 * @date: 2018/5/30 18:18
 * @description:
 */
public class QPSCounterWorker {
    /**
     * 计数节点队列
     */
    private LinkedBlockingQueue<QPSNode> queue;
    /**
     * qps打印线程
     */
    private Thread printThread;
    /**
     * 计数器
     */
    private LoadingCache<String, QPSCounter> counters;
    /**
     * 打印任务是否暂停标记
     */
    private Boolean stop;
    /**
     * qps监听器
     */
    private QPSListener listener;

    @PostConstruct
    public void ini(){
        //初始化暂停标记
        stop = false;
        //初始化节点队列
        queue = new LinkedBlockingQueue<>();
        //初始化计数器
        CacheBuilder cacheBuilder = CacheBuilder.newBuilder();
        cacheBuilder.expireAfterAccess(1, TimeUnit.MINUTES);
        cacheBuilder.maximumSize(1000L);
        counters = cacheBuilder.build(new CacheLoader<String, QPSCounter>() {
            @Override
            public QPSCounter load(String name){
                return new QPSCounter(name, queue);
            }
        });
        //初始化qps监听器
        if(listener == null){
            listener = new QPSListener() {
                @Override
                public void event(QPSNode node) {
                    //默认qps监听器什么都不做
                }
            };
        }
        //初始化打印线程
        printThread = new Thread(new PrintJob());
        printThread.setDaemon(true);
        printThread.start();
    }

    @PreDestroy
    public void destory() {
        printThread.interrupt();
        stop = true;
    }

    public QPSCounterWorker(){

    }

    public QPSCounterWorker(QPSListener listener){
        this.listener = listener;
    }
    /**
     * qps计数
     * @param path
     * @param rt
     */
    public void increment(String path, long rt) {
        try {
            QPSCounter realCounter = counters.get(path);
            realCounter.incrQPS(rt);
        } catch (ExecutionException e) {
        }
    }

    private class PrintJob implements Runnable {
        @Override
        public void run() {
            while (!stop) {
                try {
                    QPSNode node = queue.take();
                    QPSCounterWorker.this.listener.event(node);
                } catch (InterruptedException e) {

                }
            }
        }
    }
}
