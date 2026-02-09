package com.api.furshet.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductLabelType {
    BESTSELLERS("Хит продаж", "EB4747"),
    POPULAR("Популярное", "97AB94"),
    NEW("Новинка", "FF6E00"),
    PROFITABLY("Выгодно", "97AB94"),
    FOR_CHILDREN("Для детей", "1FA0D3"),
    DISCOUNT("Скидка", "EFC312");

    private final String russianName;
    private final String color;

}


