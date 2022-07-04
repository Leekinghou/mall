package com.mall.controller;

import com.mall.common.ApiRestResponse;
import com.mall.common.Constant;
import com.mall.exception.MallException;
import com.mall.exception.StatusCode;
import com.mall.model.pojo.User;
import com.mall.service.UserService;
import com.mall.utils.MD5Utils;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/getuser")
    @ResponseBody
    public User personalPage(@RequestParam Integer id) {
        User user = userService.getUser(id);
        return user;
    }

    @PostMapping("/register")
    @ResponseBody
    public ApiRestResponse register(@RequestParam("userName") String userName, @RequestParam("password") String password) throws MallException {
         if(StringUtils.isNullOrEmpty(userName)) {
            return ApiRestResponse.error(StatusCode.NEED_PASSWORD);
         }
         if(StringUtils.isNullOrEmpty(password)) {
             return ApiRestResponse.error(StatusCode.NEED_PASSWORD);
         }
         if(password.length() < 8) {
             return ApiRestResponse.error(StatusCode.PASSWORD_TOO_SHORT);
         }
         userService.register(userName, password);
         return ApiRestResponse.success();
    }

    @PostMapping("/login")
    @ResponseBody
    public ApiRestResponse login(@RequestParam("userName") String userName, @RequestParam("password") String password,
                            HttpSession session) throws MallException {
        if(StringUtils.isNullOrEmpty(userName)) {
            return ApiRestResponse.error(StatusCode.NEED_PASSWORD);
        }
        if(StringUtils.isNullOrEmpty(password)) {
            return ApiRestResponse.error(StatusCode.NEED_PASSWORD);
        }
        User user = userService.login(userName, password);
        // 保存用户信息时不保存密码
        user.setPassword(null);
        session.setAttribute(Constant.MALL_USER, user);
        return ApiRestResponse.success(user);
    }

    @PostMapping("/user/update")
    @ResponseBody
    public ApiRestResponse updateUserInfo(HttpSession session, @RequestParam String signature) throws MallException {
        User currentUser = (User) session.getAttribute(Constant.MALL_USER);

        if(currentUser == null) {
            return ApiRestResponse.error(StatusCode.NEED_LOGIN);
        }
        User user = new User();
        user.setId(currentUser.getId());
        user.setPersonalizedSignature(signature);
        userService.updateInfomation(user);
        return ApiRestResponse.success();
    }

    /**
     * 登出 清除session
     * @param session
     * @return
     * @throws MallException
     */
    @PostMapping("/user/logout")
    @ResponseBody
    public ApiRestResponse logout(HttpSession session) throws MallException {
        session.removeAttribute(Constant.MALL_USER);
        return ApiRestResponse.success();
    }

    // 管理员登录接口
    @PostMapping("/adminlogin")
    @ResponseBody
    public ApiRestResponse adminlogin(@RequestParam("userName") String userName, @RequestParam("password") String password,
                                 HttpSession session) throws MallException {
        if(StringUtils.isNullOrEmpty(userName)) {
            return ApiRestResponse.error(StatusCode.NEED_PASSWORD);
        }
        if(StringUtils.isNullOrEmpty(password)) {
            return ApiRestResponse.error(StatusCode.NEED_PASSWORD);
        }
        User user = userService.login(userName, password);
        // 校验是否是管理员
        if (userService.checkAdminRole(user)) {
            // 执行操作
            user.setPassword(null);
            session.setAttribute(Constant.MALL_USER, user);
            return ApiRestResponse.success(user);
        } else {
            return ApiRestResponse.error(StatusCode.NEED_ADMIN);
        }

    }

}
