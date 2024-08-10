package com.admin.backend.utils;

import lombok.Data;

@Data
public class Response {
    //后端返回数据的封装
    private String code;
    private String message;
    private Object data;
    public Response(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    //用于有返回数据的情况
    public static Response success(Object data) {
        return new Response("SUCCESS", "成功", data);
    }
    //用于没有返回数据的情况，如新增
    public static Response success() {
        return new Response("SUCCESS", "成功", null);
    }
    //专门用于登录的情况
    public static Response success(String code,String message,Object data) {
        return new Response(code, message, data);
    }
    //用于返回错误信息
    public static Response fail(String message) {
        return new Response("Failure", message, null);
    }
    //专门用于登录的情况
    public static Response fail(String code,String message) {
        return new Response(code, message, null);
    }

}
