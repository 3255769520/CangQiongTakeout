package com.hhxy.config;

import com.hhxy.properties.AliOssProperties;
import com.hhxy.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@EnableConfigurationProperties(AliOssProperties.class) // 必须开启属性绑定映射
public class OssConfiguration {

    @Bean
    public AliOssUtil aliOssUtil(AliOssProperties aliOssProperties) {
        log.info("2026版：正在初始化阿里云 OSS 工具类...");
        return new AliOssUtil(
                aliOssProperties.endpoint(),
                aliOssProperties.accessKeyId(),
                aliOssProperties.accessKeySecret(),
                aliOssProperties.bucketName()
        );
    }
}