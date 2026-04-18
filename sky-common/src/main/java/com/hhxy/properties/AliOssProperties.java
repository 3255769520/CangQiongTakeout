package com.hhxy.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "sky.alioss")
public record AliOssProperties(
        String endpoint,
        String accessKeyId,
        String accessKeySecret,
        String bucketName
) {}