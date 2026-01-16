package com.api.furshet.controller;

import com.api.furshet.dto.ProductDTO;
import com.api.furshet.domain.entity.Product;
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




//    @PostMapping
//    public ResponseEntity<Product> create(@RequestBody ProductDTO dto) {
//        return new ResponseEntity<>(productService.create(dto), HttpStatus.OK);
//    }

//    @PostMapping
//    public ResponseEntity<List<Product>> create(@RequestBody List<ProductDTO> dto) {
//        List<Product> products = dto.stream()
//                .map(productService::create)
//                .collect(Collectors.toList());
//        return new ResponseEntity<>(products, HttpStatus.CREATED);
//    }

    @PostMapping
    public ResponseEntity<List<Product>> create(@RequestPart("products") List<ProductDTO> dtolist,
                                                @RequestPart("file") MultipartFile file){
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
        return new ResponseEntity<>(productService.findByCategoryId(id), HttpStatus.OK);
    }

//    @PutMapping
//    public ResponseEntity<Product> update(@RequestBody Product product) {
//        return new ResponseEntity<>(productService.update(product), HttpStatus.OK);
//    }

    @DeleteMapping("/{id}")
    public HttpStatus delete(@PathVariable Long id) {
        productService.delete(id);
        return HttpStatus.OK;
    }
}
