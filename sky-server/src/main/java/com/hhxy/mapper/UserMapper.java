package com.hhxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hhxy.entity.User;
import org.apache.ibatis.annotations.Mapper;
import java.time.LocalDateTime;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    Integer countByCreateTime(LocalDateTime begin, LocalDateTime end);
}
