package com.hhxy.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;

@Data
@Schema(description = "新增员工时传递的数据模型")
public class EmployeeDTO implements Serializable {

    @Schema(description = "用户名", example = "zhangsan")
    private String username;

    @Schema(description = "姓名", example = "张三")
    private String name;

    @Schema(description = "手机号", example = "13800138000")
    private String phone;

    @Schema(description = "性别", example = "1")
    private String sex;

    @Schema(description = "身份证号", example = "110101199001011234")
    private String idNumber;

}

/*

为什么要多此一举用 DTO？
安全性：前端不需要传 password、createTime、status 这些字段。如果直接用 Entity 接收，恶意用户可能会在 JSON 里带上 status: 1 绕过你的逻辑直接激活账号。

灵活性：如果前端需要把 sex 展示为 "男/女"，但数据库存的是 1/0，我们可以在 DTO 层做转换，而不污染核心实体类。

文档整洁：使用 @Schema 后，Knife4j 生成的接口文档会非常清晰，前端同事一眼就能看出哪些是必填项。*/
