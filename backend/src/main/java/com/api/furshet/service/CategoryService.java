package com.api.furshet.service;

import com.api.furshet.dto.CategoryDTO;
import com.api.furshet.domain.entity.Category;
import com.api.furshet.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Value("${upload.path}")
    private String uploadPath;

    public Category create(CategoryDTO dto, MultipartFile file) {
        String resultFileName = null;

        if(file != null){
            File uploadFolder = new File(uploadPath);
            if(!uploadFolder.exists()){
                uploadFolder.mkdir();
            }

            String uuid = UUID.randomUUID().toString();
            resultFileName =  uuid + file.getOriginalFilename().replaceAll("\\s+", "");
            try {
                file.transferTo(new File(uploadPath + "/" + resultFileName));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        Category category = Category.builder()
                .name(dto.getName())
                .filename(resultFileName)
                .build();

        return categoryRepository.save(category);
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found " + id));
    }

    public Category update(CategoryDTO dto, MultipartFile file) {
        if (dto.getId() != null) {

            Category category = categoryRepository.findById(dto.getId()).orElseThrow(() -> new RuntimeException("Category not found " + dto.getId()));

            category.setName(dto.getName());
            category.setFilename(dto.getFilename());
            category.setActive(dto.getActive());

            return categoryRepository.save(category);
        } else {
            return create(dto, file);
        }
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

}
