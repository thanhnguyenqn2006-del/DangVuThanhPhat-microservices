package com.example.inventoryservice.repository;

import com.example.inventoryservice.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    Optional<Ingredient> findByCode(String code);

    @Query("SELECT i FROM Ingredient i WHERE i.quantityInStock <= i.minQuantity")
    List<Ingredient> findLowStockIngredients();
}

