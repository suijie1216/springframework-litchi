package org.springframework.litchi.http;

import retrofit2.CallAdapter;
import retrofit2.Retrofit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * @author: suijie
 * @date: 2018/6/9 11:22
 * @description:
 */
public class HttpCallAdapterFactory extends CallAdapter.Factory {
    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        return new HttpCallAdapter(returnType);
    }
}
