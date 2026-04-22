package com.taller.bookstore.mapper;

import com.taller.bookstore.dto.response.OrderResponse;
import com.taller.bookstore.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final OrderItemMapper orderItemMapper;

    public OrderResponse toResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUser().getId())
                .userName(order.getUser().getName())
                .status(order.getStatus().name())
                .total(order.getTotal())
                .createdAt(order.getCreatedAt())
                .items(order.getItems() != null
                        ? order.getItems().stream()
                        .map(orderItemMapper::toResponse)
                        .collect(Collectors.toList())
                        : Collections.emptyList())
                .build();
    }
}