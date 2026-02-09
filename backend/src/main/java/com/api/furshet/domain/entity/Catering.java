package com.api.furshet.domain.entity;

import com.api.furshet.domain.enums.TypePhoto;
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
public class Catering {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "point_id")
    private Point point;

    private String filename;

    @Enumerated(EnumType.STRING)
    private TypePhoto typePhoto;
}
