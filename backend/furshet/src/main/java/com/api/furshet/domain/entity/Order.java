package com.api.furshet.domain.entity;

import com.api.furshet.domain.enums.PaymentMethods;
import com.api.furshet.domain.enums.TypeDelivery;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    //Контактные данные
    private String FIO;
    private String phone;
    private String email;

    //Адрес доставки
    @Enumerated(EnumType.STRING)
    private TypeDelivery delivery;
    private String city;
    private String address;
    private String comment;

    //Способ оплаты
    @Enumerated(EnumType.STRING)
    private PaymentMethods paymentMethod;

    //Состав заказа
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<OrderItem> orderItems = new ArrayList<>();
}
