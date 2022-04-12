package com.codegym.demospringboot.repository;

import com.codegym.demospringboot.model.entity.Category;
import com.codegym.demospringboot.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {
    Iterable<Product> findByNameContaining(String name);

    Page<Product> findProductByNameContaining(String name, Pageable pageable);

    Page<Product> findAllByCategory(Category category, Pageable pageable);

    Iterable<Product> findAllByCategory(Category category);
}
