package org.springframework.litchi.common.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.litchi.profile.qps.QPSCounterWorker;
import org.springframework.litchi.profile.trace.Trace;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @author: suijie
 * @date: 2018/5/27 17:38
 * @description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={BeanConfig.class})
public class TraceTest {

    private String rootMethod = "root method1";

    @Resource
    private QPSCounterWorker qpsCounterWorker;

    @Test
    public void trace() throws InterruptedException {
        for(int i = 0; i < 2; i++){
            Trace.traceIn(rootMethod);
            Thread.sleep(19);
            Trace.traceIn("method11");
            Thread.sleep(209);
            Trace.traceOut("method11");

            Trace.traceIn("method12");
            Thread.sleep(402);
            Trace.traceOut("method12");
            Trace.traceIn("method13");
            Thread.sleep(53);
            Trace.traceOut("method13");

            Trace.traceIn("method14");
            Thread.sleep(19);
            Trace.traceIn("method141");
            Thread.sleep(209);
            Trace.traceOut("method141");

            Trace.traceIn("method142");
            Thread.sleep(402);
            Trace.traceOut("method142");
            Trace.traceIn("method143");
            Thread.sleep(53);
            Trace.traceOut("method143");

            Trace.traceOut("method14");
            Trace.traceOut(rootMethod);
            /*long rt = Trace.getCost(rootMethod);
            qpsCounterWorker.increment(rootMethod, rt);
            if(rt > 100){
                System.out.println(Trace.traceInfo());
            }*/
        }
    }
}
