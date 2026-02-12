package com.api.furshet.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TypeFeedback {
    REJECTED("Отклонено"),
    IN_PROGRESS("В работе"),
    ACCEPTED("Принято");

    private final String translate;
}
