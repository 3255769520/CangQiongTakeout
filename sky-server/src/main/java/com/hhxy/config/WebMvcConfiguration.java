package com.hhxy.config;

import com.hhxy.interceptor.JwtTokenAdminInterceptor;
import com.hhxy.interceptor.JwtTokenUserInterceptor;
import com.hhxy.properties.JwtProperties;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
@Slf4j
@EnableConfigurationProperties(JwtProperties.class)
public class WebMvcConfiguration extends WebMvcConfigurationSupport {

    @Autowired
    private JwtTokenAdminInterceptor jwtTokenAdminInterceptor;

    @Autowired
    private JwtTokenUserInterceptor jwtTokenUserInterceptor;

    protected void addInterceptors(InterceptorRegistry registry) {
        log.info("开始注册 JWT 拦截器...");
        registry.addInterceptor(jwtTokenAdminInterceptor)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/employee/login")
                .excludePathPatterns("/doc.html")
                .excludePathPatterns("/webjars/**")
                .excludePathPatterns("/v3/api-docs/**")
                .excludePathPatterns("/swagger-resources/**");

        registry.addInterceptor(jwtTokenUserInterceptor)
                .addPathPatterns("/user/**")
                .excludePathPatterns("/user/user/login")
                .excludePathPatterns("/user/shop/status");
    }

    /**
     * 2. 设置静态资源映射（这是你目前缺失的关键点）
     * 只有加了这部分，浏览器访问 /doc.html 时，Spring 才知道去 jar 包的路径下找网页
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("开始设置静态资源映射...");
        registry.addResourceHandler("/doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * 3. Knife4j 文档元数据配置
     */
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("天穹外卖系统接口文档")
                        .description("基于 Spring Boot 3.4.1 的全栈外卖解决方案") // 修改版本号更贴合实际
                        .version("v1.0")
                        .contact(new Contact().name("小白开发者").email("your@email.com")));
    }
}




/*
package com.hhxy.config;

import com.hhxy.interceptor.JwtTokenAdminInterceptor;
import com.hhxy.properties.JwtProperties;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;


*/
/*
告诉 Knife4j：你的项目叫什么名字？哪些代码需要生成文档？

Knife4j 能做什么？
自动生成漂亮文档：类似支付宝/微信的 API 文档界面
在线调试：不用打开 Postman，文档里直接测试
接口分组：把用户管理、订单管理分开展示
参数示例：告诉你接口需要传什么数据
返回示例：告诉你接口会返回什么数据

*//*

@Configuration
@Slf4j
@EnableConfigurationProperties(JwtProperties.class) // 👈 增加这一行，明确激活它
public class WebMvcConfiguration extends WebMvcConfigurationSupport {

    @Autowired
    private JwtTokenAdminInterceptor jwtTokenAdminInterceptor;

    */
/**
     * 注册自定义拦截器
     *//*

    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtTokenAdminInterceptor)
                .addPathPatterns("/admin/**")         // 拦截所有管理端接口
                .excludePathPatterns("/admin/employee/login") // 放行登录接口（否则没人能登录）
                .excludePathPatterns("/swagger-ui/**", "/v3/api-docs/**", "/doc.html/**") // 放行Swagger相关请求
                .excludePathPatterns("/webjars/**") // 放行静态资源
                .excludePathPatterns("/swagger-resources/**"); // 放行配置资源

    }


    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("天穹外卖系统接口文档")
                        .description("基于 Spring Boot 4.0 的全栈外卖解决方案")
                        .version("v1.0")
                        .contact(new Contact().name("小白开发者").email("your@email.com")));
    }
}*/
