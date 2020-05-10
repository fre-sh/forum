package com.fresh.forum.dto;

public class ResponseTO {

    private int code;

    private Object data;

    private String msg;

    public static ResponseTO success() {
        return new ResponseTO(0, null, null);
    }

    public static ResponseTO success(Object data) {
        return new ResponseTO(0, data, null);
    }

    public static ResponseTO failed(String msg) {
        return new ResponseTO(1, null, msg);
    }

    public static ResponseTO of(int code, Object data, String msg) {
        return new ResponseTO(code, data, msg);
    }

    public ResponseTO(int code, Object data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
