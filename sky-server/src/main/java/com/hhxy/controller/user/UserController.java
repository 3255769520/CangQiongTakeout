package com.hhxy.controller.user;

import com.hhxy.constant.JwtClaimsConstant;
import com.hhxy.dto.UserLoginDTO;
import com.hhxy.entity.User;
import com.hhxy.properties.JwtProperties;
import com.hhxy.result.Result;
import com.hhxy.service.UserService;
import com.hhxy.utils.JwtUtil;
import com.hhxy.vo.UserLoginVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user/user")
@Tag(name = "C端-用户相关接口")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtProperties jwtProperties;

    @PostMapping("/login")
    @Operation(summary = "微信登录")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("微信用户登录：{}", userLoginDTO.code());

        User user = userService.wxLogin(userLoginDTO);

        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        String token = JwtUtil.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claims);

        UserLoginVO userLoginVO = new UserLoginVO(user.getId(), user.getOpenid(), token);
        return Result.success(userLoginVO);
    }
}
