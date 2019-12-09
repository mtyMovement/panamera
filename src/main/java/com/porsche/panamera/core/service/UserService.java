package com.porsche.panamera.core.service;

import com.porsche.panamera.core.dal.domain.User;
import com.porsche.panamera.web.pojo.UserPojo;
import org.springframework.stereotype.Service;

public interface UserService {

    //用户登录验证
    User userLogin(UserPojo requestUser);

    User getUserById(Integer id);
}
