package com.api.furshet.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private int amount;
    private Integer price;
    private Long categoryId;
    private Boolean active;
    private String description;

    private List<Long> labelIds = new ArrayList<>();
    private List<Long> attributeIds = new ArrayList<>();

}
