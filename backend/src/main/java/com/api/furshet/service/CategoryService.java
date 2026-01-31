package com.api.furshet.service;

import com.api.furshet.domain.entity.Category;
import com.api.furshet.domain.entity.Product;
import com.api.furshet.dto.CategoryDTO;
import com.api.furshet.repository.CategoryRepository;
import com.api.furshet.repository.ProductRepository;
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
    private final ProductRepository productRepository;

    @Value("${upload.path}")
    private String uploadPath;

    public Category create(CategoryDTO dto, MultipartFile file) {
        Category category = Category.builder()
                .name(dto.getName())
                .outputToSearch(dto.getOutputToSearch())
                .build();

        if (file != null && !file.isEmpty()) {
            String fileName = saveFile(file);
            category.setFilename(fileName);
        }

        return categoryRepository.save(category);
    }

    public Category update(CategoryDTO dto, MultipartFile file) {
        if (dto.getId() != null) {

            Category category = categoryRepository.findById(dto.getId()).orElseThrow(() -> new RuntimeException("Category not found " + dto.getId()));

            category.setName(dto.getName());
            category.setOutputToSearch(dto.getOutputToSearch());


            if (Boolean.TRUE.equals(category.getActive()) && Boolean.FALSE.equals(dto.getActive())) {
                List<Product> product = productRepository.findByCategoryId(category.getId());

                for (Product prod : product) {
                    prod.setBlockedByCategory(true);
                }
                productRepository.saveAll(product);
            }

            else if (Boolean.FALSE.equals(category.getActive()) && Boolean.TRUE.equals(dto.getActive())) {
                List<Product> product = productRepository.findByCategoryId(category.getId());

                for (Product prod : product) {
                    prod.setBlockedByCategory(false);
                }
                productRepository.saveAll(product);
            }

            category.setActive(dto.getActive());

            if (file != null && !file.isEmpty()) {
                if (category.getFilename() != null) {
                    File oldFile = new File(uploadPath + "/" + category.getFilename());
                    if (oldFile.exists() && oldFile.isFile()) {
                        boolean deleted = oldFile.delete();
                        if (!deleted) {
                            System.out.println("Не удалось удалить старый файл: " + oldFile.getAbsolutePath());
                        }
                    }
                }

                String fileName = saveFile(file);
                category.setFilename(fileName);
            }

            return categoryRepository.save(category);
        } else {
            return create(dto, file);
        }
    }

    private String saveFile(MultipartFile file) {
        try {
            File dir = new File(uploadPath);
            if (!dir.exists()) dir.mkdirs();

            String uuid = UUID.randomUUID().toString();
            String fileName = uuid + "_" + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + fileName));

            return fileName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found " + id));
    }

    public void delete(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found " + id));

        if (Boolean.FALSE.equals(category.getActive())) {return;}

        if (productRepository.existsByCategory_Id(category.getId())) {
            category.setActive(false);
            categoryRepository.save(category);
        } else {
            categoryRepository.deleteById(id);
        }
    }
}
