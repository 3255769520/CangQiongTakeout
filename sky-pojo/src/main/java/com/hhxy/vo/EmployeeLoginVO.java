package com.hhxy.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "员工登录返回的数据格式")
public class EmployeeLoginVO implements Serializable {
    private Long id;

    private String userName;
    private String name;
    private String token; // 2026 年主流：JWT 令牌
}


/*

@Builder
作用: 提供建造者模式，支持链式调用创建对象 使用方式:


java
// 传统方式
EmployeeLoginVO vo = new EmployeeLoginVO();
vo.setId(1L);
vo.setUserName("admin");
vo.setName("管理员");

// 使用@Builder（更优雅）
EmployeeLoginVO vo = EmployeeLoginVO.builder()
        .id(1L)
        .userName("admin")
        .name("管理员")
        .token("jwt-token")
        .build();*/
