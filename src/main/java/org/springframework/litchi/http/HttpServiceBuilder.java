package org.springframework.litchi.http;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.core.env.Environment;
import retrofit2.Retrofit;

import javax.annotation.Resource;

/**
 * @author: suijie
 * @date: 2018/6/9 11:18
 * @description:
 */
public class HttpServiceBuilder<T> implements FactoryBean<T> {
    private final static Logger logger = LoggerFactory.getLogger(HttpServiceBuilder.class);

    private Class<T> targetClass;

    @Resource
    private Environment environment;

    public HttpServiceBuilder(Class<T> targetClass) {
        this.targetClass = targetClass;
    }

    @Override
    public Class<?> getObjectType() {
        return targetClass;
    }

    @Override
    public T getObject() {
        //初始化http客户端
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(message -> {
            if (logger.isDebugEnabled()) {
                logger.debug(message);
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();
        //初始化retrofit builder
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.client(httpClient);
        builder.addCallAdapterFactory(new HttpCallAdapterFactory());
        Host host = targetClass.getAnnotation(Host.class);
        builder.addConverterFactory(new HttpConvertFactory(host.charset(), host.snake()));
        //获取http请求地址
        String hostCofig = host.value();
        String url = null;
        if(environment != null) {
            url = environment.getProperty(hostCofig);
        }
        if (StringUtils.isEmpty(url)) {
            //以http开头
            if(!hostCofig.startsWith("http")) {
                throw new IllegalArgumentException("config:" + hostCofig + " is not define");
            }
            url = hostCofig;
        }
        builder.baseUrl(url);
        //构建retrofit
        Retrofit retrofit = builder.build();
        return retrofit.create(targetClass);
    }

}
