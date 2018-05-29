package org.springframework.litchi.common.cache;

import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;
import com.googlecode.concurrentlinkedhashmap.Weighers;
import lombok.Data;

/**
 * @author: suijie
 * @date: 2018/5/26 23:14
 * @description:
 */
public class LocalCache<K, V> {

    /**
     * 缓存失效时长毫秒值
     */
    private long expire;
    /**
     * 缓存容器
     */
    private ConcurrentLinkedHashMap<K, Node<V>> cache;

    public LocalCache(int size, long expire){
        ConcurrentLinkedHashMap.Builder<K,  Node<V>> builder = new ConcurrentLinkedHashMap.Builder<>();
        builder.initialCapacity(size / 4);
        builder.maximumWeightedCapacity(size);
        builder.weigher(Weighers.singleton());
        cache = builder.build();
        this.expire = expire;
    }

    /**
     * 获取缓存
     * @param key
     * @return
     */
    public V get(K key){
        V value = null;
        Node<V> node = cache.get(key);
        if(node != null){
            long downTime = node.getDownTime();
            if(downTime >= System.currentTimeMillis()){
                value = node.getValue();
            }else{
                cache.remove(key);
            }
        }
        return value;
    }

    /**
     * 数据存入缓存
     * @param key
     * @param value
     */
    public void put(K key, V value){
        Node<V> node = new Node<>();
        node.setValue(value);
        node.setDownTime(System.currentTimeMillis() + expire);
        cache.put(key, node);
    }

    /**
     * 刷新缓存
     */
    public void refresh(){
        cache.clear();
    }

    /**
     * 缓存节点对象
     * @param <T>
     */
    @Data
    private static class Node<T>{
        /**
         * 失效时间点
         */
        private long downTime;
        /**
         * 缓存值
         */
        private T value;
    }
}
