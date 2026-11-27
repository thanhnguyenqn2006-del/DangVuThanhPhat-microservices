package com.example.productservice.controller;

import com.example.productservice.dto.ToppingRequest;
import com.example.productservice.dto.ToppingResponse;
import com.example.productservice.service.ToppingService;
import jakarta.validation.Valid;
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
    public List<ToppingResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ToppingResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public ResponseEntity<ToppingResponse> create(@Valid @RequestBody ToppingRequest request) {
        ToppingResponse created = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ToppingResponse> update(@PathVariable Long id, @Valid @RequestBody ToppingRequest request) {
        ToppingResponse updated = service.update(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
