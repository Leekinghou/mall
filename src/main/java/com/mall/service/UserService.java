package com.mall.service;

import com.mall.exception.MallException;
import com.mall.model.pojo.User;

public interface UserService {
    User getUser(Integer id);

    /**
     * 注册
     * @param userName 用户名
     * @param Password 密码
     */
    void register(String userName, String Password) throws MallException;


    User login(String userName, String password) throws MallException;

    void updateInfomation(User user);

    boolean checkAdminRole(User user);
}
