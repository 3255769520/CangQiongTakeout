package com.hhxy.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@ConfigurationProperties(prefix = "sky.jwt") // 对应 application.yml 中的 sky.jwt 层级
@Data
public class JwtProperties {

    /**
     * 管理端员工生成JWT令牌相关配置
     */
    private String adminSecretKey;
    private long adminTtl;
    private String adminTokenName;

    /**
     * 用户端微信用户生成JWT令牌相关配置（后续会用到）
     */
    private String userSecretKey;
    private long userTtl;
    private String userTokenName;

}