package org.springframework.litchi.http;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import retrofit2.Retrofit;

/**
 * @author: suijie
 * @date: 2018/6/9 11:18
 * @description:
 */
public class HttpServiceBuilder<T> implements FactoryBean<T>, InitializingBean {
    private final static Logger logger = LoggerFactory.getLogger(HttpServiceBuilder.class);

    private Class<T> targetClass;
    private T object;
    private static OkHttpClient httpClient;

    static {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(message -> {
            if (logger.isDebugEnabled()) {
                logger.debug(message);
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        httpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();
    }

    @Autowired
    private Environment environment;

    public HttpServiceBuilder(Class<T> targetClass) {
        this.targetClass = targetClass;
    }

    @Override
    public T getObject() throws Exception {
        return object;
    }

    @Override
    public Class<?> getObjectType() {
        return targetClass;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (targetClass == null) {
            throw new NullPointerException("class is null");
        }
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.client(httpClient);
        Host host = targetClass.getAnnotation(Host.class);
        HttpConvertFactory httpConvertFactory = new HttpConvertFactory(host.charset(),host.snake());
        builder.addConverterFactory(httpConvertFactory);
        builder.addCallAdapterFactory(new BizCallAdapterFactory());


        if (host != null) {
            String value = host.value();
            String url=null;
            if(environment!=null) {
                url= environment.getProperty(value);
            }
            //spring没配置url
            if (StringUtils.isEmpty(url)) {
                //以http开头
                if(value.startsWith("http")==false) {
                    throw new IllegalArgumentException("config:" + value + " is not define");
                }else{
                    builder.baseUrl(value);
                }
            }else {
                builder.baseUrl(url);
            }
        }else{
            throw new NullPointerException("host is not define");
        }
        Retrofit build = builder.build();
        object = build.create(targetClass);
    }

}
