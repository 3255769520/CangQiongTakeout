package com.hhxy.service;

import com.hhxy.dto.UserLoginDTO;
import com.hhxy.entity.User;

public interface UserService {
    User wxLogin(UserLoginDTO userLoginDTO);
}
