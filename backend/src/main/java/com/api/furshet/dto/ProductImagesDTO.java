package com.api.furshet.dto;

import lombok.Data;

@Data
public class ProductImagesDTO {
    private Long id;
    private Long productId;
    private String filename;
}
