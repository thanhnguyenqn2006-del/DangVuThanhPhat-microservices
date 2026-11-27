package com.example.productservice.service;

import com.example.productservice.entity.Category;
import com.example.productservice.entity.Product;
import com.example.productservice.repository.CategoryRepository;
import com.example.productservice.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Optional<Product> getById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> getByCategoryId(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    public Product create(Product product) {
        if (product.getCategory() != null && product.getCategory().getId() != null) {
            Category category = categoryRepository.findById(product.getCategory().getId()).orElse(null);
            product.setCategory(category);
        }
        return productRepository.save(product);
    }

    public Optional<Product> update(Long id, Product productDetails) {
        return productRepository.findById(id).map(existing -> {
            existing.setName(productDetails.getName());
            existing.setDescription(productDetails.getDescription());
            existing.setPrice(productDetails.getPrice());
            existing.setImageUrl(productDetails.getImageUrl());
            if (productDetails.getStatus() != null) {
                existing.setStatus(productDetails.getStatus());
            }
            if (productDetails.getCategory() != null && productDetails.getCategory().getId() != null) {
                Category category = categoryRepository.findById(productDetails.getCategory().getId()).orElse(null);
                existing.setCategory(category);
            }
            return productRepository.save(existing);
        });
    }

    public boolean delete(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

