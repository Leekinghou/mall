package com.mall.exception;

public class MallException extends RuntimeException{
    private final Integer code;
    private final String message;

    public MallException(Integer code, String description) {
        this.code = code;
        this.message = description;
    }

    public MallException(StatusCode sc) {
        this(sc.getCode(), sc.getDescription());
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
