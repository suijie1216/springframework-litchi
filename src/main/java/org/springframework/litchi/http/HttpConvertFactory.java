package org.springframework.litchi.http;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;

/**
 * @author: suijie
 * @date: 2018/6/9 11:21
 * @description:
 */
public class HttpConvertFactory extends Converter.Factory {

    private String charset;

    private JacksonConverterFactory jacksonConverterFactory;

    public HttpConvertFactory(String charset, boolean snake) {
        this.charset = charset;
        ObjectMapper mapper = new ObjectMapper();
        if (snake) {
            mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        }
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        this.jacksonConverterFactory = JacksonConverterFactory.create(mapper, charset);
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {

        switch (defineType(type)) {
            case 0:
                return jacksonConverterFactory.responseBodyConverter(type, annotations, retrofit);
            case 1:
                return (Converter<ResponseBody, Byte>) value -> Byte.valueOf(value.string());
            case 2:
                return (Converter<ResponseBody, Short>) value -> Short.valueOf(value.string());
            case 3:
                return (Converter<ResponseBody, Integer>) value -> Integer.valueOf(value.string());
            case 4:
                return (Converter<ResponseBody, Long>) value -> Long.valueOf(value.string());
            case 5:
                return (Converter<ResponseBody, Double>) value -> Double.valueOf(value.string());
            case 6:
                return (Converter<ResponseBody, Float>) value -> Float.valueOf(value.string());
            case 7:
                return (Converter<ResponseBody, Character>) value -> value.string().charAt(0);
            case 8:
                return (Converter<ResponseBody, String>) value -> new String(value.bytes(), charset);
            default:
                return jacksonConverterFactory.responseBodyConverter(type, annotations, retrofit);
        }
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return jacksonConverterFactory.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit);
    }

    @Override
    public Converter<?, String> stringConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return super.stringConverter(type, annotations, retrofit);
    }

    private int defineType(Type type) {
        if (type == Byte.class || type == byte.class) {
            return 1;
        } else if (type == Short.class || type == short.class) {
            return 2;
        } else if (type == Integer.class || type == int.class) {
            return 3;
        } else if (type == Long.class || type == long.class) {
            return 4;
        } else if (type == Double.class || type == double.class) {
            return 5;
        } else if (type == Float.class || type == Float.class) {
            return 6;
        } else if (type == Character.class || type == char.class) {
            return 7;
        } else if (type == String.class) {
            return 8;
        }
        return 0;
    }
}
