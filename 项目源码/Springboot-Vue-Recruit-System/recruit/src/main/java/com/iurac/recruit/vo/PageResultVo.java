package com.iurac.recruit.vo;

import java.util.List;

public class PageResultVo<T> {
    private List<T> records;
    private Long total;

    public PageResultVo(List<T> records, Long total) {
        this.records = records;
        this.total = total;
    }

    public PageResultVo() {
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
