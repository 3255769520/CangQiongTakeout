package com.hhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hhxy.entity.Orders;
import com.hhxy.mapper.OrdersMapper;
import com.hhxy.service.ReportService;
import com.hhxy.service.WorkspaceService;
import com.hhxy.vo.BusinessDataVO;
import com.hhxy.vo.OrderReportVO;
import com.hhxy.vo.TurnoverReportVO;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ReportServiceImpl implements ReportService {

    @Autowired
    private WorkspaceService workspaceService;
    @Autowired
    private OrdersMapper ordersMapper;

    @Override
    public void exportBusinessData(HttpServletResponse response) {
        LocalDate dateBegin = LocalDate.now().minusDays(30);
        LocalDate dateEnd = LocalDate.now().minusDays(1);

        BusinessDataVO businessData = workspaceService.getBusinessData(
                LocalDateTime.of(dateBegin, LocalTime.MIN),
                LocalDateTime.of(dateEnd, LocalTime.MAX));

        InputStream in = this.getClass().getClassLoader().getResourceAsStream("template/运营数据报表模板.xlsx");

        try {
            XSSFWorkbook excel = new XSSFWorkbook(in);
            XSSFSheet sheet = excel.getSheet("Sheet1");

            sheet.getRow(3).getCell(2).setCellValue(dateBegin + "至" + dateEnd);
            sheet.getRow(4).getCell(2).setCellValue(businessData.turnover().doubleValue());
            sheet.getRow(4).getCell(4).setCellValue(businessData.orderCompletionRate().doubleValue());
            sheet.getRow(4).getCell(6).setCellValue(businessData.newUsers());

            for (int i = 0; i < 30; i++) {
                LocalDate date = dateBegin.plusDays(i);
                BusinessDataVO todayData = workspaceService.getBusinessData(
                        LocalDateTime.of(date, LocalTime.MIN),
                        LocalDateTime.of(date, LocalTime.MAX));

                XSSFRow row = sheet.getRow(7 + i);
                row.getCell(1).setCellValue(date.toString());
                row.getCell(2).setCellValue(todayData.turnover() != null ? todayData.turnover().doubleValue() : 0.0);
            }

            ServletOutputStream out = response.getOutputStream();
            response.setContentType("application/vnd.ms-excel");
            excel.write(out);

            out.close();
            excel.close();
        } catch (IOException e) {
            log.error("报表导出异常：{}", e.getMessage());
        }
    }

    @Override
    public TurnoverReportVO getTurnoverStatistics(LocalDate begin, LocalDate end) {
        List<LocalDate> dateList = new ArrayList<>();
        List<BigDecimal> turnoverList = new ArrayList<>();

        LocalDate current = begin;
        while (!current.isAfter(end)) {
            dateList.add(current);

            LocalDateTime beginTime = LocalDateTime.of(current, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(current, LocalTime.MAX);

            BigDecimal dayTurnover = ordersMapper.sumByCondition(new LambdaQueryWrapper<Orders>()
                    .eq(Orders::getStatus, 5)
                    .ge(Orders::getOrderTime, beginTime)
                    .le(Orders::getOrderTime, endTime));

            turnoverList.add(dayTurnover == null ? BigDecimal.ZERO : dayTurnover);

            current = current.plusDays(1);
        }

        return TurnoverReportVO.builder()
                .dateList(StringUtils.join(dateList, ","))
                .turnoverList(StringUtils.join(turnoverList, ","))
                .build();
    }

    @Override
    public OrderReportVO getOrderStatistics(LocalDate begin, LocalDate end) {
        // 增加校验，防止 begin 或 end 为空
        if (begin == null || end == null) {
            log.error("报表查询失败，开始日期或结束日期为空！begin: {}, end: {}", begin, end);
            throw new RuntimeException("查询日期不能为空");
        }

        List<LocalDate> dateList = new ArrayList<>();
        List<Integer> orderCountList = new ArrayList<>();
        List<Integer> validOrderCountList = new ArrayList<>();

        int totalOrderCount = 0;
        int validOrderCount = 0;

        LocalDate current = begin;
        while (!current.isAfter(end)) {
            dateList.add(current);

            LocalDateTime beginTime = LocalDateTime.of(current, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(current, LocalTime.MAX);

            // 查询每日订单总数
            Long orderCount = ordersMapper.selectCount(new LambdaQueryWrapper<Orders>()
                    .ge(Orders::getOrderTime, beginTime)
                    .le(Orders::getOrderTime, endTime));

            // 查询每日有效订单数（已完成）
            Long validOrder = ordersMapper.selectCount(new LambdaQueryWrapper<Orders>()
                    .eq(Orders::getStatus, Orders.COMPLETED)
                    .ge(Orders::getOrderTime, beginTime)
                    .le(Orders::getOrderTime, endTime));

            orderCountList.add(orderCount.intValue());
            validOrderCountList.add(validOrder.intValue());

            totalOrderCount += orderCount.intValue();
            validOrderCount += validOrder.intValue();

            current = current.plusDays(1);
        }

        // 计算订单完成率
        Double orderCompletionRate = totalOrderCount == 0 ? 0.0 :
                (double) validOrderCount / totalOrderCount;

        return OrderReportVO.builder()
                .dateList(StringUtils.join(dateList, ","))
                .orderCountList(StringUtils.join(orderCountList, ","))
                .validOrderCountList(StringUtils.join(validOrderCountList, ","))
                .totalOrderCount(totalOrderCount)
                .validOrderCount(validOrderCount)
                .orderCompletionRate(orderCompletionRate)
                .build();
    }
}