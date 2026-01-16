package com.api.furshet.dto;

import com.api.furshet.domain.enums.TypeCallbackRequest;
import lombok.Data;
import lombok.Value;


@Data
public class CallbackRequestDto {
    Long id;
    String FIO;
    String email;
    String phone;
    String amountPeople;
    String data;
    TypeCallbackRequest typeCallbackRequest;
}