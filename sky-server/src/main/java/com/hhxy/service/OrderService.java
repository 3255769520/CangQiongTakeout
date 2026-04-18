package com.hhxy.service;

import com.hhxy.dto.OrdersCancelDTO;
import com.hhxy.dto.OrdersConfirmDTO;
import com.hhxy.dto.OrdersPageQueryDTO;
import com.hhxy.dto.OrdersPaymentDTO;
import com.hhxy.dto.OrdersRejectionDTO;
import com.hhxy.dto.OrdersSubmitDTO;
import com.hhxy.result.PageResult;
import com.hhxy.vo.OrderPaymentVO;
import com.hhxy.vo.OrderSubmitVO;
import com.hhxy.vo.OrderVO;

public interface OrderService {
    OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);
    OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception;
    void paySuccess(String orderNumber);
    void repetition(Long id);
    PageResult pageQueryUser(int page, int pageSize, Integer status);
    OrderVO getDetails(Long id);
    OrderVO getById(Long id);
    PageResult conditionSearch(OrdersPageQueryDTO dto);
    void confirm(OrdersConfirmDTO ordersConfirmDTO);
    void rejection(OrdersRejectionDTO dto);
    void delivery(Long id);
    void complete(Long id);
    void reminder(Long id);
    void cancelOrder(Long id);
}