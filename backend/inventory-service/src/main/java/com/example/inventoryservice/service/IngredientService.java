package com.example.inventoryservice.service;

import com.example.inventoryservice.entity.Ingredient;
import com.example.inventoryservice.repository.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientService {

    private final IngredientRepository repository;

    public IngredientService(IngredientRepository repository) {
        this.repository = repository;
    }

    public List<Ingredient> getAll() {
        return repository.findAll();
    }

    public Optional<Ingredient> getById(Long id) {
        return repository.findById(id);
    }

    public Optional<Ingredient> getByCode(String code) {
        return repository.findByCode(code);
    }

    public List<Ingredient> getLowStock() {
        return repository.findLowStockIngredients();
    }

    public Ingredient create(Ingredient ingredient) {
        if (ingredient.getQuantityInStock() == null) {
            ingredient.setQuantityInStock(0.0);
        }
        if (ingredient.getMinQuantity() == null) {
            ingredient.setMinQuantity(5.0);
        }
        ingredient.updateStatus();
        return repository.save(ingredient);
    }

    public Optional<Ingredient> update(Long id, Ingredient details) {
        return repository.findById(id).map(existing -> {
            existing.setName(details.getName());
            existing.setUnit(details.getUnit());
            if (details.getQuantityInStock() != null) {
                existing.setQuantityInStock(details.getQuantityInStock());
            }
            if (details.getMinQuantity() != null) {
                existing.setMinQuantity(details.getMinQuantity());
            }
            if (details.getUnitPrice() != null) {
                existing.setUnitPrice(details.getUnitPrice());
            }
            existing.updateStatus();
            return repository.save(existing);
        });
    }

    public boolean delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}

