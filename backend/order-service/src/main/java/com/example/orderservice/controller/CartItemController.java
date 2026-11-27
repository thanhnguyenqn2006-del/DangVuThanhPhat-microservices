package com.example.orderservice.controller;

import com.example.orderservice.entity.CartItem;
import com.example.orderservice.service.CartItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
public class CartItemController {

    private final CartItemService service;

    public CartItemController(CartItemService service) {
        this.service = service;
    }

    @GetMapping("/{customerSession}")
    public List<CartItem> getCart(@PathVariable String customerSession) {
        return service.getBySession(customerSession);
    }

    @PostMapping
    public ResponseEntity<CartItem> addToCart(@RequestBody CartItem item) {
        CartItem added = service.addItem(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(added);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartItem> updateQuantity(@PathVariable Long id, @RequestBody Map<String, Integer> payload) {
        Integer quantity = payload.get("quantity");
        if (quantity == null || quantity < 1) {
            quantity = 1;
        }
        return service.updateQuantity(id, quantity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        if (service.deleteItem(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/session/{customerSession}")
    public ResponseEntity<Void> clearCart(@PathVariable String customerSession) {
        service.clearCart(customerSession);
        return ResponseEntity.noContent().build();
    }
}
