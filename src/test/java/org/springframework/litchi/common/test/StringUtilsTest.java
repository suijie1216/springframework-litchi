package org.springframework.litchi.common.test;

import com.google.common.collect.Sets;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Set;

/**
 * @author: suijie
 * @date: 2018/5/27 09:46
 * @description:
 */
public class StringUtilsTest {

    public static String txt2String(File file){
        String result = "";
        Set<String> set = Sets.newHashSet();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s;
            while((s = br.readLine())!=null){
                set.add(s);
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println(set.size());
        for(String s : set){
            result = result + "," + s;
        }
        result =result.substring(1);
        return result;
    }

    @Test
    public void readTest(){
        File file = new File("/Users/suijie/test");
        System.out.println(txt2String(file));
    }
}
