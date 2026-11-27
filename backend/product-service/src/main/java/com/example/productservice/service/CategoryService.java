package com.example.productservice.service;

import com.example.productservice.dto.CategoryRequest;
import com.example.productservice.dto.CategoryResponse;
import com.example.productservice.entity.Category;
import com.example.productservice.exception.ResourceNotFoundException;
import com.example.productservice.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    private CategoryResponse toResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }

    public List<CategoryResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public CategoryResponse getById(Long id) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy danh mục có ID: " + id));
        return toResponse(category);
    }

    public CategoryResponse create(CategoryRequest request) {
        Category category = new Category();
        category.setName(request.name());
        category.setDescription(request.description());
        Category saved = repository.save(category);
        return toResponse(saved);
    }

    public CategoryResponse update(Long id, CategoryRequest request) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy danh mục có ID: " + id));
        category.setName(request.name());
        category.setDescription(request.description());
        Category updated = repository.save(category);
        return toResponse(updated);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Không tìm thấy danh mục có ID: " + id);
        }
        repository.deleteById(id);
    }
}
