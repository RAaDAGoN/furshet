package com.api.furshet.domain.entity;

import com.api.furshet.domain.enums.TypeCallbackRequest;
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
public class CallbackRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String FIO;
    private String email;
    private String phone;
    private String amountPeople;
    private String data;

    @Enumerated(EnumType.STRING)
    private TypeCallbackRequest typeCallbackRequest;
}
