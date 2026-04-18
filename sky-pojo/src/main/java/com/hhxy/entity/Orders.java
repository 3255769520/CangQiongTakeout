package com.hhxy.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Orders implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String number;
    private Integer status;
    private Long userId;
    private Long addressBookId;
    private LocalDateTime orderTime;
    private LocalDateTime checkoutTime;
    private LocalDateTime cancelTime;
    private LocalDateTime deliveryTime;
    private Integer payMethod;
    private BigDecimal amount;
    private Integer payStatus;
    private String remark;
    private String phone;
    private String address;
    String consignee;
    private String cancelReason;
    private String rejectionReason;
    private LocalDateTime estimatedArrivalTime;
    private Integer deliveryStatus;
    private Integer packAmount;
    private Integer tablewareNumber;
    private Integer tablewareStatus;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public static final int PENDING_PAYMENT = 1;
    public static final int UN_PAID = 0;
    public static final int PAID = 1;
    public static final int TO_BE_CONFIRMED = 2;
    public static final int CONFIRMED = 3;
    public static final int CANCELLED = 6;
    public static final int DELIVERY_IN_PROGRESS = 4;
    public static final int COMPLETED = 5;
}