package org.springframework.litchi.common.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.litchi.common.helper.JsonHelper;
import org.springframework.litchi.module.response.Response;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @author: suijie
 * @date: 2018/6/9 16:40
 * @description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={BeanConfig.class})
public class HttpTest {

    @Resource
    private BaiduCall baiduCall;

    @Test
    public void test(){
        Response response = baiduCall.test(4960103394892318721L);
        System.out.println(JsonHelper.toJson(response));
    }
}
