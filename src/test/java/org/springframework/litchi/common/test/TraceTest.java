package org.springframework.litchi.common.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.litchi.profile.qps.QPSCounterWorker;
import org.springframework.litchi.profile.trace.Trace;
import org.springframework.litchi.profile.trace.TracePoint;
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
@EnableAspectJAutoProxy
@ComponentScans({@ComponentScan("org.springframework.litchi")})
public class TraceTest {

    private String rootMethod = "root method1";

    @Resource
    private QPSCounterWorker qpsCounterWorker;
    
    @Test
    public void trace() throws InterruptedException {
        for(int i = 0; i < 2; i++){
            Trace.enter(rootMethod);
            Thread.sleep(19);
            Trace.enter("method11");
            Thread.sleep(209);
            Trace.release("method11");

            Trace.enter("method12");
            Thread.sleep(402);
            Trace.release("method12");
            Trace.enter("method13");
            Thread.sleep(53);
            Trace.release("method13");

            Trace.enter("method14");
            Thread.sleep(19);
            Trace.enter("method141");
            Thread.sleep(209);
            Trace.release("method141");

            Trace.enter("method142");
            Thread.sleep(402);
            Trace.release("method142");
            Trace.enter("method143");
            Thread.sleep(53);
            Trace.release("method143");

            Trace.release("method14");
            Trace.release(rootMethod);
            long rt = Trace.getCost(rootMethod);
            qpsCounterWorker.increment(rootMethod, rt);
            if(rt > 100){
                System.out.println(Trace.end());
            }
        }
    }
}
