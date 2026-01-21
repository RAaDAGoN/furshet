package com.api.furshet.controller;

import com.api.furshet.domain.entity.Category;
import com.api.furshet.dto.ProductDTO;
import com.api.furshet.domain.entity.Product;
import com.api.furshet.service.CategoryService;
import com.api.furshet.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<List<Product>> create(@RequestPart("products") List<ProductDTO> dtolist,
                                                @RequestParam List<MultipartFile> file){
        List<Product> products = dtolist.stream()
                .map(dto -> productService.create(dto, file))
                .collect(Collectors.toList());
        return new ResponseEntity<>(products, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<Product>> readByCategoryId(@PathVariable Long id) {
        Category category = categoryService.findById(id);
        if(category == null || Boolean.FALSE.equals(category.getActive())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Получаем только активные продукты
        List<Product> activeProducts = productService.findByCategoryIdAndActiveTrue(id);

        return ResponseEntity.ok(activeProducts);
    }

    @DeleteMapping("/{id}")
    public HttpStatus delete(@PathVariable Long id) {
        productService.delete(id);
        return HttpStatus.OK;
    }
}
