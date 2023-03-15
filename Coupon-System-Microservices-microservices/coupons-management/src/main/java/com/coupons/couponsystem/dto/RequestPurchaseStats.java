package com.coupons.couponsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RequestPurchaseStats {

    private long product_id;
    private long customer_id;
    private long company_id;
    private double price;
}
