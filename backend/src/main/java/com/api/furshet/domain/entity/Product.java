package com.api.furshet.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private int amount;
    private Integer price;
    private String filename;
    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
