package com.hhxy.service;

import com.hhxy.dto.EmployeeDTO;
import com.hhxy.dto.EmployeeLoginDTO;
import com.hhxy.dto.EmployeePageQueryDTO;
import com.hhxy.entity.Employee;
import com.hhxy.result.PageResult;

public interface EmployeeService {
    Employee login(EmployeeLoginDTO employeeLoginDTO);

     void save(EmployeeDTO employeeDTO);

    PageResult pageQuery(EmployeePageQueryDTO dto);

    /**
     * 启用禁用员工账号
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);
}

/*

1. 解耦：制定“契约”
接口的本质是一份契约（Contract）。

Controller（调用方）：它只关心“我要一个能登录、能注册的东西”，它不关心你怎么实现。

Service（实现方）：它负责具体的逻辑（查数据库、校验密码等）。

这样做的好处是：如果有一天你的 login 逻辑要从“数据库校验”改为“调用第三方 LDAP”或“双因子验证”，你只需要增加一个新的实现类，而完全不需要改动 Controller 的代码。

        2. Spring AOP 与 动态代理
这是技术层面的核心原因。Spring 的很多高级功能（比如 @Transactional 事务管理、日志记录、权限校验）都是基于 AOP（面向切面编程） 实现的。

Spring 默认使用 JDK 动态代理，这种代理方式必须要有接口才能工作。

如果没有接口，Spring 只能改用 CGLIB。虽然现在 Spring Boot 对 CGLIB 支持很好，但在早期的 Java 体系中，接口代理是更标准、性能更优的选择。

        3. 为了方便测试 (Mocking)
当你编写单元测试时，如果你想测试 Controller 的逻辑，但不想真的去连接数据库：

如果有接口，你可以非常轻松地用工具（如 Mockito）模拟（Mock）出一个实现类，返回一个假数据。

如果没有接口，强耦合的类结构会让自动化测试变得非常痛苦。

        4. 团队协作的“并行”利器
在一个大项目中，往往是“你写 Controller，我写 Service”。

我们先把 EmployeeService 接口定义好。

你可以直接拿着这个接口去写你的 Controller 逻辑（哪怕我还没写具体实现）。

我慢慢写我的实现类。 如果没有接口，你必须等我把整个类写完，你才能开始调用。

总结：什么时候可以不写接口？
虽然它是“最佳实践”，但并不代表它是“法律”。

建议写接口的情况： 中大型项目、需要事务管理、需要多人协作、逻辑复杂的业务。

可以不写接口的情况： 个人小练习、极简的微服务、纯粹的“增删改查”且确定以后不会有多种实现方式。

比喻： 接口就像是墙上的插座标准。插座（接口）定义了孔位，至于后面连的是火电、水电还是风电（实现类），你的电器（Controller）并不关心，只要插头对得上就能用。*/
