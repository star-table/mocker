package org.nico.mocker.consts;

public enum RespCode {

    SUCCESS(200, "成功"),
    
    API_NOT_FOUND(404, "接口不存在"),
    FAILURE(500, "失败"),
    
    API_PRASE_ERR(10001, "api解析异常"),
    ;
    
    private int code;
    
    private String msg;

    private RespCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
