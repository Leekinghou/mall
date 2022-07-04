package com.mall.model.dao;

import com.mall.model.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    void selectByName() {
        User user = userMapper.selectByName("xiaomu");
        System.out.println(user.toString());

    }
}