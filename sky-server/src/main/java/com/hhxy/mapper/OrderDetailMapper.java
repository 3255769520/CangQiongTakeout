package com.hhxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hhxy.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {
    void insertBatch(List<OrderDetail> orderDetailList);
    List<OrderDetail> getByOrderId(Long orderId);
}