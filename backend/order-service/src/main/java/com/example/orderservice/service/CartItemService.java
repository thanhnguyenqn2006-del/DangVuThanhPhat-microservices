package com.example.orderservice.service;

import com.example.orderservice.entity.CartItem;
import com.example.orderservice.repository.CartItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CartItemService {

    private final CartItemRepository repository;

    public CartItemService(CartItemRepository repository) {
        this.repository = repository;
    }

    public List<CartItem> getBySession(String customerSession) {
        return repository.findByCustomerSession(customerSession);
    }

    public CartItem addItem(CartItem item) {
        if (item.getQuantity() == null || item.getQuantity() < 1) {
            item.setQuantity(1);
        }
        BigDecimal unit = item.getUnitPrice() != null ? item.getUnitPrice() : BigDecimal.ZERO;
        item.setTotalPrice(unit.multiply(BigDecimal.valueOf(item.getQuantity())));
        return repository.save(item);
    }

    public Optional<CartItem> updateQuantity(Long id, int quantity) {
        return repository.findById(id).map(existing -> {
            existing.setQuantity(quantity);
            BigDecimal unit = existing.getUnitPrice() != null ? existing.getUnitPrice() : BigDecimal.ZERO;
            existing.setTotalPrice(unit.multiply(BigDecimal.valueOf(quantity)));
            return repository.save(existing);
        });
    }

    public boolean deleteItem(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public void clearCart(String customerSession) {
        repository.deleteByCustomerSession(customerSession);
    }
}
