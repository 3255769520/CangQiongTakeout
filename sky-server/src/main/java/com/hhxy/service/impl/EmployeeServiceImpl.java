package com.hhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hhxy.constant.PasswordConstant;
import com.hhxy.constant.StatusConstant;
import com.hhxy.dto.EmployeeDTO;
import com.hhxy.dto.EmployeeLoginDTO;
import com.hhxy.dto.EmployeePageQueryDTO;
import com.hhxy.entity.Employee;
import com.hhxy.exception.AccountNotFoundException;
import com.hhxy.mapper.EmployeeMapper;
import com.hhxy.result.PageResult;
import com.hhxy.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();




        // 1. 根据用户名查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, username);
        Employee employee = employeeMapper.selectOne(queryWrapper);

     /*   // 修改前
        if (employee == null) {
            throw new RuntimeException("账号不存在");
        }
*/
// 修改后（更专业，更清晰）
        if (employee == null) {
            throw new AccountNotFoundException("对不起，您的账号不存在");
        }
        // 3. 密码比对（对前端传来的明文进行 MD5 加密后再对比）
        // 2026 年虽然有更强的加密，但 MD5 依然是理解加密流程的基础
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(employee.getPassword())) {
            throw new RuntimeException("密码错误");
        }

        if (employee.getStatus() == 0) {
            throw new RuntimeException("账号已锁定");
        }

        return employee;


    }

    @Override
    public void save(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        // 对象属性拷贝 (将 DTO 转为 Entity)
        BeanUtils.copyProperties(employeeDTO, employee);

        // 设置默认状态 (1启用，0禁用)，手动设置这个就行
        employee.setStatus(StatusConstant.ENABLE);

        // 设置默认密码 (比如 123456)，需要 MD5 加密
        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));

        // 【高能预警】你发现了吗？我们没有写 employee.setCreateTime(...)
        // 也没有写 employee.setCreateUser(...)
        employeeMapper.insert(employee);
    }

    @Override
    public PageResult pageQuery(EmployeePageQueryDTO dto) {
        // 1. 构建分页对象 (MyBatis Plus 的 Page)
        Page<Employee> pageInfo = new Page<>(dto.page(), dto.pageSize());

        // 2. 构建查询条件：Lambda 表达式避免硬编码字符串
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(dto.name()), Employee::getName, dto.name())
                .orderByDesc(Employee::getUpdateTime);

        // 3. 执行查询
        employeeMapper.selectPage(pageInfo, queryWrapper);

        // 4. 封装结果
        return new PageResult(pageInfo.getTotal(), pageInfo.getRecords());
    }


    @Override
    public void startOrStop(Integer status, Long id) {
        // 2026 版写法：直接构建实体对象，仅设置需要修改的字段
        Employee employee = new Employee();
        employee.setId(id);
        employee.setStatus(status);

        // 重点：updateById 会根据主键 ID 进行更新
        // 此时会触发 MyMetaObjectHandler 中的 updateFill 方法！
        employeeMapper.updateById(employee);
    }

}