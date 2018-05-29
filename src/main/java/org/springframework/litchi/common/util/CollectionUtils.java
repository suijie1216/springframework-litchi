package org.springframework.litchi.common.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CollectionUtils {

    public static <K, V> Map<K, V> listToMap(List<V> list, Function<K, V> function) {
        Map<K, V> map = Maps.newHashMap();
        for (V v : list) {
            K k = function.apply(v);
            if (k != null) {
                map.put(function.apply(v), v);
            }
        }
        return map;
    }

    public static <K, V> Map<K, List<V>> listToListMap(List<V> list, Function<K, V> function) {
        Map<K, List<V>> map = Maps.newHashMap();
        for (V v : list) {
            K k = function.apply(v);
            if (k != null) {
                List<V> currentList = map.get(k);
                if (currentList == null) {
                    currentList = Lists.newArrayList();
                    map.put(k, currentList);
                }
                currentList.add(v);
            }
        }
        return map;
    }

    public static <K,V> void addListMap(Map<K,List<V>> map,K k,V v){
        List<V> list = map.get(k);
        if(list==null){
            list=new ArrayList<>();
            map.put(k,list);
        }
        list.add(v);
    }

    public static <T> List<T> splitList(List<T> main, int pageNo, int pageSize) {
        int total = main.size();
        int begin = (pageNo - 1) * pageSize;
        List<T> sub = new ArrayList<>();
        if (begin >= total || pageNo < 1 || pageSize < 1) {
            return sub;
        }
        for (int i = begin; i < total && i < begin + pageSize; i++) {
            sub.add(main.get(i));
        }
        return sub;
    }

    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.size() == 0;
    }

    public static boolean isNotEmpty(Collection collection) {
        return !isEmpty(collection);
    }

    public static boolean isEmpty(Map map) {
        return map == null || map.isEmpty();
    }

    public static boolean isNotEmpty(Map map) {
        return !isEmpty(map);
    }
}
