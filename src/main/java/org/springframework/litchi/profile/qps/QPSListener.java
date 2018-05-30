package org.springframework.litchi.profile.qps;

/**
 * @author: suijie
 * @date: 2018/5/29 19:40
 * @description:
 */
public interface QPSListener {
    /**
     * qps监听器
     * @param node
     */
    void event(QPSNode node);
}
