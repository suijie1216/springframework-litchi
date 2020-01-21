package org.springframework.litchi.profile.trace;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
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
 * @author suijie
 */
@Aspect
@Component
public class TraceAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger("trace");

    @Pointcut("@annotation(org.springframework.litchi.profile.trace.TracePoint)")
    public void tracePoint() {

    }

    @Before("tracePoint()")
    public void doBefore(JoinPoint joinPoint){
        String point = getPoint(joinPoint);
        if (point == null) {
            return;
        }
        Trace.enter(point);
    }

    @AfterReturning("tracePoint()")
    public void doAfter(JoinPoint joinPoint) {
        try{
            String point = getPoint(joinPoint);
            if (point == null) {
                return;
            }
            Trace.release(point);
            if(Trace.isEnd()){
                LOGGER.warn(Trace.end());
            }
        }catch (Exception e){
            LOGGER.error("trace after error", e);
        }finally {
            Trace.reset();
        }

    }

    @AfterThrowing("tracePoint()")
    public void doAfterThrowing() {
        Trace.reset();
    }

    private String getPoint(JoinPoint joinPoint) {
        MethodSignature sign = (MethodSignature) joinPoint.getSignature();
        Method method = sign.getMethod();
        TracePoint tracePoint = method.getAnnotation(TracePoint.class);
        String point = null;
        if (tracePoint != null) {
            point = tracePoint.value();
        }
        if (point == null || "".equals(point)) {
            point = method.getDeclaringClass().getSimpleName() + "." + method.getName();
        }
        return point;
    }

}
