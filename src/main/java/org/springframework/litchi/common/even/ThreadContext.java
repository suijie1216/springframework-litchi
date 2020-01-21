package org.springframework.litchi.common.even;

import com.google.common.collect.Maps;

import java.lang.ref.SoftReference;
import java.util.Map;

/**
 * @author suijie
 */
public class ThreadContext {

    /**
     * 上下文容器
     */
    private static ThreadLocal<Map<String, SoftReference<Object>>> context = null;
    /**
     * 初始化容器
     */
    public static void iniContext(){
        if(context == null){
            synchronized(ThreadContext.class){
                if(context == null){
                    context = ThreadLocal.withInitial(Maps::newHashMap);
                }
            }
        }
    }

    /**
     * put值
     */
    public static void put(String key, Object value) {
        SoftReference<Object> ref = new SoftReference<>(value);
        context.get().put(key, ref);
    }

    /**
     * 获取变量
     */
    public static Object get(String key) {
        Object value = null;
        SoftReference<Object> valRef = context.get().get(key);
        if(valRef != null){
            value = valRef.get();
            if(value == null){
                context.get().remove(key);
            }
        }
        return value;
    }

    /**
     * 上下文大小
     */
    public static int size(){
        return context.get().size();
    }

    /**
     * 清空线程上下文
     * 注意：此方法强制在线程结束前调用
     */
    public static void clear(){
        context.remove();
    }
}
