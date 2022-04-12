package com.codegym.demospringboot.model.dto;

import com.codegym.demospringboot.model.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductForm {
    private Long id;

    @NotEmpty(message = "Not blank")
    @Size(min = 5, max = 20)
    private String name;

    private double price;

    private String description;

    private MultipartFile image;

    private Category category;
}
