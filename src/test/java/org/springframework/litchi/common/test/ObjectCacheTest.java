package org.springframework.litchi.common.test;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.litchi.common.cache.ObjectCache;

/**
 * @author: suijie
 * @date: 2018/5/26 23:08
 * @description:
 */
public class ObjectCacheTest {

    @Test
    public void cacheTest(){
        ObjectCache<String, Object> cache = new ObjectCache<>(2);
        cache.put("name", "suijie");
        cache.put("age", "30");
        System.out.println(cache.get("name"));
        cache.put("mvp", "kobe");
        Assert.assertEquals(cache.keys().size(), 2);
        cache.refresh();
        Assert.assertEquals(cache.keys().size(), 0);
    }
}
