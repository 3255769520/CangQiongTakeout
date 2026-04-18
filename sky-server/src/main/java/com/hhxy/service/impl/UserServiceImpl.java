package com.hhxy.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hhxy.constant.MessageConstant;
import com.hhxy.dto.UserLoginDTO;
import com.hhxy.entity.User;
import com.hhxy.exception.LoginFailedException;
import com.hhxy.mapper.UserMapper;
import com.hhxy.properties.WeChatProperties;
import com.hhxy.service.UserService;
import com.hhxy.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    public static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";

    @Autowired
    private WeChatProperties weChatProperties;
    @Autowired
    private UserMapper userMapper;

    @Override
    public User wxLogin(UserLoginDTO userLoginDTO) {
        String openid = getOpenid(userLoginDTO.code());

        if (openid == null) {
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }

        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getOpenid, openid));

        if (user == null) {
            user = User.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now())
                    .build();
            userMapper.insert(user);
        }

        return user;
    }

    private String getOpenid(String code) {
        Map<String, String> map = new HashMap<>();
        map.put("appid", weChatProperties.getAppid());
        map.put("secret", weChatProperties.getSecret());
        map.put("js_code", code);
        map.put("grant_type", "authorization_code");

        String json = HttpClientUtil.doGet(WX_LOGIN, map);

        log.info("微信服务器返回的原始响应数据：{}", json);

        JSONObject jsonObject = JSON.parseObject(json);
        
        if (jsonObject.containsKey("errcode")) {
            log.error("微信登录报错，错误码：{}，错误信息：{}", 
                      jsonObject.get("errcode"), 
                      jsonObject.get("errmsg"));
        }

        return jsonObject.getString("openid");
    }
}
