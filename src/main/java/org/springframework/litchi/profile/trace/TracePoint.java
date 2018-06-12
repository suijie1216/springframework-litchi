package org.springframework.litchi.profile.trace;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: suijie
 * @date: 2018/5/27 11:27
 * @description:
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface TracePoint {

    /**
     * 自定义方法名
     * @return
     */
    String value() default "";

    /**
     * 调用时间超过阈值则打印trace
     * @return
     */
    long threshold() default 200L;

}
