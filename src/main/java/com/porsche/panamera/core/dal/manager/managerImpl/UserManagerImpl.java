package com.porsche.panamera.core.dal.manager.managerImpl;

import com.porsche.panamera.core.dal.domain.User;
import com.porsche.panamera.core.dal.dao.UserDao;
import com.porsche.panamera.core.dal.manager.UserManager;
import com.porsche.panamera.core.common.base.BaseManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserManagerImpl extends BaseManagerImpl<UserDao, User> implements UserManager{
    @Autowired
    UserDao userDao;
}
