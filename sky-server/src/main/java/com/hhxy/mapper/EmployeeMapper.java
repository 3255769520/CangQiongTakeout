package com.hhxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hhxy.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper // 告诉 Spring 这是一个映射器
public interface EmployeeMapper extends BaseMapper<Employee> {
    // 这里竟然是空的？没错！BaseMapper 已经帮你写好了 100 多个常用方法
}