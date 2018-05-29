package org.springframework.litchi.common.test;

import org.junit.Test;
import org.springframework.litchi.trace.TraceIdGenerator;

/**
 * @author: suijie
 * @date: 2018/5/27 10:35
 * @description:
 */
public class TraceIdGeneratorTest {

    @Test
    public void traceTest(){
        System.out.println(TraceIdGenerator.generate());
    }
}
