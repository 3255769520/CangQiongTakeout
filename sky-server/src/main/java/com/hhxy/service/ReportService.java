package com.hhxy.service;

import com.hhxy.vo.OrderReportVO;
import com.hhxy.vo.TurnoverReportVO;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;

public interface ReportService {
    void exportBusinessData(HttpServletResponse response);
    TurnoverReportVO getTurnoverStatistics(LocalDate begin, LocalDate end);

    /**
     * 根据时间段统计订单相关数据
     * @param begin 开始日期
     * @param end 结束日期
     * @return 订单统计结果
     */
    OrderReportVO getOrderStatistics(LocalDate begin, LocalDate end);
}