package com.hhxy.controller;


import com.hhxy.dto.EmployeeDTO;
import com.hhxy.dto.EmployeeLoginDTO;
import com.hhxy.dto.EmployeePageQueryDTO;
import com.hhxy.entity.Employee;
import com.hhxy.result.PageResult;
import com.hhxy.result.Result;
import com.hhxy.service.CategoryService;
import com.hhxy.service.EmployeeService;
import com.hhxy.utils.JwtUtil;
import com.hhxy.vo.EmployeeLoginVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/admin/employee")
@Tag(name = "员工相关接口")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private CategoryService categoryService;

    // 2026 推荐做法：使用 @Value 或配置属性类读取
    @Value("${sky.jwt.admin-secret-key}")
    private String adminSecretKey;
    @Value("${sky.jwt.admin-ttl}")
    private long adminTtl;
/*
    我们以第一行为例： @Value("${sky.jwt.admin-secret-key}") private String adminSecretKey;

① @Value —— “快递员”
    这个 @ 开头的符号叫注解。它的任务是：去别的地方搬一个值过来，塞进下面的变量里。

            ② "${...}" —— “取件码”
    大括号里的路径 sky.jwt.admin-secret-key 就是取件码。

    程序启动时，它会拿着这个码，跑去一个叫 application.yml 或 application.properties 的文件里找。

    那个文件里可能写着：sky: jwt: admin-secret-key: abc123456。

    快递员发现值是 abc123456，就把它搬回来了。

            ③ private String adminSecretKey; —— “储物柜”
    这是你在 Java 代码里准备好的容器。搬回来的值就存放在这里，供后面的代码（比如生成登录令牌时）使用。

            3. 这两个变量具体是干嘛的？
    这是关于 JWT（登录令牌） 的两个核心设置：

    adminSecretKey (管理员密钥)：

    业务作用：就像是盖章用的“印章”。系统给管理员发登录证件（Token）时，必须用这个密钥“盖个章”。没有这个密钥，别人造出来的证件就是假的。

    adminTtl (管理员令牌有效期)：

    业务作用：TTL 全称是 Time To Live。它规定了管理员登录后，多长时间内不用重新登录。比如设置为 7200 秒（2 小时），过了这个时间，证件就失效了，得重新扫脸/输密码。
    */





    @PostMapping("/login")
    public Result<EmployeeLoginVO>  login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
   /*
   @RequestBody  的作用：告诉 Spring 把前端发送的 JSON 请求体转换成一个 Java 对象（这里是 EmployeeLoginDTO）。
   / 前端发送的JSON请求体
        {
            "username": "admin",
                "password": "123456"
        }

// 被@RequestBody转换为EmployeeLoginDTO对象
        EmployeeLoginDTO dto = new EmployeeLoginDTO();
        dto.setUsername("admin");
        dto.setPassword("123456");
        */

        // 1. 调用 Service 校验身份（之前已经写好的 MD5 逻辑）
        Employee employee = employeeService.login(employeeLoginDTO);

        // 2. 登录成功后，生成真正的 JWT 令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put("empId", employee.getId()); // 把员工 ID 存入令牌

        String token = JwtUtil.createJWT(
                adminSecretKey,
                adminTtl,
                claims
        );

        // 3. 封装 VO 返回给前端
        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }


    @PostMapping
    @Operation(summary = "新增员工")
    public Result save(@RequestBody EmployeeDTO employeeDTO) {
        log.info("新增员工：{}", employeeDTO);
        employeeService.save(employeeDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @Operation(summary = "员工分页查询")
    public Result<PageResult> page(EmployeePageQueryDTO employeePageQueryDTO) {
        log.info("员工分页查询，参数为：{}", employeePageQueryDTO);
        PageResult pageResult = employeeService.pageQuery(employeePageQueryDTO);
        return Result.success(pageResult);
    }


    /**
     * 启用禁用员工账号
     * @param status 状态：1启用 0禁用
     * @param id 员工ID
     * @return
     */
    @PostMapping("/status/{status}")
    @Operation(summary = "启用禁用员工账号")
    public Result startOrStop(@PathVariable Integer status, Long id) {
        log.info("启用禁用员工账号：{},{}", status, id);
        employeeService.startOrStop(status, id);
        return Result.success();
    }




}





/*

package com.hhxy.controller;

import com.hhxy.dto.EmployeeLoginDTO;
import com.hhxy.entity.Employee;
import com.hhxy.result.Result;
import com.hhxy.service.EmployeeService;
import com.hhxy.vo.EmployeeLoginVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/admin/employee")
@Tag(name = "员工相关接口")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    @Operation(summary = "员工登录")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        Employee employee = employeeService.login(employeeLoginDTO);

        // 登录成功后，生成简易 Token（后续我们会升级为正式的 JWT）
        String token = UUID.randomUUID().toString();

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }
}*/
