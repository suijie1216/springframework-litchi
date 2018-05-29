package org.springframework.litchi.module.exception;

/**
 * @author: suijie
 * @date: 2018/5/24 16:52
 * @description:
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String errorMessage){
        super(errorMessage);
    }

}
