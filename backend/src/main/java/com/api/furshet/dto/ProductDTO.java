package com.api.furshet.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private int amount;
    private Integer price;
    private Long categoryId;
    private String filename;
    private Boolean active;
}
