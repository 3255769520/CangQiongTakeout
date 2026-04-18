package com.hhxy.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhxy.entity.OrderDetail;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "订单详情")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderVO implements Serializable {
    @Schema(description = "订单id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    @Schema(description = "订单号")
    private String number;
    @Schema(description = "订单状态")
    private Integer status;
    @Schema(description = "下单时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderTime;
    @Schema(description = "地址")
    private String address;
    @Schema(description = "收货人")
    private String consignee;
    @Schema(description = "手机号")
    private String phone;
    @Schema(description = "订单明细")
    private List<OrderDetail> orderDetailList;
    @Schema(description = "用户id")
    private Long userId;
    @Schema(description = "地址簿id")
    private Long addressBookId;
    @Schema(description = "结账时间")
    private LocalDateTime checkoutTime;
    @Schema(description = "支付方式")
    private Integer payMethod;
    @Schema(description = "支付状态")
    private Integer payStatus;
    @Schema(description = "订单金额")
    private BigDecimal amount;
    @Schema(description = "备注")
    private String remark;
    @Schema(description = "取消原因")
    private String cancelReason;
    @Schema(description = "拒单原因")
    private String rejectionReason;
    @Schema(description = "取消时间")
    private LocalDateTime cancelTime;
    @Schema(description = "预计送达时间")
    private LocalDateTime estimatedArrivalTime;
    @Schema(description = "配送状态")
    private Integer deliveryStatus;
    @Schema(description = "送达时间")
    private LocalDateTime deliveryTime;
    @Schema(description = "打包数量")
    private Integer packAmount;
    @Schema(description = "餐具数量")
    private Integer tablewareNumber;
    @Schema(description = "餐具状态")
    private Integer tablewareStatus;
}