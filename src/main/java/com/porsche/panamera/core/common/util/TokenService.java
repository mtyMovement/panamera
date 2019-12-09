package com.porsche.panamera.core.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.porsche.panamera.core.dal.domain.User;
import org.springframework.stereotype.Service;


@Service
public class TokenService {

    public String getToken(User user) {
        String token = "";
        token = JWT.create().withAudience(String.valueOf(user.getUserId()))
                .sign(Algorithm.HMAC256(user.getPassword()));
        return token;
    }
}
