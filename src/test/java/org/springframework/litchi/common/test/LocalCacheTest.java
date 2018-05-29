package org.springframework.litchi.common.test;

import org.junit.Test;
import org.springframework.litchi.common.cache.LocalCache;

/**
 * @author: suijie
 * @date: 2018/5/26 23:57
 * @description:
 */
public class LocalCacheTest {

    @Test
    public void putTest() throws Exception {
        LocalCache<String, Object> cache = new LocalCache<>(2, 1000L);
        cache.put("name", "lily");
        cache.put("age", 20);
        cache.put("mvp", "kobe");
        System.out.println(cache.get("name"));
        System.out.println(cache.get("mvp"));
        cache.refresh();
        System.out.println(cache.get("mvp"));
        Thread.sleep(1100L);
        System.out.println(cache.get("mvp"));
    }

}
