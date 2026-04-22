package com.taller.bookstore.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderResponse {
    private Long id;
    private Long userId;
    private String userName;
    private String status;
    private BigDecimal total;
    private LocalDateTime createdAt;
    private List<OrderItemResponse> items;
}