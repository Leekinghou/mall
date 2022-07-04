package com.mall.service.impl;

import com.mall.common.ApiRestResponse;
import com.mall.exception.MallException;
import com.mall.exception.StatusCode;
import com.mall.model.dao.CategoryMapper;
import com.mall.model.pojo.Category;
import com.mall.model.request.AddCategoryReq;
import com.mall.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public void add(AddCategoryReq addCategoryReq) {
        Category category = new Category();
        BeanUtils.copyProperties(addCategoryReq, category);
        Category categoryOld = categoryMapper.selectByName(addCategoryReq.getName());
        if(categoryOld != null) {
            throw new MallException(StatusCode.NAME_EXIST);
        }
        int count = categoryMapper.insertSelective(category);
        if(count == 0) {
            throw new MallException(StatusCode.INSERT_FAILED);
        }
    }

    @Override
    public void update(Category updateCategory) {
        if(updateCategory.getName() != null) {
            Category categoryOld = categoryMapper.selectByName(updateCategory.getName());

            if (categoryOld != null && !categoryOld.getId().equals(updateCategory.getId())) {
                throw new MallException(StatusCode.NAME_EXIST);
            }
        }
        int count = categoryMapper.updateByPrimaryKeySelective(updateCategory);
        if(count == 0) {
            throw new MallException(StatusCode.UPDATE_FAILED);
        }
    }

    @Override
    public ApiRestResponse delete(Integer id) {
        Category categoryOld = categoryMapper.selectByPrimaryKey(id);

        if(categoryOld == null) {
            throw new MallException(StatusCode.DELETE_ERROR);
        }
        int count = categoryMapper.deleteByPrimaryKey(id);
        if(count == 0) {
            throw new MallException(StatusCode.DELETE_ERROR);
        }
        return null;
    }
}
