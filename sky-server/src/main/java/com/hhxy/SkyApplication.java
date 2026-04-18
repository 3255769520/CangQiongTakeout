package com.hhxy;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j // 学习点：Lombok 日志注解，有了它你可以直接用 log.info() 打印漂亮的信息
@SpringBootApplication // 核心：开启 Spring Boot 自动配置
@EnableTransactionManagement // 学习点：开启事务管理，保证点餐、付钱等操作的原子性
@MapperScan("com.hhxy.mapper")

public class SkyApplication {
    public static void main(String[] args) {
        SpringApplication.run(SkyApplication.class, args);
        log.info("----------------------------------------------------------");
        log.info("🚀 天穹外卖启动成功！欢迎进入 2026 现代开发模式...");
        log.info("----------------------------------------------------------");
    }
}