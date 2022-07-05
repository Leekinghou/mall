package com.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mall.common.ApiRestResponse;
import com.mall.exception.MallException;
import com.mall.exception.StatusCode;
import com.mall.model.dao.CategoryMapper;
import com.mall.model.pojo.Category;
import com.mall.model.request.AddCategoryReq;
import com.mall.model.vo.CategoryVO;
import com.mall.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;


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

    @Override
    public PageInfo listForAdmin(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize, "type, order_num");
        List<Category> categoryList = categoryMapper.selectList();
        PageInfo pageInfo = new PageInfo(categoryList);
        return pageInfo;
    }

    @Override
    @Cacheable(value = "listCategoryForCustomer") // redis
    public List<CategoryVO> listCategoryForCustomer(Integer parentId) {
        ArrayList<CategoryVO> categoryVOArrayList = new ArrayList<>();
        recursivelyFindCategories(categoryVOArrayList, parentId);
        return categoryVOArrayList;
    }

    private void recursivelyFindCategories(List<CategoryVO> categoryVOList, Integer parentId) {
        //递归获取所有子类别，并组合成为一个“目录树”
        List<Category> categoryList = categoryMapper.selectCategoriesByParentId(parentId);
        if(!CollectionUtils.isEmpty(categoryList)) {
            for (int i = 0; i < categoryList.size(); i++) {
                Category category = categoryList.get(i);
                CategoryVO categoryVO = new CategoryVO();
                BeanUtils.copyProperties(category, categoryVO);
                categoryVOList.add(categoryVO);
                recursivelyFindCategories(categoryVO.getChildCategory(), categoryVO.getId());
            }
        }
    }

}
