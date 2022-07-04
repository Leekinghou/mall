package com.mall.service.impl;

import com.mall.common.ApiRestResponse;
import com.mall.common.Constant;
import com.mall.exception.MallException;
import com.mall.exception.StatusCode;
import com.mall.model.dao.UserMapper;
import com.mall.model.pojo.User;
import com.mall.service.UserService;
import com.mall.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    private MD5Utils md5Utils;

    @Override
    public User getUser(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public void register(String userName, String Password) throws MallException {
        // 首先要确定用户是否已存在
        User result = userMapper.selectByName(userName);
        /**
         * service层表示用户已存在的方法不该是跟controller层一样返回给用户
         * 解决思路应该是抛出异常给到controller层
         * 由controller层返回提示信息给用户
         */
        if(result != null) {
            throw new MallException(StatusCode.USER_EXIST);
        }

        User user = new User();
        user.setUsername(userName);
        String savePassword = MD5Utils.md5Digest(Password, Constant.SALT);
        user.setPassword(savePassword);
        user.setSalt(190758);
        int flag = userMapper.insertSelective(user);
        if(flag == 0) {
            throw new MallException(StatusCode.INSERT_FAILED);
        }
    }

    @Override
    public User login(String userName, String password) throws MallException {
        String md5Password = null;
        md5Password = MD5Utils.md5Digest(password, Constant.SALT);
        User user = userMapper.selectLogin(userName, md5Password);
        if (user == null) {
            throw new MallException(StatusCode.USER_AUTHENTICATION_ERROR);
        }
        return user;
    }

    @Override
    public void updateInfomation(User user) {
        // 首先要确定用户是否已存在
        int updateCount = userMapper.updateByPrimaryKeySelective(user);
        if(updateCount > 1) {
            throw new MallException(StatusCode.UPDATE_FAILED);
        }
    }

    @Override
    public boolean checkAdminRole(User user) {
        // 1是普通用户，2是管理员
        return user.getRole().equals(2);
    }
}
