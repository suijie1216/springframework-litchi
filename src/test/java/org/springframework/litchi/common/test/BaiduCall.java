package org.springframework.litchi.common.test;

import org.springframework.litchi.http.Host;
import org.springframework.litchi.module.response.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author: suijie
 * @date: 2018/6/9 16:36
 * @description:
 */
@Host("http://10.95.121.194:8002/")
public interface BaiduCall {

    @GET("order/get")
    Response test(@Query("order_id") Long id);
}
