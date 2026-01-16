package com.api.furshet.service;

import com.api.furshet.dto.ProductDTO;
import com.api.furshet.domain.entity.Product;
import com.api.furshet.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    @Value("${upload.path}")
    private String uploadPath;

    public Product create(ProductDTO dto, MultipartFile file) {
        String resultFileName = null;


        if (file != null && !file.isEmpty()) {
            String uuid = UUID.randomUUID().toString();
            resultFileName = uuid + file.getOriginalFilename().replaceAll("\\s+", "");
            try {
                file.transferTo(new File(uploadPath + "/" + resultFileName));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        Product product = Product.builder()
                .name(dto.getName())
                .amount(dto.getAmount())
                .price(dto.getPrice())
                .category(categoryService.findById(dto.getCategoryId()))
                .filename(resultFileName)
                .build();

        return productRepository.save(product);
    }

    public Product create(ProductDTO dto) {
        Product product = Product.builder()
                .name(dto.getName())
                .amount(dto.getAmount())
                .price(dto.getPrice())
                .category(categoryService.findById(dto.getCategoryId()))
                .build();

        return productRepository.save(product);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<Product> findByCategoryId(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Product update(ProductDTO dto, MultipartFile file) {
        // если приходит id, то обновляем, иначе создаём новый
        if (dto.getId() != null) {

            Product product = productRepository.findById(dto.getId())
                    .orElseThrow(() -> new RuntimeException("Товар не найден"));

            String resultFileName = null;

            if (file != null && !file.isEmpty()) {
                if (product.getFilename() != null) {
                    File oldFile = new File(uploadPath + "/" + product.getFilename());
                    if (oldFile.exists()) {
                        oldFile.delete();
                    }
                }

                File uploadFolder = new File(uploadPath);
                if (!uploadFolder.exists()) {
                    uploadFolder.mkdir();
                }

                String uuid = UUID.randomUUID().toString();
                resultFileName = uuid + file.getOriginalFilename().replaceAll("\\s+", "");
                try {
                    file.transferTo(new File(uploadPath + "/" + resultFileName));
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }

                product.setFilename(resultFileName);
            }


            product.setName(dto.getName());
            product.setPrice(dto.getPrice());
            product.setAmount(dto.getAmount());
            product.setCategory(categoryService.findById(dto.getCategoryId()));
            product.setActive(dto.getActive());


            return productRepository.save(product);
        } else {
            return create(dto, file);
        }
    }

    public void delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Товар не найден"));

        if (product.getFilename() != null) {
            File file = new File(uploadPath + "/" + product.getFilename());
            if (file.exists()) {
                file.delete();
            }
        }

        productRepository.deleteById(id);
    }
}
