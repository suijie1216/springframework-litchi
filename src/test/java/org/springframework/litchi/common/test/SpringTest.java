package org.springframework.litchi.common.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.litchi.common.cache.LocalCache;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @author: suijie
 * @date: 2018/5/27 15:04
 * @description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={TestConfig.class})
public class SpringTest {

    @Resource
    private LocalCache<String, Object> localCache;

    @Test
    public void test(){
        localCache.put("name", "kobe");
        System.out.println(localCache.get("name"));
    }
}
