package com.mall.exception;

/*---------------------------
        在构造自定义业务异常对象时使用了 枚举 的方式，将常见的业务错误提示语对应的错误代码进行
        映射，枚举类如下所示：*/
public enum StatusCode {
    NEED_USER_NAME(10001, "用户名不能为空"),
    USER_NOT_FOUND(10002,"用户不存在"),
    NEED_PASSWORD(10003, "密码不能为空"),
    PASSWORD_TOO_SHORT(10004, "密码不能少于8个字符"),
    USER_EXIST(10005, "用户名已存在，注册失败"),
    INSERT_FAILED(10006, "插入失败，请重试"),
    USER_NOT_EXIST(10007,"用户不存在"),
    NEED_LOGIN(10008,"用户未登录"),
    UPDATE_FAILED(10009,"更新错误"),
    NEED_ADMIN(10010,"无管理员权限"),
    PARAM_NOT_NULL(10011,"参数不能为空"),
    REQUEST_PARAM_ERROR(10009,"更新错误"),
    NAME_EXIST(100012, "名称已存在"),
    DELETE_ERROR(10013,"目录ID不存在，删除失败"),
    REQUEST_SUCCESS(200,"用户交互正常"),
    USER_AUTHENTICATION_ERROR(10002,"用户密码不正确"),
    DATA_NULL(40404,"查询数据为空"),
    RETURN_ERROR(911,"用户交互存在异常"),
    SYSTEM_ERROR(200000, "系统异常"),
    ;

    /* public static final  int ACCOUNT_OR_PASSWORD_ERROR=401;
     public static final  int REQUEST_SUCCESS=200;
     public static final int  LOGIN_SUCCESS=202;
     public static final int  NEED_LOGIN=402;
     public static final int RETURN_ERROR=911;*/

    private  Integer code;
    private  String description;

    StatusCode(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public String getDescription(String description){
        this.description=description;
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
