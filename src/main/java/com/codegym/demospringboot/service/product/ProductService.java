package com.codegym.demospringboot.service.product;

import com.codegym.demospringboot.model.entity.Category;
import com.codegym.demospringboot.model.entity.Product;
import com.codegym.demospringboot.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService implements IProductService {
    @Autowired
    private IProductRepository productRepository;

    @Override
    public Iterable<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void removeById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Iterable<Product> findProductByNameContaining(String name) {
        return productRepository.findByNameContaining(name);
    }

    @Override
    public Iterable<Product> findAllByCategory(Category category) {
        return productRepository.findAllByCategory(category);
    }

    @Override
    public Page<Product> findProductByNameContaining(String name, Pageable pageable) {
        return productRepository.findProductByNameContaining(name, pageable);
    }

    @Override
    public Page<Product> findAllByCategory(Category category, Pageable pageable) {
        return productRepository.findAllByCategory(category, pageable);
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

}
