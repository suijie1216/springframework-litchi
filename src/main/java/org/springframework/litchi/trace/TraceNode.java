package org.springframework.litchi.trace;

import lombok.Data;

import java.text.SimpleDateFormat;

/**
 * @author: suijie
 * @date: 2018/5/30 22:15
 * @description:
 */
@Data
public class TraceNode {
    private String name;
    private long time;
    private int index;

    public String getFormatTime(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return format.format(time);
    }
}
