package org.springframework.litchi.http;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author: suijie
 * @date: 2018/6/9 11:16
 * @description:
 */
@Target(ElementType.TYPE)
@Retention(RUNTIME)
public @interface Host {
    /**
     * 请求地址
     * @return
     */
    String value();

    /**
     * 参数格式
     * @return
     */
    boolean snake() default true;

    /**
     * 编码
     * @return
     */
    String charset() default "utf-8";
}
