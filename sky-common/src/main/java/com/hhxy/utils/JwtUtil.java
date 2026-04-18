package com.hhxy.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

public class JwtUtil {
    /**
     * 生成jwt
     */
    public static String createJWT(String secretKey, long ttlMillis, Map<String, Object> claims) {
        // 1. 根据密钥生成安全密钥对象
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        // 2. 计算过期时间
        long expMillis = System.currentTimeMillis() + ttlMillis;
        Date exp = new Date(expMillis);

        // 3. 构建 JWT (0.12.x 新语法)
        return Jwts.builder()
                .claims(claims)          // 设置载荷
                .expiration(exp)         // 设置过期时间
                .signWith(key)           // 自动识别 HS256 算法
                .compact();
    }


    // 在 JwtUtil 类中添加
    public static Claims parseJWT(String secretKey, String token) {
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        return Jwts.parser()
                .verifyWith(key) // 2026 版新语法
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}