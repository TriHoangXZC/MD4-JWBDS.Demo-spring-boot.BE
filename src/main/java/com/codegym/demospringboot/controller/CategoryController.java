package com.codegym.demospringboot.controller;

import com.codegym.demospringboot.model.entity.Category;
import com.codegym.demospringboot.model.entity.Product;
import com.codegym.demospringboot.service.category.ICategoryService;
import com.codegym.demospringboot.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IProductService productService;

    @GetMapping
    public ResponseEntity<Iterable<Category>> getAllCategory() {
        return new ResponseEntity<>(categoryService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Category> saveCategory(@RequestBody Category category) {
        return new ResponseEntity<>(categoryService.save(category), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> findById(@PathVariable Long id) {
        Optional<Category> categoryOptional = categoryService.findById(id);
        if (!categoryOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(categoryOptional.get(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category newCategory) {
        Optional<Category> categoryOptional = categoryService.findById(id);
        if (!categoryOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        newCategory.setId(id);
        return new ResponseEntity<>(categoryService.save(newCategory), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Category> deleteCategory(@PathVariable Long id) {
        Optional<Category> categoryOptional = categoryService.findById(id);
        if (!categoryOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        categoryService.deleteAllProductByCategory(id);
        return new ResponseEntity<>(categoryOptional.get(), HttpStatus.OK);
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<Iterable<Product>> findAllByCategory(@PathVariable Long id) {
        Optional<Category> categoryOptional = categoryService.findById(id);
        if (!categoryOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Category category = categoryOptional.get();
        Iterable<Product> products = productService.findAllByCategory(category);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
