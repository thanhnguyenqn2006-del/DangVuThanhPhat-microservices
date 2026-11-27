package com.example.orderservice.service;

import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.OrderItem;
import com.example.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    public Optional<Order> getById(Long id) {
        return orderRepository.findById(id);
    }

    public Optional<Order> getByOrderCode(String orderCode) {
        return orderRepository.findByOrderCode(orderCode);
    }

    @Transactional
    public Order create(Order order) {
        if (order.getOrderCode() == null || order.getOrderCode().trim().isEmpty()) {
            order.setOrderCode("ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        }
        if (order.getCreatedAt() == null) {
            order.setCreatedAt(LocalDateTime.now());
        }
        if (order.getStatus() == null) {
            order.setStatus("PENDING");
        }
        if (order.getPaymentStatus() == null) {
            order.setPaymentStatus("UNPAID");
        }

        // Tự động tính tổng tiền nếu có danh sách món
        BigDecimal total = BigDecimal.ZERO;
        if (order.getItems() != null && !order.getItems().isEmpty()) {
            for (OrderItem item : order.getItems()) {
                item.setOrder(order);
                if (item.getSubtotal() == null) {
                    BigDecimal unit = item.getUnitPrice() != null ? item.getUnitPrice() : BigDecimal.ZERO;
                    int qty = item.getQuantity() != null ? item.getQuantity() : 1;
                    item.setSubtotal(unit.multiply(BigDecimal.valueOf(qty)));
                }
                total = total.add(item.getSubtotal());
            }
        }
        if (order.getTotalAmount() == null || order.getTotalAmount().compareTo(BigDecimal.ZERO) == 0) {
            order.setTotalAmount(total);
        }

        return orderRepository.save(order);
    }

    @Transactional
    public Optional<Order> updateStatus(Long id, String status, String paymentStatus) {
        return orderRepository.findById(id).map(existing -> {
            if (status != null) {
                existing.setStatus(status);
            }
            if (paymentStatus != null) {
                existing.setPaymentStatus(paymentStatus);
            }
            return orderRepository.save(existing);
        });
    }

    @Transactional
    public Optional<Order> update(Long id, Order orderDetails) {
        return orderRepository.findById(id).map(existing -> {
            existing.setCustomerName(orderDetails.getCustomerName());
            existing.setCustomerPhone(orderDetails.getCustomerPhone());
            existing.setShippingAddress(orderDetails.getShippingAddress());
            existing.setNote(orderDetails.getNote());
            if (orderDetails.getStatus() != null) {
                existing.setStatus(orderDetails.getStatus());
            }
            if (orderDetails.getPaymentMethod() != null) {
                existing.setPaymentMethod(orderDetails.getPaymentMethod());
            }
            if (orderDetails.getPaymentStatus() != null) {
                existing.setPaymentStatus(orderDetails.getPaymentStatus());
            }
            return orderRepository.save(existing);
        });
    }

    @Transactional
    public boolean delete(Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
