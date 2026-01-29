package com.api.furshet.service;

import com.api.furshet.domain.entity.Label;
import com.api.furshet.domain.entity.ProductImages;
import com.api.furshet.domain.entity.ProductLabel;
import com.api.furshet.domain.enums.ProductLabelType;
import com.api.furshet.dto.ProductDTO;
import com.api.furshet.domain.entity.Product;
import com.api.furshet.repository.LabelRepository;
import com.api.furshet.repository.OrderItemRepository;
import com.api.furshet.repository.ProductImagesRepository;
import com.api.furshet.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ProductImagesRepository productImagesRepository;
    private final OrderItemRepository orderItemRepository;
    private final LabelRepository labelRepository;

    @Value("${upload.path}")
    private String uploadPath;

    public Product create(ProductDTO dto, List<MultipartFile> files) {

        Product product = Product.builder()
                .name(dto.getName())
                .amount(dto.getAmount())
                .price(dto.getPrice())
                .category(categoryService.findById(dto.getCategoryId()))
                .build();

        if (product.getProductImages() == null) {
            product.setProductImages(new ArrayList<>());
        }

        productRepository.save(product);

        if (files != null) {
            for (MultipartFile file : files) {
                if (file.isEmpty()) continue;

                String fileName = saveFile(file);

                ProductImages productImages = ProductImages.builder()
                        .filename(fileName)
                        .product(product)
                        .build();

                product.getProductImages().add(productImages);
            }
        }

        productRepository.save(product);
        return product;
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

    public List<Product> findByCategoryIdAndActiveTrue(Long categoryId) {
        return productRepository.findByCategoryIdAndActiveTrue(categoryId);
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Product update(ProductDTO dto, List<MultipartFile> newImages, List<Long> deleteId) {
        System.out.println(dto);
        // если приходит id, то обновляем, иначе создаём новый
        if (dto.getId() != null) {

            Product product = productRepository.findById(dto.getId())
                    .orElseThrow(() -> new RuntimeException("Товар не найден"));

            if (deleteId != null) {
                for (Long imageId : deleteId) {
                    ProductImages image = productImagesRepository.findById(imageId)
                            .orElseThrow();

                    // удалить файл
                    File file = new File(uploadPath + "/" + image.getFilename());
                    if (file.exists()) {
                        file.delete();
                    }

                    productImagesRepository.delete(image);
                }
            }

            product.setName(dto.getName());
            product.setPrice(dto.getPrice());
            product.setAmount(dto.getAmount());
            product.setCategory(categoryService.findById(dto.getCategoryId()));
            product.setActive(dto.getActive());

            product.getProductLabel().clear();

            if (dto.getLabelIds() != null) {
                List<ProductLabel> productLabels = dto.getLabelIds().stream()
                        .map(labelId -> {
                            Label label = labelRepository.findById(labelId).orElseThrow();

                            ProductLabel productLabel = new ProductLabel();
                            productLabel.setLabel(label);
                            productLabel.setProduct(product);
                            return productLabel;
                        }).toList();

                product.getProductLabel().addAll(productLabels);
            }

            if (newImages != null && !newImages.isEmpty()) {
                for (MultipartFile file : newImages) {

                    if (file.isEmpty()) continue;

                    String filename = saveFile(file);

                    ProductImages image = ProductImages.builder()
                            .filename(filename)
                            .product(product)
                            .build();

                    product.getProductImages().add(image);
                }
            }

            return productRepository.save(product);
        } else {
            return create(dto, newImages);
        }
    }

    public void delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Продукт не найден"));

        if (Boolean.FALSE.equals(product.getActive())) { return; }

        if (orderItemRepository.existsByProductId(product.getId())) {
            product.setActive(false);
            productRepository.save(product);
        } else {
            productRepository.deleteById(id);
        }
    }
}
