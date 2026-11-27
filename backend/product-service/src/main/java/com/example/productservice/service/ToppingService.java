package com.example.productservice.service;

import com.example.productservice.entity.Topping;
import com.example.productservice.repository.ToppingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ToppingService {

    private final ToppingRepository repository;

    public ToppingService(ToppingRepository repository) {
        this.repository = repository;
    }

    public List<Topping> getAll() {
        return repository.findAll();
    }

    public Optional<Topping> getById(Long id) {
        return repository.findById(id);
    }

    public Topping create(Topping topping) {
        return repository.save(topping);
    }

    public Optional<Topping> update(Long id, Topping toppingDetails) {
        return repository.findById(id).map(existing -> {
            existing.setName(toppingDetails.getName());
            existing.setPrice(toppingDetails.getPrice());
            if (toppingDetails.getStatus() != null) {
                existing.setStatus(toppingDetails.getStatus());
            }
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
