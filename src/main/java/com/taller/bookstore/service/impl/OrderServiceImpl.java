package com.taller.bookstore.service.impl;

import com.taller.bookstore.dto.request.OrderItemRequest;
import com.taller.bookstore.dto.request.OrderRequest;
import com.taller.bookstore.dto.response.OrderResponse;
import com.taller.bookstore.entity.*;
import com.taller.bookstore.exception.custom.*;
import com.taller.bookstore.mapper.OrderMapper;
import com.taller.bookstore.repository.*;
import com.taller.bookstore.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderResponse create(OrderRequest request, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + userEmail));

        List<OrderItem> items = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (OrderItemRequest itemReq : request.getItems()) {
            Book book = bookRepository.findById(itemReq.getBookId())
                    .orElseThrow(() -> new ResourceNotFoundException("Book", itemReq.getBookId()));

            if (book.getStock() < itemReq.getQuantity()) {
                throw new InsufficientStockException(book.getTitle(), book.getStock());
            }

            BigDecimal subtotal = book.getPrice().multiply(BigDecimal.valueOf(itemReq.getQuantity()));
            total = total.add(subtotal);

            book.setStock(book.getStock() - itemReq.getQuantity());
            bookRepository.save(book);

            OrderItem item = OrderItem.builder()
                    .book(book)
                    .quantity(itemReq.getQuantity())
                    .subtotal(subtotal)
                    .build();
            items.add(item);
        }

        Order order = Order.builder()
                .user(user)
                .status(OrderStatus.PENDING)
                .total(total)
                .build();

        Order savedOrder = orderRepository.save(order);

        items.forEach(item -> item.setOrder(savedOrder));
        savedOrder.setItems(items);

        Order finalOrder = orderRepository.save(savedOrder);
        return orderMapper.toResponse(finalOrder);
    }

    @Override
    public List<OrderResponse> getMyOrders(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + userEmail));
        return orderRepository.findByUserId(user.getId())
                .stream()
                .map(orderMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAllWithDetails()
                .stream()
                .map(orderMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderResponse cancelOrder(Long orderId, String userEmail) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", orderId));

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + userEmail));

        if (!order.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedAccessException();
        }

        if (order.getStatus() == OrderStatus.CONFIRMED) {
            throw new InvalidOrderStateException("No se puede cancelar un pedido ya confirmado");
        }

        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new InvalidOrderStateException("El pedido ya está cancelado");
        }

        order.setStatus(OrderStatus.CANCELLED);
        return orderMapper.toResponse(orderRepository.save(order));
    }
}