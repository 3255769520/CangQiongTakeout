package com.hhxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.hhxy.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {
    BigDecimal sumTurnoverByDate(LocalDateTime begin, LocalDateTime end);
    List<Map<String, Object>> getTurnoverByDate(LocalDateTime begin, LocalDateTime end);

    @Select("SELECT SUM(amount) FROM orders ${ew.customSqlSegment}")
    BigDecimal sumByCondition(@Param("ew") Wrapper<Orders> wrapper);
}