package com.example.inventoryservice.controller;

import com.example.inventoryservice.entity.StockTransaction;
import com.example.inventoryservice.service.StockTransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stock-transactions")
public class StockTransactionController {

    private final StockTransactionService service;

    public StockTransactionController(StockTransactionService service) {
        this.service = service;
    }

    @GetMapping
    public List<StockTransaction> getAll() {
        return service.getAll();
    }

    @GetMapping("/ingredient/{ingredientId}")
    public List<StockTransaction> getByIngredient(@PathVariable Long ingredientId) {
        return service.getByIngredientId(ingredientId);
    }

    @PostMapping
    public ResponseEntity<?> createTransaction(@RequestBody Map<String, Object> payload) {
        try {
            Long ingredientId = Long.valueOf(payload.get("ingredientId").toString());
            String type = payload.get("transactionType").toString();
            Double quantity = Double.valueOf(payload.get("quantity").toString());
            BigDecimal unitPrice = payload.get("unitPrice") != null ? new BigDecimal(payload.get("unitPrice").toString()) : null;
            String note = payload.get("note") != null ? payload.get("note").toString() : "";

            StockTransaction transaction = service.createTransaction(ingredientId, type, quantity, unitPrice, note);
            return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}

