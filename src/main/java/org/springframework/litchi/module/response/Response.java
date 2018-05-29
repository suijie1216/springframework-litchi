package org.springframework.litchi.module.response;

import lombok.Data;

/**
 * @author: suijie
 * @date: 2018/5/24 15:32
 * @description:
 */
@Data
public class Response<T> implements Status {

    /**
     * 状态值
     */
    private int status;
    /**
     * 状态码
     */
    private String code;
    /**
     * 状态描述
     */
    private String msg;
    /**
     * 业务数据
     */
    private T data;

    public Response() {
    }

    public Response(int status, String code, String msg) {
        this.status = status;
        this.code = code;
        this.msg = msg;
    }

    public Response(Status status) {
        this(status.getStatus(), status.getCode(), status.getMsg());
    }

    /**
     * 参数不能为空的返回
     * @param param
     * @return
     */
    public static Response paramIsEmptyResponse(String param) {
        Response response = new Response();
        response.setStatus(StatusCode.PARAM_IS_EMPTY.getStatus());
        response.setCode(StatusCode.PARAM_IS_EMPTY.getCode());
        response.setMsg(String.format(StatusCode.PARAM_IS_EMPTY.getMsg(), param));
        return response;
    }

    /**
     * 数据不存在的返回
     * @param data
     * @return
     */
    public static Response dataNotExitResponse(String data) {
        Response response = new Response();
        response.setStatus(StatusCode.DATA_NOT_EXIST.getStatus());
        response.setCode(StatusCode.DATA_NOT_EXIST.getCode());
        response.setMsg(String.format(StatusCode.DATA_NOT_EXIST.getMsg(), data));
        return response;
    }

    /**
     * 默认正确返回
     * @param data
     * @param <R>
     * @return
     */
    public static <R> Response<R> successResponse(R data) {
        Response response = new Response(StatusCode.SERVICE_RUN_SUCCESS);
        response.setData(data);
        return response;
    }

    /**
     * 默认异常返回
     * @return
     */
    public static  Response failureResponse(){
        return new Response(StatusCode.SERVICE_RUN_ERROR);
    }


    /**
     * 是否成功的返回值
     * @return
     */
    public boolean isSuccess() {
        return status == 10000;
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

}
