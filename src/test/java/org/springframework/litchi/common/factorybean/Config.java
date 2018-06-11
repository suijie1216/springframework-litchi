package org.springframework.litchi.common.factorybean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.litchi.common.cache.LocalCache;
import org.springframework.litchi.common.test.BaiduCall;
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
public class Config {

    @Bean
    public HelloWorldFactoryBean buildHelloWorld(){
        return new HelloWorldFactoryBean(HelloWorld.class);
    }

}
