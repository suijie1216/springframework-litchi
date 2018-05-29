package org.springframework.litchi.common.test;

import org.junit.Test;
import org.springframework.litchi.trace.Trace;

/**
 * @author: suijie
 * @date: 2018/5/27 17:38
 * @description:
 */
public class TraceTest {

    @Test
    public void trace() throws InterruptedException {
        Trace.traceIn("method1");
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
        Trace.traceOut("method1");
        System.out.println(Trace.printTrace());
    }
}
