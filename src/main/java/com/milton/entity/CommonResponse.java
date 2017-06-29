package com.milton.entity;

/**
 * @author milton.zhang
 * @description 公共返回
 * @date 2017-06-05 15:19
 */
public class CommonResponse {
    private Integer code = 0;
    private String msg = "成功";
    private Object data;

    public CommonResponse() {
    }

    public CommonResponse(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
