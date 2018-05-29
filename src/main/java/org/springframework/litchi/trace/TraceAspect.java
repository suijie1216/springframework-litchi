package org.springframework.litchi.trace;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
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

    @Pointcut("@annotation(org.springframework.litchi.trace.TracePoint)")
    public void tracePoint() {

    }

    @Before("tracePoint()")
    public void doBefore(JoinPoint joinPoint) {
        String point = getSignature(joinPoint);
        if (point == null) {
            return;
        }
        Trace.traceIn(point);
    }

    @After("tracePoint()")
    public void doAfter(JoinPoint joinPoint) {
        String point = getSignature(joinPoint);
        if (point == null) {
            return;
        }
        Trace.traceOut(point);
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
}
