package com.api.furshet.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TypePhoto {
    BIG("Большое"),
    MEDIUM("Среднее"),
    SMALL("Маленькое");

    private final String translate;
}
