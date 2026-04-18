package com.hhxy.controller;

import com.hhxy.entity.Employee;
import com.hhxy.mapper.EmployeeMapper;
import com.hhxy.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // 学习点：告知 Spring 这是一个接口类，返回的数据会自动转为 JSON
@Slf4j   // 学习点：Lombok 日志注解，有了它你可以直接用 log.info() 打印漂亮的信息
@Tag(name = "测试接口", description = "用于测试接口是否正常工作")  // 接口分组
public class TestController {

    @Autowired // 让 Spring 自动把写好的 Mapper 塞进来
    private EmployeeMapper employeeMapper;

    @Operation(summary = "测试数据库", description = "通过 ID 查询 admin 用户") // 接口描述
    @GetMapping("/test/db")
    public Result<Employee> testDb() {
        // 这就是传说中的“一行代码”：通过 ID 查出我们之前 INSERT 的 admin 用户
        Employee employee = employeeMapper.selectById(1L);
        return Result.success(employee);
    }

    @GetMapping("/hello") // 学习点：定义访问路径。当你在浏览器输入 /hello 时，会触发这个方法
    @Operation(summary = "欢迎接口", description = "返回系统的第一声问候") // 接口描述
    public Result<String> hello() {
        log.info("接收到了浏览器的测试请求！");
        return Result.success("天穹外卖连接成功！2026 开启美味之旅！");
    }


}