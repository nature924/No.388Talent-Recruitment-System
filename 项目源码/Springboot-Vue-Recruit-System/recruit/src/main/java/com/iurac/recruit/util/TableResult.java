package com.iurac.recruit.util;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

public class TableResult<T> {

    private Integer code;
    private String msg;
    private Long count;
    private List<T> data;

    public TableResult() {
    }

    public TableResult(Integer code, String msg, Long count, List<T> data) {
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.data = data;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

}
