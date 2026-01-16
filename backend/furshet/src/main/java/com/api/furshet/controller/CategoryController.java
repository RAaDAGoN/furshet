package com.api.furshet.controller;

import com.api.furshet.dto.CategoryDTO;
import com.api.furshet.domain.entity.Category;
import com.api.furshet.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

//    @PostMapping
//    public ResponseEntity<Category> create(@RequestBody CategoryDTO dto) {
//        return new ResponseEntity<>(categoryService.create(dto), HttpStatus.OK);
//    }

//    @PostMapping
//    public ResponseEntity<List<Category>> create(@RequestBody List<CategoryDTO> dto) {
//        List<Category> categories = dto.stream()
//                .map(categoryService::create)
//                .collect(Collectors.toList());
//        return new ResponseEntity<>(categories, HttpStatus.CREATED);
//    }

    @PostMapping
    public ResponseEntity<List<Category>> create(@RequestPart("categories") List<CategoryDTO> dtolist,
                                                 @RequestPart("file") MultipartFile file) {
        List<Category> categories = dtolist.stream()
                .map(dto -> categoryService.create(dto, file))
                .collect(Collectors.toList());

        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAll() {
        return new ResponseEntity<>(categoryService.findAll(), HttpStatus.OK);
    }

//    @PutMapping
//    public ResponseEntity<Category> update(@RequestBody Category category) {
//        return new ResponseEntity<>(categoryService.update(category), HttpStatus.OK);
//    }

    @DeleteMapping
    public HttpStatus delete(@PathVariable Long id) {
        categoryService.delete(id);
        return HttpStatus.OK;
    }
}
