package com.codegym.demospringboot.service.category;


import com.codegym.demospringboot.model.entity.Category;
import com.codegym.demospringboot.service.IGeneralService;

public interface ICategoryService extends IGeneralService<Category> {
    void deleteAllProductByCategory(Long category_id);
}
