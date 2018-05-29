package org.springframework.litchi.qps;

/**
 * @author: suijie
 * @date: 2018/5/29 19:40
 * @description:
 */
public interface QPSListener {
    /**
     * qps监听器
     * @param path
     * @param time
     * @param qps
     * @param rt
     */
    void event(String path, String time, int qps, long rt);
}
