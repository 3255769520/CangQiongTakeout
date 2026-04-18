package com.hhxy.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis Plus 配置类
 */
@Configuration
public class MybatisPlusConfiguration {

    /**
     * 定义 MyBatis Plus 拦截器 Bean
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        
        // 1. 添加分页插件 (必须指定数据库类型为 MYSQL)
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);
        
        // 2. 设置分页合理化 (可选：如果查询页码 > 总页数，是否自动返回最后一页)
        paginationInnerInterceptor.setOverflow(true);
        
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        return interceptor;
    }
}