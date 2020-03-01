package org.nico.mocker.resp;

import org.nico.mocker.consts.RespCode;

public class RespVo<T> {
    
    private int code;
    
    private T data;
    
    private String msg;
    
    public RespVo(RespCode respCode, T data) {
        this.code = respCode.getCode();
        this.msg = respCode.getMsg();
        this.data = data;
    }
    
    public RespVo(RespCode respCode, T data, Object...objects) {
        this.code = respCode.getCode();
        this.msg = String.format(respCode.getMsg(), objects);
        this.data = data;
    }
    
    public static <T> RespVo<T> success(T data) {
        return new RespVo<T>(RespCode.SUCCESS, data);
    }
    
    public static <T> RespVo<T> success() {
        return new RespVo<T>(RespCode.SUCCESS, null);
    }
    
    public static <T> RespVo<T> failure(T data) {
        return new RespVo<T>(RespCode.FAILURE, data);
    }
    
    public static <T> RespVo<T> failure() {
        return new RespVo<T>(RespCode.FAILURE, null);
    }
    
    public static <T> RespVo<T> failure(RespCode respCode) {
        return new RespVo<T>(respCode, null);
    }
    
    public static <T> RespVo<T> failure(RespCode respCode, Object...objects) {
        return new RespVo<T>(respCode, null, objects);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(T data) {
        this.data = data;
    }
    
}
