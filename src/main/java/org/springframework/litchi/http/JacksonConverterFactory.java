package org.springframework.litchi.http;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * @author: suijie
 * @date: 2018/6/9 11:22
 * @description:
 */
public final class JacksonConverterFactory extends Converter.Factory {

    private String charset;

    private MediaType MEDIA_TYPE;

    private final ObjectMapper mapper;


    public static JacksonConverterFactory create(ObjectMapper mapper, String charset) {
        if (mapper == null) {
            throw new NullPointerException("mapper == null");
        }
        if (charset == null) {
            throw new NullPointerException("charset == null");
        }
        return new JacksonConverterFactory(mapper, charset);
    }

    private JacksonConverterFactory(ObjectMapper mapper, String charset) {
        this.charset = charset;
        MEDIA_TYPE = MediaType.parse("application/json; charset=" + charset);
        this.mapper = mapper;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        JavaType javaType = mapper.getTypeFactory().constructType(type);
        ObjectReader reader = mapper.readerFor(javaType);
        return new JacksonResponseBodyConverter<>(reader);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        JavaType javaType = mapper.getTypeFactory().constructType(type);
        ObjectWriter writer = mapper.writerFor(javaType);
        return new JacksonRequestBodyConverter<>(writer);
    }

    private class JacksonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
        private final ObjectReader adapter;

        JacksonResponseBodyConverter(ObjectReader adapter) {
            this.adapter = adapter;
        }

        @Override
        public T convert(ResponseBody value) throws IOException {
            try {
                return adapter.readValue(new String(value.bytes(), charset));
            } finally {
                value.close();
            }
        }
    }

    private class JacksonRequestBodyConverter<T> implements Converter<T, RequestBody> {

        private final ObjectWriter adapter;

        JacksonRequestBodyConverter(ObjectWriter adapter) {
            this.adapter = adapter;
        }

        @Override
        public RequestBody convert(T value) throws IOException {
            byte[] bytes = adapter.writeValueAsBytes(value);
            return RequestBody.create(MEDIA_TYPE, bytes);
        }
    }
}
