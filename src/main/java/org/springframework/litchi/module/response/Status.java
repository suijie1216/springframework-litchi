package org.springframework.litchi.module.response;

/**
 * @author suijie
 */
public interface Status {
    /**
     * 状态码值
     * @return 状态码值
     */
    int getStatus();

    /**
     * 错误码
     * @return 错误码
     */
    String getCode();

    /**
     * 状态描述
     * @return 状态描述
     */
    String getMsg();

}
