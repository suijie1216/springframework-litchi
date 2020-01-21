package org.springframework.litchi.module.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 业务异常
 * 由于业务原因中断流程的异常。
 * 若是消息链路无需重试，在上层入口统一打印日志
 * 若是RPC链路提供的服务，一般需要转成异常码透出给上游
 * @author suijie
 * @date 2019/12/6
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BusinessException extends RuntimeException {

    private String errorCode;
    private String errorMessage;

    public BusinessException(String errorCode, String errorMessage) {
        super(errorCode + ":" + errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public BusinessException(ApiErrors errors) {
        this(errors.getErrorCode(), errors.getErrorMessage());
    }

    public BusinessException(ApiErrors errors, Object... args) {
        this(errors.getErrorCode(), String.format(errors.getErrorMessage(), args));
    }
}
