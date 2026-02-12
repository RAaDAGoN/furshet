package com.api.furshet.domain.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TypeCallbackRequest {
    SIMPLE("Простая"),
    CALCULATION("Расчёт мероприятия");

    public final String translate;
}
