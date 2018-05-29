package org.springframework.litchi.common.helper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author suijie
 * @date: 2018/5/23 19:38
 * @description:
 */
public class JsonHelper {

    private static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    /**
     * 将object对象转为json字符串
     * @param object 待转json的对象
     * @param <T>    对象object的泛型类型
     * @return json字符串
     */
    public static <T> String toJson(T object) {
        if (object == null) {
            return "";
        }
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将json字符串转为clazz类型的java对象
     * @param json  字符串json数据
     * @param clazz 类
     * @param <T>   泛型
     * @return clazz类型的java对象
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        try {
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将json字符串转为Map类型的集合
     * @param json 字符串json数据
     * @param <K,  V> 泛型
     * @return Map类型的集合
     */
    public static <K, V> Map<K, V> mapFromJson(String json, Class<K> clazzK, Class<V> clazzV) {
        if (StringUtils.isBlank(json)) {
            return Collections.emptyMap();
        }

        JavaType javaType = mapper.getTypeFactory().constructParametricType(HashMap.class, clazzK, clazzV);

        try {
            return mapper.readValue(json, javaType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将json字符串转为List类型的集合
     * @param json 字符串json数据
     * @param <T>  泛型
     * @return List类型的集合
     */
    public static <T> List<T> listFromJson(String json, Class<T> clazz) {
        if (StringUtils.isBlank(json)) {
            return Collections.emptyList();
        }
        JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, clazz);

        try {
            return mapper.readValue(json, javaType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

