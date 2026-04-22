package com.taller.bookstore.service;

import com.taller.bookstore.dto.request.OrderRequest;
import com.taller.bookstore.dto.response.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse create(OrderRequest request, String userEmail);
    List<OrderResponse> getMyOrders(String userEmail);
    List<OrderResponse> getAllOrders();
    OrderResponse cancelOrder(Long orderId, String userEmail);
}