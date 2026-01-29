package com.api.furshet.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductLabelType {
    BESTSELLERS,
    POPULAR,
    NEW,
    PROFITABLY,
    FOR_CHILDREN,
    DISCOUNT
}


//BESTSELLERS("Хит продаж"),
//POPULAR("Популярное"),
//NEW("Новинка"),
//PROFITABLY("Выгодно"),
//FOR_CHILDREN("Для детей"),
//DISCOUNT("Скидка");
//
//private final String russianName;
