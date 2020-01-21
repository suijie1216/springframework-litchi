package org.springframework.litchi.profile.qps;

/**
 * @author: suijie
 */
public interface QPSListener {
    /**
     * qps监听器
     */
    void event(QPSNode node);
}
