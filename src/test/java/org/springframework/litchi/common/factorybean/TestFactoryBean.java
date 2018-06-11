package org.springframework.litchi.common.factorybean;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.litchi.common.test.BeanConfig;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @author: suijie
 * @date: 2018/6/11 19:15
 * @description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={BeanConfig.class})
public class TestFactoryBean {

    @Resource
    private HelloWorld helloWorld;

    @Test
    public void test(){
        System.out.println(helloWorld.say());
    }
}
