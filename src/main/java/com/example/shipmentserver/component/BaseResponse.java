package com.example.shipmentserver.component;

import lombok.Data;

@Data
public class BaseResponse<T> {
    private Integer code; // 编码：20000成功，其它数字为失败
    private String msg; // 错误信息
    private T data; // 数据

    public static <T> BaseResponse<T> success(T object) {
        BaseResponse<T> r = new BaseResponse<T>();
        r.data = object;
        r.code = 20000;
        return r;
    }

    public static <T> BaseResponse<T> error(String msg) {
        var r = new BaseResponse();
        r.msg = msg;
        r.code = -1;
        return r;
    }
}
