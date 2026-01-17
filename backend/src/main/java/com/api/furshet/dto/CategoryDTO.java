package com.api.furshet.dto;

import lombok.Data;

@Data
public class CategoryDTO {
    private Long id;
    private String name;
    private String filename;
    private Boolean active;
}
