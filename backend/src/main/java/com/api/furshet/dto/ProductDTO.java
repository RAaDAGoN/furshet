package com.api.furshet.dto;

import com.api.furshet.domain.enums.ProductLabelType;
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

    private List<Long> labelIds = new ArrayList<>();
}
