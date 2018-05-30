package org.springframework.litchi.common.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.litchi.qps.QPSCounterWorker;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @author: suijie
 * @date: 2018/5/30 19:21
 * @description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={BeanConfig.class})
public class QPSCounterWorkerTest {

    @Resource
    private QPSCounterWorker qpsCounterWorker;

    @Test
    public void testQPSCounterWorker() throws InterruptedException {
        for(int i = 0; i < 100; i ++){
            qpsCounterWorker.increment("/test1", 11);
            qpsCounterWorker.increment("/test1", 12);
            qpsCounterWorker.increment("/test1", 13);
            qpsCounterWorker.increment("/test1", 14);
            if( i % 3 == 0){
                Thread.sleep(1001L);
            }
        }
    }

}
