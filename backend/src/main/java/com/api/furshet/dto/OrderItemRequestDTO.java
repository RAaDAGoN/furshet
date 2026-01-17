package com.api.furshet.dto;

import lombok.Data;

@Data
public class OrderItemRequestDTO{
    private Long Id;
    private Long productId;
    private String productName;
    private short quantity;
}
