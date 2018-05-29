package org.springframework.litchi.common.test;

import org.junit.Test;
import org.springframework.litchi.common.util.HttpUtils;

/**
 * @author: suijie
 * @date: 2018/5/27 10:12
 * @description:
 */
public class HttpUtilsTest {

    @Test
    public void testIp(){
        System.out.println(HttpUtils.getLocalIp());
    }
}
