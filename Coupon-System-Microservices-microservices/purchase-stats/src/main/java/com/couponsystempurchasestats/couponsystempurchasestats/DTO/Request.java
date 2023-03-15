package com.couponsystempurchasestats.couponsystempurchasestats.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Request {

    private long product_id;
    private long customer_id;
    private long company_id;
    private double price;
}
