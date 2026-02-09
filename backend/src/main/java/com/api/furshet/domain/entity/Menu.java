package com.api.furshet.domain.entity;

import com.api.furshet.domain.enums.TypeMenu;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Enumerated(EnumType.STRING)
    private TypeMenu name;

    private String translate;
    private String filename;

    @Builder.Default
    @ColumnDefault("true")
    private Boolean active = true;
}
