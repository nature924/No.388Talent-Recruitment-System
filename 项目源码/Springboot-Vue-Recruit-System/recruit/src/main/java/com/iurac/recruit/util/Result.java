package com.iurac.recruit.util;

import java.io.Serializable;

public class Result implements Serializable {
    private boolean success;
    private String msg;
    private Object data;
    private Integer code;

    public Result() {
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static Result succ(String msg) {
        Result m = new Result();
        m.setSuccess(true);
        m.setCode(0);
        m.setData(null);
        m.setMsg(msg);
        return m;
    }

    public static Result succ(String msg, Object data) {
        Result m = new Result();
        m.setSuccess(true);
        m.setCode(0);
        m.setData(data);
        m.setMsg(msg);
        return m;
    }

    public static Result fail(String msg) {
        Result m = new Result();
        m.setSuccess(false);
        m.setCode(-1);
        m.setData(null);
        m.setMsg(msg);
        return m;
    }

    public static Result fail(String msg, Object data) {
        Result m = new Result();
        m.setSuccess(false);
        m.setCode(-1);
        m.setData(data);
        m.setMsg(msg);
        return m;
    }



}