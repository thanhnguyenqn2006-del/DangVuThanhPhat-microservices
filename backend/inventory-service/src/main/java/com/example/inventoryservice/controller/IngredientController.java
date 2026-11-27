package com.example.inventoryservice.controller;

import com.example.inventoryservice.entity.Ingredient;
import com.example.inventoryservice.service.IngredientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingredients")
public class IngredientController {

    private final IngredientService service;

    public IngredientController(IngredientService service) {
        this.service = service;
    }

    @GetMapping
    public List<Ingredient> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<Ingredient> getByCode(@PathVariable String code) {
        return service.getByCode(code)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/low-stock")
    public List<Ingredient> getLowStock() {
        return service.getLowStock();
    }

    @PostMapping
    public ResponseEntity<Ingredient> create(@RequestBody Ingredient ingredient) {
        Ingredient created = service.create(ingredient);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ingredient> update(@PathVariable Long id, @RequestBody Ingredient details) {
        return service.update(id, details)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

