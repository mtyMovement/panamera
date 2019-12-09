package com.porsche.panamera.core.service.serviceImpl;

import com.porsche.panamera.core.dal.domain.User;
import com.porsche.panamera.core.dal.manager.managerImpl.UserManagerImpl;
import com.porsche.panamera.core.service.UserService;
import com.porsche.panamera.web.pojo.UserPojo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserManagerImpl userManager;

    @Override
    public User userLogin(UserPojo requestUser) {
        User user = new User();
        BeanUtils.copyProperties(requestUser, user);
        log.info("userLogin, requestUser:{}, user:{}",requestUser,user);
        User userResult = userManager.selectOne(user);
        if(Objects.isNull(userResult)){
            return null;
        }
        return userResult;
    }

    @Override
    public User getUserById(Integer id) {
        if (Objects.isNull(id)) {
            return null;
        }
        return userManager.selectById(id);
    }
}
