package org.springframework.litchi.common.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.litchi.common.cache.LocalCache;
import org.springframework.litchi.http.HttpServiceBuilder;
import org.springframework.litchi.profile.qps.QPSCounterWorker;
import org.springframework.litchi.profile.qps.QPSListener;
import org.springframework.litchi.profile.qps.QPSNode;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: suijie
 * @date: 2018/5/27 15:07
 * @description:
 */
@Configuration
@EnableAspectJAutoProxy
@ComponentScans({@ComponentScan("org.springframework.litchi")})
public class BeanConfig {

    @Bean
    public HttpServiceBuilder<BaiduCall> buildBaiduCall(){
        return new HttpServiceBuilder<>(BaiduCall.class);
    }

    @Bean
    public LocalCache<String, Object> buildLocalCache(){
        return new LocalCache<>(50, 1000000);
    }

    @Bean(value = "qpsCounterWorker")
    public QPSCounterWorker buildQPSCounterWorker() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        QPSListener listener = new QPSListener() {
            @Override
            public void event(QPSNode node) {
                String result = String.format("api:%s,time:%s,qps:%s,rt:%s",
                        node.getPath(),
                        format.format(new Date(node.getTime())),
                        node.getQps(),
                        node.getRt().longValue() / node.getQps().intValue());
                System.out.println(result);
            }
        };
        return new QPSCounterWorker(listener);
    }

}
