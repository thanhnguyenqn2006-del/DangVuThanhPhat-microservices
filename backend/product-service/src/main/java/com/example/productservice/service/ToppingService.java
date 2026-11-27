package com.example.productservice.service;

import com.example.productservice.dto.ToppingRequest;
import com.example.productservice.dto.ToppingResponse;
import com.example.productservice.entity.Topping;
import com.example.productservice.exception.ResourceNotFoundException;
import com.example.productservice.repository.ToppingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToppingService {

    private final ToppingRepository repository;

    public ToppingService(ToppingRepository repository) {
        this.repository = repository;
    }

    private ToppingResponse toResponse(Topping topping) {
        return new ToppingResponse(
                topping.getId(),
                topping.getName(),
                topping.getPrice(),
                topping.getStatus()
        );
    }

    public List<ToppingResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ToppingResponse getById(Long id) {
        Topping topping = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy topping có ID: " + id));
        return toResponse(topping);
    }

    public ToppingResponse create(ToppingRequest request) {
        Topping topping = new Topping();
        topping.setName(request.name());
        topping.setPrice(request.price());
        topping.setStatus(request.status() != null ? request.status() : "AVAILABLE");
        Topping saved = repository.save(topping);
        return toResponse(saved);
    }

    public ToppingResponse update(Long id, ToppingRequest request) {
        Topping topping = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy topping có ID: " + id));
        topping.setName(request.name());
        topping.setPrice(request.price());
        if (request.status() != null) {
            topping.setStatus(request.status());
        }
        Topping updated = repository.save(topping);
        return toResponse(updated);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Không tìm thấy topping có ID: " + id);
        }
        repository.deleteById(id);
    }
}
