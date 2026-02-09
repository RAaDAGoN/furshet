package com.api.furshet.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TypeMenu {
    REGULAR("Основное"),
    SEASONAL("Сезонное"),
    CATERING("Кейтеринг");

    private final String translate;
}
