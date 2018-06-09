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
    String value();
    boolean snake() default true;
    String charset() default "utf-8";
}
