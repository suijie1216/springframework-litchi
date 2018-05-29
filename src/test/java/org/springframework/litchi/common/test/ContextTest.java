package org.springframework.litchi.common.test;

import org.junit.Test;
import org.springframework.litchi.common.even.ThreadContext;

/**
 * @author: suijie
 * @date: 2018/5/26 20:12
 * @description:
 */
public class ContextTest {
    @Test
    public void contextTest() {
        ThreadContext.iniContext();
        ThreadContext.put("name", "sunwukong");
        ThreadContext.put("bigData", new byte[1024*1024*2]);
        ThreadContext.put("bigData1", new byte[1024*1024*2]);
        ThreadContext.get("name");
        ThreadContext.get("bigData");
        ThreadContext.get("bigData1");
        System.out.println(ThreadContext.size());
    }
}
