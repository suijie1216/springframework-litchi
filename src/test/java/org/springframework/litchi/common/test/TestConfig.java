package org.springframework.litchi.common.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.litchi.common.cache.LocalCache;

/**
 * @author: suijie
 * @date: 2018/5/27 15:07
 * @description:
 */
@Configuration
@EnableAspectJAutoProxy
@ComponentScans({@ComponentScan("org.springframework.litchi")})
public class TestConfig {

    @Bean
    public LocalCache<String, Object> buildLocalCache(){
        return new LocalCache<>(50, 1000000);
    }

}
