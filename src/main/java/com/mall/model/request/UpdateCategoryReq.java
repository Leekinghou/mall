package com.mall.model.request;


import io.swagger.models.auth.In;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 1. 一个类对应一个功能
 * 2. 避免传入createTime等字段导致数据库被修改
 * 3. 对传入参数要有下面的参数校验注解，如果加在entity实体上，会很乱
 */

public class UpdateCategoryReq {
    @NotNull(message = "id must be not null")
    private Integer id;

    @Size(min = 2, max = 5)
    private String name;

    @Max(3) // 目录最大层级数目
    private Integer type;

    private Integer parentId;

    private Integer orderNum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }
}
