package com.api.furshet.dto;

import com.api.furshet.domain.enums.PaymentMethods;
import com.api.furshet.domain.enums.TypeDelivery;
import jakarta.persistence.JoinColumn;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderRequestDTO {
    private Long id;

    //Контактные данные
    private String FIO;
    private String phone;
    private String email;

    //Адрес доставки
    private TypeDelivery delivery;
    private String city;
    private String address;
    private String comment;

    //Способ оплаты
    private PaymentMethods paymentMethod;

    //Состав заказа

    private List<OrderItemRequestDTO> orderItems = new ArrayList<>();
}