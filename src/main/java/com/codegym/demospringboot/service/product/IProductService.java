package com.codegym.demospringboot.service.product;


import com.codegym.demospringboot.model.entity.Category;
import com.codegym.demospringboot.model.entity.Product;
import com.codegym.demospringboot.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductService extends IGeneralService<Product> {
    Iterable<Product> findProductByNameContaining(String name);

    Iterable<Product> findAllByCategory(Category category);

    Page<Product> findProductByNameContaining(String name, Pageable pageable);

    Page<Product> findAllByCategory(Category category, Pageable pageable);

    Page<Product> findAll(Pageable pageable);
}
