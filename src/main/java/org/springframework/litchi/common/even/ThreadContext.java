package org.springframework.litchi.common.even;

import com.google.common.collect.Maps;

import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author: suijie
 * @date: 2018/5/26 22:47
 * @description:
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
                    context = ThreadLocal.withInitial(new Supplier<Map<String, SoftReference<Object>>>() {
                        @Override
                        public Map<String, SoftReference<Object>> get() {
                            return Maps.newHashMap();
                        }
                    });
                }
            }
        }
    }

    /**
     *
     * @param key
     * @param value
     */
    public static void put(String key, Object value) {
        SoftReference<Object> ref = new SoftReference<>(value);
        context.get().put(key, ref);
    }

    /**
     * 获取变量
     * @param key
     * @return
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
     * @return
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
