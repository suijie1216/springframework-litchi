package org.springframework.litchi.module.exception;

/**
 * @author suijie
 */
public interface ApiErrors {

    /**
     * 异常码
     */
    String getErrorCode();

    /**
     * 异常描述
     */
    String getErrorMessage();
}
