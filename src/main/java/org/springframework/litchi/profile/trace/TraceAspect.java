package org.springframework.litchi.profile.trace;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author: suijie
 * @date: 2018/5/27 11:24
 * @description:
 */
@Aspect
@Component
public class TraceAspect {

    private static final Logger logger = LoggerFactory.getLogger("trace");

    @Pointcut("@annotation(org.springframework.litchi.profile.trace.TracePoint)")
    public void tracePoint() {

    }

    @Before("tracePoint()")
    public void doBefore(JoinPoint joinPoint) {
        String point = this.getSignature(joinPoint);
        if (point == null) {
            return;
        }
        Trace.traceIn(point);
    }

    @After("tracePoint()")
    public void doAfter(JoinPoint joinPoint) {
        String point = this.getSignature(joinPoint);
        if (point == null) {
            return;
        }
        Trace.traceOut(point);
        //如果追踪结束并且调用总时间大于阈值则打印trace
        if(Trace.traceEnd()){
            if(autoPrintTrace(joinPoint) && Trace.getCost(point) > this.getThreshold(joinPoint)){
                logger.info(Trace.traceInfo());
            }
        }
    }

    @AfterThrowing("tracePoint()")
    public void doAfterThrowing() {
        Trace.reset();
    }

    /**
     * 获取MethodSignature，样例：classname.methodname
     * @param joinPoint
     * @return
     */
    private String getSignature(JoinPoint joinPoint) {
        MethodSignature sign = (MethodSignature) joinPoint.getSignature();
        Method method = sign.getMethod();
        TracePoint tracePoint = method.getAnnotation(TracePoint.class);
        String signature = null;
        if (tracePoint != null) {
            signature = tracePoint.value();
        }
        if (StringUtils.isEmpty(signature)) {
            signature = method.getDeclaringClass().getSimpleName() + "." + method.getName();
        }
        return signature;
    }

    /**
     * 获取需要打印trace的阈值
     * @param joinPoint
     * @return
     */
    private long getThreshold(JoinPoint joinPoint){
        MethodSignature sign = (MethodSignature) joinPoint.getSignature();
        Method method = sign.getMethod();
        TracePoint tracePoint = method.getAnnotation(TracePoint.class);
        long threshold = 0L;
        if (tracePoint != null) {
            threshold = tracePoint.threshold();
        }
        return threshold;
    }

    /**
     * 是否自动打印trace
     * @param joinPoint
     * @return
     */
    private boolean autoPrintTrace(JoinPoint joinPoint){
        MethodSignature sign = (MethodSignature) joinPoint.getSignature();
        Method method = sign.getMethod();
        TracePoint tracePoint = method.getAnnotation(TracePoint.class);
        boolean isPrintTrace = false;
        if (tracePoint != null) {
            isPrintTrace = tracePoint.print();
        }
        return isPrintTrace;
    }

}
