package com.coupons.couponsystem.dto;

import com.paypal.api.payments.Item;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Order {

    private double price;
    private String currency;
    private String method;
    private String intent;
    private String description;

    private List<Item> items;

}