package org.springframework.litchi.common.cache;

import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;
import com.googlecode.concurrentlinkedhashmap.Weighers;
import java.util.Set;

/**
 * @author: suijie
 * @date: 2018/5/26 22:24
 * @description:
 */
public class ObjectCache<K, V>{

    /**
     * 缓存容器
     */
    private ConcurrentLinkedHashMap<K, V> cache;

    public ObjectCache(int size){
        ConcurrentLinkedHashMap.Builder<K, V> builder = new ConcurrentLinkedHashMap.Builder<>();
        builder.initialCapacity(size / 4);
        builder.maximumWeightedCapacity(size);
        builder.weigher(Weighers.singleton());
        cache = builder.build();
    }

    /**
     * 缓存关键字合集
     * @return
     */
    public Set<K> keys(){
        return cache.keySet();
    }

    /**
     * 缓存获取数据
     * @param key
     * @return
     */
    public V get(K key){
        return cache.get(key);
    }

    /**
     * 数据存入缓存
     * @param key
     * @param value
     */
    public void put(K key, V value){
        cache.put(key, value);
    }

    /**
     * 刷新缓存
     */
    public void refresh(){
        cache.clear();
    }

}
