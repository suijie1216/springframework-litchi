package org.springframework.litchi.module.response;

/**
 * @author suijie
 */
public enum StatusCode implements Status {

    /**
     * 枚举值
     */
    SERVICE_RUN_SUCCESS(10000, "服务运行成功"),

    PARAM_IS_EMPTY(20001, "参数%s不能为空"),

    DATA_NOT_EXIST(20002, "%s不存在"),

    JSON_FORMAT_ERROR(30001, "JSON格式不正确"),

    HTTP_ACESS_ERROR(40001, "HTTP访问异常"),

    DATA_FORMAT_ERROR(50005, "数据格式化异常"),

    SERVICE_RUN_ERROR(99999, "服务器异常,请稍后再试");

    private int status;
    private String msg;

    StatusCode(int status, String message) {
        this.status = status;
        this.msg = message;
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public String getMsg() {
        return msg;
    }

}