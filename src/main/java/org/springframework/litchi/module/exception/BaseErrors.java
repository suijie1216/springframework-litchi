package org.springframework.litchi.module.exception;

/**
 * @author suijie
 */
public enum BaseErrors implements ApiErrors {
    /**
     * 枚举值
     */
    SUCCESS(10000, "SUCCESS", ""),
    PARAM_ILLEGAL(10001, "PARAM_ILLEGAL", "参数非法:%s"),
    PARAM_EMPTY(10002, "PARAM_EMPTY", "参数%s不能为空"),
    DATA_NOT_EXIST(10003, "DATA_NOT_EXIST", "%s不存在"),

    DB_ERROR(80000, "DB_ERROR", "数据库异常:%s"),
    SYSTEM_ERROR(99999, "SYSTEM_ERROR", "服务器忙,请稍后再试");

    private int code;
    private String errorCode;
    private String errorMessage;

    BaseErrors(int code, String errorCode, String errorMessage) {
        this.code = code;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

    public int getCode() {
        return code;
    }
}
