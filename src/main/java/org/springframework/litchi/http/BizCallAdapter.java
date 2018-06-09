package org.springframework.litchi.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.CallAdapter;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @author: suijie
 * @date: 2018/6/9 11:22
 * @description:
 */
public class BizCallAdapter implements CallAdapter<Object, Object> {
    private Type responseType;
    private final static Logger logger = LoggerFactory.getLogger(BizCallAdapter.class);

    public BizCallAdapter(Type responseType) {
        this.responseType = responseType;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public Object adapt(Call<Object> call) {
        try {
            return call.execute().body();
        } catch (IOException e) {
            logger.error("request error", e);
            throw new RuntimeException("request error", e);
        }
    }
}
