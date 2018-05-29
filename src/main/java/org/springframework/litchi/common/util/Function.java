package org.springframework.litchi.common.util;

public interface Function<K, V> {

    K apply(V v);

}
