package com.example.productservice.controller;

import com.example.productservice.entity.Topping;
import com.example.productservice.service.ToppingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/toppings")
public class ToppingController {

    private final ToppingService service;

    public ToppingController(ToppingService service) {
        this.service = service;
    }

    @GetMapping
    public List<Topping> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Topping> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Topping> create(@RequestBody Topping topping) {
        Topping created = service.create(topping);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Topping> update(@PathVariable Long id, @RequestBody Topping toppingDetails) {
        return service.update(id, toppingDetails)
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

