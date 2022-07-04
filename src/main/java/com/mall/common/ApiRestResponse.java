package com.mall.common;

import com.mall.exception.MallException;
import com.mall.exception.StatusCode;

import java.io.Serializable;

public class ApiRestResponse<T> implements Serializable {
    private Integer status;

    private String msg;

    // 写泛型是因为返回类型可能是商品类型也可能是订单类型
    private T data;

    private static final int OK_CODE = 10000;

    private static final String OK_MSG = "SUCCESS";

    public ApiRestResponse(Integer status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public ApiRestResponse(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public ApiRestResponse() {
        this(OK_CODE, OK_MSG);
    }

    // 返回一个两参的响应信息: {OK_CODE, OK_MSG}
    public static <T> ApiRestResponse<T> success() {
        return new ApiRestResponse<>();
    }

    public static <T> ApiRestResponse<T> success(T result) {
        ApiRestResponse<Object> response = new ApiRestResponse<>();
        response.setData(result);
        return (ApiRestResponse<T>) response;
    }

    public static <T> ApiRestResponse<T> error(Integer code, String description) {
        return new ApiRestResponse<>(code, description);
    }

    public static <T> ApiRestResponse<T> error(StatusCode ex) {
        return new ApiRestResponse<>(ex.getCode(), ex.getDescription());
    }



    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static int getOkCode() {
        return OK_CODE;
    }

    @Override
    public String toString() {
        return "ApiRestResponse{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
