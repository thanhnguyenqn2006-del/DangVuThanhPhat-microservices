package com.example.inventoryservice.service;

import com.example.inventoryservice.entity.Ingredient;
import com.example.inventoryservice.entity.StockTransaction;
import com.example.inventoryservice.repository.IngredientRepository;
import com.example.inventoryservice.repository.StockTransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StockTransactionService {

    private final StockTransactionRepository transactionRepository;
    private final IngredientRepository ingredientRepository;

    public StockTransactionService(StockTransactionRepository transactionRepository, IngredientRepository ingredientRepository) {
        this.transactionRepository = transactionRepository;
        this.ingredientRepository = ingredientRepository;
    }

    public List<StockTransaction> getAll() {
        return transactionRepository.findAll();
    }

    public List<StockTransaction> getByIngredientId(Long ingredientId) {
        return transactionRepository.findByIngredientIdOrderByCreatedAtDesc(ingredientId);
    }

    @Transactional
    public StockTransaction createTransaction(Long ingredientId, String type, Double quantity, java.math.BigDecimal unitPrice, String note) {
        Ingredient ingredient = ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new IllegalArgumentException("Khong tim thay nguyen lieu co ID: " + ingredientId));

        if ("IMPORT".equalsIgnoreCase(type)) {
            ingredient.setQuantityInStock(ingredient.getQuantityInStock() + quantity);
            if (unitPrice != null) {
                ingredient.setUnitPrice(unitPrice);
            }
        } else if ("EXPORT".equalsIgnoreCase(type)) {
            if (ingredient.getQuantityInStock() < quantity) {
                throw new IllegalStateException("Ton kho khong du de xuat! Hien con: " + ingredient.getQuantityInStock());
            }
            ingredient.setQuantityInStock(ingredient.getQuantityInStock() - quantity);
        } else {
            throw new IllegalArgumentException("Loai giao dich khong hop le (chi chap nhan IMPORT hoac EXPORT)");
        }

        ingredient.updateStatus();
        ingredientRepository.save(ingredient);

        StockTransaction transaction = new StockTransaction();
        transaction.setIngredient(ingredient);
        transaction.setTransactionType(type.toUpperCase());
        transaction.setQuantity(quantity);
        transaction.setUnitPrice(unitPrice != null ? unitPrice : ingredient.getUnitPrice());
        transaction.setNote(note);
        transaction.setCreatedAt(LocalDateTime.now());

        return transactionRepository.save(transaction);
    }
}

