package org.springframework.litchi.module.response;

import lombok.Data;

import java.util.List;

/**
 * @author suijie
 */
@Data
public class PageResponse<T> extends Response<List<T>> {
    /**
     * 统计总数
     */
    private int total;
    /**
     * 页码
     */
    private int pageNo;
    /**
     * 每页数据量
     */
    private int pageSize;
}
