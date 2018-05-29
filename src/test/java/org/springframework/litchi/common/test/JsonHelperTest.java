package org.springframework.litchi.common.test;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.litchi.common.helper.JsonHelper;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author suijie
 * @date: 2018/5/23 19:38
 * @description:
 */
public class JsonHelperTest {

    @Test
    public void toJson() {
        Store store = new Store();
        store.setId(1716L);
        store.setName("测试门店");
        store.setOnlineTime(new Date(1514474781575L));
        Goods g1 = new Goods();
        g1.setId(1L);
        g1.setName("手机");
        Goods g2 = new Goods();
        g2.setId(2L);
        g2.setName("电脑");
        List<Goods> goodsList = Lists.newArrayList();
        goodsList.add(g1);
        goodsList.add(g2);
        store.setGoodsList(goodsList);
        System.out.println(JsonHelper.toJson(store));
    }

    @Test
    public void fromJson(){
        String json = "{\"id\":1716,\"name\":\"测试门店\",\"online_time\":\"2017-12-28 23:26:21\",\"goods_list\":[{\"id\":1,\"name\":\"手机\"},{\"id\":2,\"name\":\"电脑\"}]}";
        Store store = JsonHelper.fromJson(json, Store.class);
        System.out.println(store);
    }

    @Test
    public void mapFromJson() {
        Map<String, Store> map = Maps.newHashMap();
        Store store = new Store();
        store.setId(1716L);
        store.setName("测试门店");
        store.setOnlineTime(new Date(1514474781575L));
        Goods g1 = new Goods();
        g1.setId(1L);
        g1.setName("手机");
        Goods g2 = new Goods();
        g2.setId(2L);
        g2.setName("电脑");
        List<Goods> goodsList = Lists.newArrayList();
        goodsList.add(g1);
        goodsList.add(g2);
        store.setGoodsList(goodsList);
        map.put("k1", store);
        String json = JsonHelper.toJson(map);
        System.out.println(json);
        map = JsonHelper.mapFromJson(json, String.class, Store.class);
        Store s1 = map.get("k1");
        System.out.println(s1);
    }

    @Test
    public void listFromJson() {
        String json = "[4938430150972220247, 4938430150972220999]";
        List<Long> list = JsonHelper.listFromJson(json, Long.class);
        Assert.assertEquals(list.size(), 2);
        Assert.assertEquals(list.get(0).longValue(), 4938430150972220247L);
        Assert.assertEquals(list.get(1).longValue(), 4938430150972220999L);
    }

    @Data
    private static class Store {
        private Long id;
        private String name;
        private Date onlineTime;
        private List<Goods> goodsList;
    }

    @Data
    private static class Goods{
        private Long id;
        private String name;
    }
}
