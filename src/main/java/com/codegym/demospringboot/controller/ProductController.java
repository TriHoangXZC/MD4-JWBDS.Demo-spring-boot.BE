package com.codegym.demospringboot.controller;

import com.codegym.demospringboot.model.entity.Product;
import com.codegym.demospringboot.model.dto.ProductForm;
import com.codegym.demospringboot.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private IProductService productService;

    @Value("${file-upload}")
    private String uploadPath;

//    @GetMapping
//    public ResponseEntity<Iterable<Product>> findAll(@RequestParam(name = "q") Optional<String> q) {
//        Iterable<Product> products = productService.findAll();
//        if (q.isPresent()) {
//            products = productService.findProductByNameContaining(q.get());
//        }
//        return new ResponseEntity<>(products, HttpStatus.OK);
//    }

    @GetMapping
    public ResponseEntity<Page<Product>> findAll(@RequestParam(name = "q") Optional<String> q, @PageableDefault (value = 5) Pageable pageable) {
        Page<Product> products = productService.findAll(pageable);
        if (q.isPresent()) {
            products = productService.findProductByNameContaining(q.get(), pageable);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id) {
        Optional<Product> productOptional = productService.findById(id);
        if (!productOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productOptional.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Product> save(@ModelAttribute ProductForm productForm) {
        MultipartFile image = productForm.getImage();
        if (image.getSize() != 0) {
            String fileName = productForm.getImage().getOriginalFilename();
            long currentTime = System.currentTimeMillis();
            fileName = currentTime + fileName;
            try {
                FileCopyUtils.copy(productForm.getImage().getBytes(), new File(uploadPath + fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Product product = new Product(productForm.getId(), productForm.getName(), productForm.getPrice(), productForm.getDescription(), fileName, productForm.getCategory());
            return new ResponseEntity<>(productService.save(product), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @ModelAttribute ProductForm newProduct) {
        Optional<Product> productOptional = productService.findById(id);
        if (!productOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Product oldProduct = productOptional.get();
        MultipartFile image = newProduct.getImage();
        if (image != null) {
            String fileName = image.getOriginalFilename();
            long currentTime = System.currentTimeMillis();
            fileName = currentTime + fileName;
            oldProduct.setImage(fileName);
            try {
                FileCopyUtils.copy(image.getBytes(), new File(uploadPath + fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        oldProduct.setName(newProduct.getName());
        oldProduct.setPrice(newProduct.getPrice());
        oldProduct.setDescription(newProduct.getDescription());
        oldProduct.setCategory(newProduct.getCategory());
        return new ResponseEntity<>(productService.save(oldProduct), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable Long id) {
        Optional<Product> productOptional = productService.findById(id);
        if (!productOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        productService.removeById(id);
        return new ResponseEntity<>(productOptional.get(), HttpStatus.OK);
    }
}
