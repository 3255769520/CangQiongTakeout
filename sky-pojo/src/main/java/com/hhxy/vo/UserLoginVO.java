package com.hhxy.vo;

import java.io.Serializable;

public record UserLoginVO(Long id, String openid, String token) implements Serializable {}
