package org.springframework.litchi.common.factorybean;

import org.springframework.beans.factory.FactoryBean;

/**
 * @author: suijie
 * @date: 2018/6/11 19:11
 * @description:
 */
public class HelloWorldFactoryBean implements FactoryBean<HelloWorld> {

    public Class clazz;

    public HelloWorldFactoryBean(Class clazz){
        this.clazz = clazz;
    }

    @Override
    public HelloWorld getObject() {
        return new HelloWorld() {
            @Override
            public String say() {
                return "hi leo!!!";
            }
        };
    }

    @Override
    public Class<?> getObjectType() {
        return HelloWorld.class;
    }
}
