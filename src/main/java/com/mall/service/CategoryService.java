package com.mall.service;

import com.mall.common.ApiRestResponse;
import com.mall.model.pojo.Category;
import com.mall.model.request.AddCategoryReq;

public interface CategoryService {
    void add(AddCategoryReq addCategoryReq);

    void update(Category updateCategoryReq);

    ApiRestResponse delete(Integer id);
}
